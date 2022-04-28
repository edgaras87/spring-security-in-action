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

## HTTPS at the application level

### generate private and public keys

$ winpty openssl req -newkey rsa:2048 -x509 -keyout key.pem -out cert.pem -days 365
Generating a RSA private key
...............+++++
........+++++
writing new private key to 'key.pem'
Enter PEM pass phrase:
Verifying - Enter PEM pass phrase:
...

###  generate the self-signed certificate

$ winpty openssl pkcs12 -export -in cert.pem -inkey key.pem -out certificate.p12 -name "certificate"
Enter pass phrase for key.pem:
Enter Export Password:
Verifying - Enter Export Password:

### test https rest endpoint with self-signed certificate

$ curl -k -i -s -u user:076b6dff-11c3-4c91-852b-caea8e9388cf  https://localhost:8080/home
HTTP/1.1 200
Set-Cookie: JSESSIONID=96C6278D3E773ECA5A003644AAE050F1; Path=/; Secure; HttpOnly
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
Strict-Transport-Security: max-age=31536000 ; includeSubDomains
X-Frame-Options: DENY
Content-Type: text/plain;charset=UTF-8
Content-Length: 5
Date: Wed, 27 Apr 2022 17:02:09 GMT

Hello

## Define in memory custom user 

### testing new user

$ curl -i -s -u jon:pass http://localhost:8080/home
HTTP/1.1 200
Set-Cookie: JSESSIONID=EA496EF1F1468C50155B1BE746BA8E30; Path=/; HttpOnly
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Type: text/plain;charset=UTF-8
Content-Length: 5
Date: Wed, 27 Apr 2022 17:53:11 GMT

Hello
