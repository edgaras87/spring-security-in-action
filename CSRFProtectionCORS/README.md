# Notes

# CSRF

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

# CORS cross-origin resource sharing
By default, browsers don’t allow requests made for any domain other than the one from
which the site is loaded. For example, if you access the site from example.com, 
the browser won’t let the site make requests to api.example.com. 
We can briefly say that a browser uses the CORS mechanism to relax this strict policy 
and allow requests made between different origins in some conditions.

You can configure CORS both for an endpoint using the @CrossOrigin annotation or centralized in
the configuration class using the cors() method of the HttpSecurity object.

## The CORS mechanism works based on HTTP headers
The most important are:

### Access-Control-Allow-Origin
Specifies the foreign domains (origins) that can access resources
on your domain. 
### Access-Control-Allow-Methods
Lets us refer only to some HTTP methods in situations in which we want to allow access to a different
domain, but only to specific HTTP methods. You use this if you’re going to enable example.com to
call some endpoint, but only with HTTP GET, for example.
### Access-Control-Allow-Headers
Adds limitations to which headers you can use in a specific request

## Preflight request
Sometimes, the browser first makes a call using the HTTP OPTIONS method
to test whether the request should be allowed. The preflight request and 
the decision to make it or not are the responsibility of the browser.

