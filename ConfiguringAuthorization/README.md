# Notes

## antMatchers(String), mvcMatchers(HTTPmethod, String) mvcMatchers(HTTPmethod) 
Same logic as mvcMatchers with few difference:
- antMatchers(HTTPmethod) acts on path (/**)
- antMatchers is less safe. for example: 
	*mvcMatchers("/home") == antMatchers("/home", "/home/")
	*if antMatchers("/home").authenticated() is set 
		then antMatchers("/home/") is permitAll() by default

## regex matchers
Handier to use regex matchers when you find something like the
following:
- Specific configurations for all paths containing phone
	numbers or email addresses
- Specific configurations for all paths having a certain format, 
	including what is sent through all the path	variables
	
	
## Filters in the Spring Security architecture
The filters in Spring Security architecture are typical HTTP
filters. We can create filters by implementing the Filter
interface from the javax.servlet package. As for any other
HTTP filter, you need to override the doFilter() method to
implement its logic. This method receives as parameters the
ServletRequest, ServletResponse, and FilterChain:

### ServletRequest
Represents the HTTP request. We use the ServletRequest object to retrieve
details about the request.

### ServletResponse
Represents the HTTP response. We use the ServletResponse object
to alter the response before sending it back to the
client or further along the filter chain.

### FilterChain
Represents the chain of filters. We use the FilterChain object to forward the
request to the next filter in the chain

## Filter chain
Represents a collection of filters with a defined order in which they act. 
Spring Security provides some filter implementations and their order for us. 
Among the provided filters

### BasicAuthenticationFilter 
takes care of HTTP Basic authentication, if present.

### CsrfFilter 
takes care of cross-site request forgery (CSRF) protection

### CorsFilter 
takes care of cross-origin resource sharing (CORS) authorization rules

## Filter order number
Each filter has an order number. This determines the order in which 
filters are applied to a request. 

## addFilterAt(filter, BasicAuthenticationFilter.class)
When adding a filter at a specific position, Spring Security does not assume it is the only one at that position. 


