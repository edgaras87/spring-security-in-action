# Notes

## Authorization Server
Spring Security OAuth2 Boot simplifies standing up an OAuth 2.0 Authorization Server.
https://docs.spring.io/spring-security-oauth2-boot/docs/2.2.6.BUILD-SNAPSHOT/reference/htmlsingle/


## Spring Cloud
Spring Boot compatibility
https://spring.io/projects/spring-cloud
dependencyManagement
https://reddit.fun/19436/beancreationexception-creating-configurationpropertiesbeans

### ClientRegistration interface 
defines the OAuth 2 client registration in Spring Security. 

### ClientRegistrationRepository interface
describes the object responsible for managing client registrations.

## Testing Authorization Server

## Getting token

base64 encoder
https://www.base64encode.org/ 

### Using the password grant type (with refresh token grant type)

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
    "access_token": "bd13a908-fba8-45a4-8869-c08d98b00fd9",
    "token_type": "bearer",
    "refresh_token": "1ecdf7e2-5808-4448-b686-5f3b21d586f9",
    "expires_in": 41976,
    "scope": "read"
}

### Using the authorization code grant type

base64.encode("client2:secret2") => Y2xpZW50MjpzZWNyZXQy

request
http://localhost:8080/oauth/authorize?response_type=code&client_id=client2&scope=read
response
http://localhost:9090/home?code=8OawV6

request
curl --location --request POST 'http://localhost:8080/oauth/token' \
--header 'Authorization: Basic Y2xpZW50MjpzZWNyZXQy' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'grant_type=authorization_code' \
--data-urlencode 'scope=read' \
--data-urlencode 'code=8OawV6'	

response
{
    "access_token": "c2a812fd-a500-477b-a6c2-559d786462ff",
    "token_type": "bearer",
    "expires_in": 43199,
    "scope": "read"
}

### Using the client credentials grant type

base64.encode("client3:secret3") => Y2xpZW50MzpzZWNyZXQz

request
curl --location --request POST 'http://localhost:8080/oauth/token' \
--header 'Authorization: Basic Y2xpZW50MzpzZWNyZXQz' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'grant_type=client_credentials' \
--data-urlencode 'scope=info'

response
{
    "access_token": "38c107c5-65ce-4acf-8de6-5cd2a12d4b62",
    "token_type": "bearer",
    "expires_in": 43199,
    "scope": "info"
}

