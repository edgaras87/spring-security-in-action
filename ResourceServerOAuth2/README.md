# Notes

# Resource Server Project

## implement token validation by allowing the resource server to call the authorization server directly

Implementation is possible in two ways: (both are implemented in this project) 
1. by using org.springframework.cloud => spring-cloud-dependencies
2. using token introspection without spring-cloud-dependencies (still dependencies needed)

choose this approach if the tokens in your system are plain
(for example, simple UUIDs as in the default implementation of the 
authorization server with Spring Security).

The disadvantage of this approach is that for each request on the resource server 
having a new, as yet unknown token, the resource server calls the authorization 
server to validate the token. These calls can put an unnecessary load on the
authorization server.

## blackboarding  architectural style
authorization server and the resource server use a shared database.

To implement token management with blackboarding, Spring Security offers 
the JdbcTokenStore implementation. 


### TokenStore
The contract representing the object that manages tokens in Spring Security, 
both on the authorization server as well as for the resource server, is the TokenStore.

Spring Security offers various implementations for the TokenStore contract, 
and in most cases, you won’t need to write your own implementation.

### InMemoryTokenStore
a default token store provided by Spring Security.

### JdbcTokenStore
this token store works with a database directly via JDBC. It works similarly to 
the JdbcUserDetailsManager but instead of managing users, the JdbcTokenStore manages
tokens.

JdbcTokenStore expects you to have two tables in the
database. It uses one table to store access tokens (the name
for this table should be oauth_access _token) and one table
to store refresh tokens (the name for this table should be
oauth_refresh_token). The table used to store tokens persists
the refresh tokens.

you could choose to use TokenStore just to persist tokens and continue using 
the /oauth/check_token endpoint. You would choose to do so if you don’t want 
to use a shared database, but you need
to persist tokens 


## Testing Resource Server

### basic credentials encoding
base64 encoder https://www.base64encode.org/ 

### Testing using the password grant type (with refresh token grant type)

=== obtain new token
base64.encode("client1:secret1") => Y2xpZW50MTpzZWNyZXQx

request
curl --location --request POST 'http://localhost:8080/oauth/token' \
--header 'Authorization: Basic Y2xpZW50MTpzZWNyZXQx' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'grant_type=password' \
--data-urlencode 'username=jon' \
--data-urlencode 'password=pass' \
--data-urlencode 'scope=read'

response
{
	"access_token": "69f39b3b-0a57-43eb-9862-d71a1346ecd2",
	"token_type": "bearer",
	"refresh_token": "8aee7e08-0654-4405-aadd-540c6daa3a4d",
	"expires_in": 43199,
	"scope": "read"
}

=== check token
request
base64.encode("resourceserver1:resourceserversecret1") => cmVzb3VyY2VzZXJ2ZXIxOnJlc291cmNlc2VydmVyc2VjcmV0MQ==
curl --location --request POST 'http://localhost:8080/oauth/check_token' \
--header 'Authorization: Basic cmVzb3VyY2VzZXJ2ZXIxOnJlc291cmNlc2VydmVyc2VjcmV0MQ==' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'token=69f39b3b-0a57-43eb-9862-d71a1346ecd2'

response
{
    "active": true,
    "exp": 1652398843,
    "user_name": "jon",
    "authorities": [
        "read"
    ],
    "client_id": "client1",
    "scope": [
        "read"
    ]
}

=== use token
request
curl --location --request GET 'http://localhost:9090/hello' \
--header 'Authorization: Bearer 69f39b3b-0a57-43eb-9862-d71a1346ecd2'

response
Hello!





