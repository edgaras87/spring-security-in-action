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


