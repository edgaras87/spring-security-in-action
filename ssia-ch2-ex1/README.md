# Curl commands

## test rest endpoint

### user flag 
$ curl -i -s -u user:e829ed45-debb-4fda-a841-5553b9b2fb19 http://localhost:8080/home
	HTTP/1.1 200
	Set-Cookie: JSESSIONID=B35171737EA19F707A6C6AD0C204042E; Path=/; HttpOnly
	X-Content-Type-Options: nosniff
	X-XSS-Protection: 1; mode=block
	Cache-Control: no-cache, no-store, max-age=0, must-revalidate
	Pragma: no-cache
	Expires: 0
	X-Frame-Options: DENY
	Content-Type: text/plain;charset=UTF-8
	Content-Length: 5
	Date: Wed, 27 Apr 2022 14:35:04 GMT
	
	Hello

### base64 encoding
$ echo -n user:e829ed45-debb-4fda-a841-5553b9b2fb19 | base64
	dXNlcjplODI5ZWQ0NS1kZWJiLTRmZGEtYTg0MS01NTUzYjliMmZiMTk=

$ curl -s -H "Authorization: Basic dXNlcjplODI5ZWQ0NS1kZWJiLTRmZGEtYTg0MS01NTUzYjliMmZiMTk=" http://localhost:8080/home
	Hello
