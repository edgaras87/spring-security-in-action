# Notes
This project (SingleSignOnAppOAuth2) uses OAuth 2 authorization code grant.

### SSO (single sign-on).

## OAuth 2 grants 
 -Authorization code
 -Password
 -Client credentials
 -Refresh token

### Authorization code grants
1. Make the authentication request
2. Obtain an access token
3. Call the protected resource

The authentication code grant type works by
allowing the user to directly authenticate at the
authorization server, which enables the client to
obtain an access token. We choose this grant type
when the user doesn’t trust the client and doesn’t
want to share their credentials with it.
 
### Password grants
1. Request an access token.
2. Use the access token to call resources.

The password grant type implies that the user
shares its credentials with the client. You should
apply this only if you can trust the client.

### Client credentials
1. Request an access token
2. Use the access token to call resources

The client credentials grant type implies that the
client obtains a token by authenticating only with
its credentials. We choose this grant type when the
client needs to call an endpoint of the resource
server that isn’t a resource of the user
