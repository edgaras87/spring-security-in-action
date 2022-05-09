# Notes

## CSRF token and JSESSIONID
Default implementation of CsrfTokenRepository stores the value of the CSRF token
on the session. Example:
	'Cookie: JSESSIONID=21ADA55E10D70BA81C338FFBB06B0206' 

## Perform post with activated csrf protection to REST endpoint
first
	from curl -XGET, we get X-CSRF-TOKEN and Cookie 
then
	curl -i -s -XPOST 
	-H "X-CSRF-TOKEN: 35834856-293c-45db-b2ed-dbdc2931af74" 
	-H "Cookie: JSESSIONID=A2C95529AECD24D7BEFEA5C4511E07A2" 
	-u jon:pass 
	http://localhost:8080/home
	
## About CSRF tokens 
About CSRF tokens work well in an architecture where the same server is responsible 
for both the frontend and the backend, mainly for its simplicity. But CSRF tokens don’t work well
when the client is independent of the backend solution it consumes. This scenario happens 
when you have a mobile application as a client or a web frontend developed independently. 
A web client developed with a framework like Angular, ReactJS, or Vue.js is ubiquitous 
in web application architectures, and this is why you need to know how to implement the security 
approach for these cases as well.

### never use HTTP GET with mutating operations!

## Perform post with csrf token from database
generates and saves token to database with identifier="X-IDENTIFIER"
$ curl -i -s -H "X-IDENTIFIER:pass"  http://localhost:8080/home 
using identifier and token porforming post requests 
$ curl -i -s -H "X-IDENTIFIER:pass" -H "X-CSRF-TOKEN:c83a6767-a7fe-41fc-8684-35af5169c0a0" -XPOST  http://localhost:8080/home


