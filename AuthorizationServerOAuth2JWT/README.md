# Authorization Server notes

If you need to share the key outside your system, it shouldn’t be symmetric

When we can’t assume a trustful relationship between the authorization server and the resource server, we use
asymmetric key pairs. With symmetric keys, the resource server has too much power: the possibility of not just 
validating tokens, but signing them as well.


### symmetric key
the same key for signing the token as well as for verifying the signature

### asymmetric key pair
one key to sign the token but a different one to verify the signature

## Some errors

### 500 Internal Server Error
1. https://github.com/Baeldung/spring-security-oauth/issues/118
2. do not put authentication/resource serve in sub folder: ProjectPath/subfolder/AuthenticationServerProj
it should be : ProjectPath/AuthenticationServerProj

# Generating the asymmetric key pair
(which means it has a private part used by the authorization server to sign 
a token and a public part used by the resource server to validate the signature)

GENERATING A PRIVATE KEY
$ keytool -genkeypair -alias edge -keyalg RSA -keypass keypass -keystore jwtkeys.jks -storepass keypass
output: jwtkeys.jks

OBTAINING THE PUBLIC KEY
$ keytool -list -rfc --keystore jwtkeys.jks | openssl x509 -inform pem -pubkey
Enter keystore password:  keypass
-----BEGIN PUBLIC KEY-----
MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAzGJtf53yPPCHZbjImfcD
8EVexa07r2MNMPgHU+VBfmsX0w+AfDx5MmnQGypzRR0CyRyNKPWhafW0Sc2SGUu5
QwLrPJIcY3VMzMahqBdWJjFImvJljoFl8h0alwK+VSq3iRPwM82ccpOqOhleR2Yq
R8zdQDBZ4fno2lyNmBpPoiWSitrkQxDY8UMnnO9Prqc/nAVmaNwA9rBhWY8J8syP
DKQhsq1VeutmAWFPjyjUrrKHUPMiOXcm9XAx1cx5+5F4RcBRqc2mTZEQrJYGKYZd
6LM1XHWfczjUdtfsp3Yx6o9J+pOKAVTn4RIVKWdLl5DnhWpu9p4GbCOE3/ows8di
rwIDAQAB
-----END PUBLIC KEY-----
-----BEGIN CERTIFICATE-----
MIIDVjCCAj6gAwIBAgIJAIym2atpuwnLMA0GCSqGSIb3DQEBCwUAMFkxCzAJBgNV
BAYTAkRFMQ4wDAYDVQQIEwVCYWRlbjEOMAwGA1UEBxMFQmFkZW4xDTALBgNVBAoT
BGhvbWUxDTALBgNVBAsTBGhvbWUxDDAKBgNVBAMTA2pvbjAeFw0yMjA1MTQxMTQy
MzdaFw0yMjA4MTIxMTQyMzdaMFkxCzAJBgNVBAYTAkRFMQ4wDAYDVQQIEwVCYWRl
bjEOMAwGA1UEBxMFQmFkZW4xDTALBgNVBAoTBGhvbWUxDTALBgNVBAsTBGhvbWUx
DDAKBgNVBAMTA2pvbjCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAMxi
bX+d8jzwh2W4yJn3A/BFXsWtO69jDTD4B1PlQX5rF9MPgHw8eTJp0Bsqc0UdAskc
jSj1oWn1tEnNkhlLuUMC6zySHGN1TMzGoagXViYxSJryZY6BZfIdGpcCvlUqt4kT
8DPNnHKTqjoZXkdmKkfM3UAwWeH56NpcjZgaT6Ilkora5EMQ2PFDJ5zvT66nP5wF
ZmjcAPawYVmPCfLMjwykIbKtVXrrZgFhT48o1K6yh1DzIjl3JvVwMdXMefuReEXA
UanNpk2REKyWBimGXeizNVx1n3M41HbX7Kd2MeqPSfqTigFU5+ESFSlnS5eQ54Vq
bvaeBmwjhN/6MLPHYq8CAwEAAaMhMB8wHQYDVR0OBBYEFLABIeV8wTfTiTEi5poT
Oie6Uvl3MA0GCSqGSIb3DQEBCwUAA4IBAQCVJgyo7u4rgqJ97NzWfHHcBVWVdyAg
aThS4nWP1WDLPRwolKACcnGK07Khqqnyl97fzuPquWVs+fxJZIUjPOvCu5j11u4L
eDI99fje1U5TG6EjOAagJki5+YPOaXY0RxO2sNj/IX4l7V9jqP16WXYOkWA5JvBF
swJwEjcCAb3VpLunLPcnTcBTfE4M3pWKGaemdsR70T4RqsWFeKXsOsPbH9bSfJtS
hPHRj1Yh3k7mk2L3g8xI0onye7vW021FgCWokpdfi/6IBjYhAorIfCf5jes+y7em
ELRpmU3VUhwDX8jSYP3YKNeRwPRX+Rw/ne4NcpT12H4EY8XsJoDkY8V7
-----END CERTIFICATE-----

# Testing OAuth2 JWT

## JWT signed with symmmetric keys

(key: ymLTU8rq83j4fmJZj60wh4OrMNuntIj4fmJ)

=== request token:
curl --location --request POST 'http://localhost:8080/oauth/token' \
--header 'Authorization: Basic Y2xpZW50MTpzZWNyZXQx' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'grant_type=password' \
--data-urlencode 'username=jon' \
--data-urlencode 'password=pass' \
--data-urlencode 'scope=read'

=== response:
{
    "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2NTI1NzM5MDAsInVzZXJfbmFtZSI6ImpvbiIsImF1dGhvcml0aWVzIjpbInJlYWQiXSwianRpIjoiZjQzMzljYzctYjAyYy00YjNlLWFlMzUtNzE4ZmVkMTU3NjM5IiwiY2xpZW50X2lkIjoiY2xpZW50MSIsInNjb3BlIjpbInJlYWQiXX0.gZa0Gy6XJdNsOuvVW6_wEiRqU0xfP0Cf_wxncvzhhSg",
    "token_type": "bearer",
    "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJqb24iLCJzY29wZSI6WyJyZWFkIl0sImF0aSI6ImY0MzM5Y2M3LWIwMmMtNGIzZS1hZTM1LTcxOGZlZDE1NzYzOSIsImV4cCI6MTY1NTEyMjcwMCwiYXV0aG9yaXRpZXMiOlsicmVhZCJdLCJqdGkiOiJkZjk2ZTdmMy1kYjEyLTQzODAtOTcwMi1jNjI0NWI0N2EzNTIiLCJjbGllbnRfaWQiOiJjbGllbnQxIn0.FFptL9DtZBhmsoEeE4TcN__zANnYVQ7cA2hYClSLKy8",
    "expires_in": 43199,
    "scope": "read",
    "jti": "f4339cc7-b02c-4b3e-ae35-718fed157639"
}



=== request for endpoint: /hello
curl --location --request GET 'http://localhost:9090/hello' \
--header 'Authorization: Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2NTI1NzM0NDMsInVzZXJfbmFtZSI6ImpvbiIsImF1dGhvcml0aWVzIjpbInJlYWQiXSwianRpIjoiNDgwZDE5ZTktYWM4OC00ODhiLTlhNDUtNmUwZWYwMDM5ZDhmIiwiY2xpZW50X2lkIjoiY2xpZW50MSIsInNjb3BlIjpbInJlYWQiXX0.af5ZnX171Wkc7uMjTEtoYRjQRuX6rDbgAkEwFHXeC4MfLsn4yecxXN-SnsuXfRl_1ivQmcPndtpGuf71uu7YtnMw35ePsXEvNwYy1p2e3dPA3kCreTEKjiim8-RC3sWUOSbGyu1vraBy8SrTDBBt6_tZNH_J7-6XTLSAYs2QVr8QgNr_b3bZUx12ZbYGoaaW6k08WjEVvk2ItpK1qpsQZpHK2HCiXfcaUPveCre1DwDGDfjyuelQkPIFPR-fbLJkF60ZxYhV-9kZTX4ACeWrtrfehRoocMNGLjK5Ag7E1n5u-D_BqLeK3mxW05tAAtQlXKEmHpYIng61PaEXURAl0g'

=== response:
Hello!

## JWT signed with asymmmetric keys

=== request token:
curl --location --request POST 'http://localhost:8080/oauth/token' \
--header 'Authorization: Basic Y2xpZW50MTpzZWNyZXQx' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'grant_type=password' \
--data-urlencode 'username=jon' \
--data-urlencode 'password=pass' \
--data-urlencode 'scope=read'

=== response:
{
    "access_token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2NTI1NzM0NDMsInVzZXJfbmFtZSI6ImpvbiIsImF1dGhvcml0aWVzIjpbInJlYWQiXSwianRpIjoiNDgwZDE5ZTktYWM4OC00ODhiLTlhNDUtNmUwZWYwMDM5ZDhmIiwiY2xpZW50X2lkIjoiY2xpZW50MSIsInNjb3BlIjpbInJlYWQiXX0.af5ZnX171Wkc7uMjTEtoYRjQRuX6rDbgAkEwFHXeC4MfLsn4yecxXN-SnsuXfRl_1ivQmcPndtpGuf71uu7YtnMw35ePsXEvNwYy1p2e3dPA3kCreTEKjiim8-RC3sWUOSbGyu1vraBy8SrTDBBt6_tZNH_J7-6XTLSAYs2QVr8QgNr_b3bZUx12ZbYGoaaW6k08WjEVvk2ItpK1qpsQZpHK2HCiXfcaUPveCre1DwDGDfjyuelQkPIFPR-fbLJkF60ZxYhV-9kZTX4ACeWrtrfehRoocMNGLjK5Ag7E1n5u-D_BqLeK3mxW05tAAtQlXKEmHpYIng61PaEXURAl0g",
    "token_type": "bearer",
    "refresh_token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJqb24iLCJzY29wZSI6WyJyZWFkIl0sImF0aSI6IjQ4MGQxOWU5LWFjODgtNDg4Yi05YTQ1LTZlMGVmMDAzOWQ4ZiIsImV4cCI6MTY1NTEyMjI0MywiYXV0aG9yaXRpZXMiOlsicmVhZCJdLCJqdGkiOiI4MjFmMTIxYi1hOTVjLTQ1OTgtOGZmMy04NTdhNzI2ODZmMWUiLCJjbGllbnRfaWQiOiJjbGllbnQxIn0.UDNZgAn0Awj97hPYfhk5m5fb-a61inqJ0RzsvEaAm5NbvhLQxOJIFFW5jTJwdK0vi2Nli0-1b5c2J7X6W-1o5HdqNVmbV5AmsSNxuDnoKHEBlYJfAYREzvh6O2uhC01eDJwNLiS2Tjy5rwSUJ6HBvafdxXMJh69yL2-Ns32xtCQLuLhZjAbqpfxZilrtpscIx8aRsZyLVo9dQET3PCBK62wvvtn-SUPBGH3O9J7ZzeL1av-WheoyWSBAolca59FDiu0xkyqR5vZEjcCvis7Zp87kzU_6XEF6b65ZV3woqOvfwlkFPmuuOg7NjS1kkUmxkWdtzMJYtKdBkfgGcwlv3w",
    "expires_in": 43199,
    "scope": "read",
    "jti": "480d19e9-ac88-488b-9a45-6e0ef0039d8f"
}



=== request for endpoint: /hello
curl --location --request GET 'http://localhost:9090/hello' \
--header 'Authorization: Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2NTI1NzM0NDMsInVzZXJfbmFtZSI6ImpvbiIsImF1dGhvcml0aWVzIjpbInJlYWQiXSwianRpIjoiNDgwZDE5ZTktYWM4OC00ODhiLTlhNDUtNmUwZWYwMDM5ZDhmIiwiY2xpZW50X2lkIjoiY2xpZW50MSIsInNjb3BlIjpbInJlYWQiXX0.af5ZnX171Wkc7uMjTEtoYRjQRuX6rDbgAkEwFHXeC4MfLsn4yecxXN-SnsuXfRl_1ivQmcPndtpGuf71uu7YtnMw35ePsXEvNwYy1p2e3dPA3kCreTEKjiim8-RC3sWUOSbGyu1vraBy8SrTDBBt6_tZNH_J7-6XTLSAYs2QVr8QgNr_b3bZUx12ZbYGoaaW6k08WjEVvk2ItpK1qpsQZpHK2HCiXfcaUPveCre1DwDGDfjyuelQkPIFPR-fbLJkF60ZxYhV-9kZTX4ACeWrtrfehRoocMNGLjK5Ag7E1n5u-D_BqLeK3mxW05tAAtQlXKEmHpYIng61PaEXURAl0g'

=== response:
Hello!

# Customize JWT details
In most cases, you need no more than what Spring Security already adds to the token. To add additional details 
to your token, you need to create an object of type TokenEnhancer. The enhance() method of a TokenEnhancer object
receives as a parameter the token we enhance and returns the “enhanced” token, containing the additional details.

## token enchancers
We have to create a chain of token enhancers and set the entire chain instead of only one object, because the 
access token converter object is also a token enhancer. If we configure only our custom token enhancer, we would 
override the behavior of the access token converter. Instead, we add both in a chain of responsibilities, and we 
configure the chain containing both objects.

# Testing dustomized JWT details
=== request token:
{
    "access_token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJqb24iLCJzY29wZSI6WyJyZWFkIl0sImdlbmVyYXRlZEluWm9uZSI6IkV1cm9wZS9CZXJsaW4iLCJleHAiOjE2NTI1ODYyNzYsImF1dGhvcml0aWVzIjpbInJlYWQiXSwianRpIjoiN2YwNDFlZmYtOWZjMi00ZmU4LThkM2UtYmE2OTNlNjExY2U5IiwiY2xpZW50X2lkIjoiY2xpZW50MSJ9.xT4jkD4erRjtA_y_Bl67X1pyNYj9fBg5hXxwFLqDsfEKyzoLALz9E7bm_i-TAIpFF0q_Gg9mpH25F-j1GNUZE9rVubojwHCS_9cmXCT2ykXlDnuwKlPiw5GUlDM93yKhkt6_ezsdhyGvYED_LYV57SeG9bTchGBkIp1QaQQId6GIDdJcXlThhGVKdruvtnrI4iDN2i5mQam2mEunXRUs1MLUKonZdiGAEAu-xM9DXT57xA6WuAAsAQhJLoBSqA6ZHjYAnpAScfPD1rJ92fk5iA6wFyr0ctVPOPQknf9_Kbhtl16GZb8POjlbvxanp3lOzVE2XPjFnwkkLbvC2eieJQ",
    "token_type": "bearer",
    "refresh_token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJqb24iLCJzY29wZSI6WyJyZWFkIl0sImF0aSI6IjdmMDQxZWZmLTlmYzItNGZlOC04ZDNlLWJhNjkzZTYxMWNlOSIsImdlbmVyYXRlZEluWm9uZSI6IkV1cm9wZS9CZXJsaW4iLCJleHAiOjE2NTUxMzUwNzYsImF1dGhvcml0aWVzIjpbInJlYWQiXSwianRpIjoiYzRiMjRjMTAtN2Y1Mi00YTJhLTkyZmEtNmY3YWRmZGE4NDk0IiwiY2xpZW50X2lkIjoiY2xpZW50MSJ9.nfnI-DwvRCHwv9Mp-5MdOvAiq9aU_6x8FhAkDI1xTFzEg-oORxM7VWNmi04UrjWJaNxQL75-oN6j-6foF5FHD6eCb42GFq0mWZQcDQeo0_woi3vqPfLMKQeUiX_kWsiZ6jg7WKwolw5_swbA0N79DTDLjMNo9yB0BdYgOIo3xDYDu4wq0TyeLMUrKQ2-wLHqK0389yUBiICIc2OWX0rjkJ4_Ehhv9W2HpdKhxiSJB7RtKu1024oqUhasDXKT1p6Abiq1ohjjF7Jc-wVcxoDCGXrbfHH2GWTmcf5MRdOmpnVfFyOW88PgCl7tgcVWw9Ere9JQP450CAAyvsrz0YLiTQ",
    "expires_in": 43199,
    "scope": "read",
    "generatedInZone": "Europe/Berlin",
    "jti": "7f041eff-9fc2-4fe8-8d3e-ba693e611ce9"
}

=== response:
{
    "access_token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJqb24iLCJzY29wZSI6WyJyZWFkIl0sImdlbmVyYXRlZEluWm9uZSI6IkV1cm9wZS9CZXJsaW4iLCJleHAiOjE2NTI1ODYyNzYsImF1dGhvcml0aWVzIjpbInJlYWQiXSwianRpIjoiN2YwNDFlZmYtOWZjMi00ZmU4LThkM2UtYmE2OTNlNjExY2U5IiwiY2xpZW50X2lkIjoiY2xpZW50MSJ9.xT4jkD4erRjtA_y_Bl67X1pyNYj9fBg5hXxwFLqDsfEKyzoLALz9E7bm_i-TAIpFF0q_Gg9mpH25F-j1GNUZE9rVubojwHCS_9cmXCT2ykXlDnuwKlPiw5GUlDM93yKhkt6_ezsdhyGvYED_LYV57SeG9bTchGBkIp1QaQQId6GIDdJcXlThhGVKdruvtnrI4iDN2i5mQam2mEunXRUs1MLUKonZdiGAEAu-xM9DXT57xA6WuAAsAQhJLoBSqA6ZHjYAnpAScfPD1rJ92fk5iA6wFyr0ctVPOPQknf9_Kbhtl16GZb8POjlbvxanp3lOzVE2XPjFnwkkLbvC2eieJQ",
    "token_type": "bearer",
    "refresh_token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJqb24iLCJzY29wZSI6WyJyZWFkIl0sImF0aSI6IjdmMDQxZWZmLTlmYzItNGZlOC04ZDNlLWJhNjkzZTYxMWNlOSIsImdlbmVyYXRlZEluWm9uZSI6IkV1cm9wZS9CZXJsaW4iLCJleHAiOjE2NTUxMzUwNzYsImF1dGhvcml0aWVzIjpbInJlYWQiXSwianRpIjoiYzRiMjRjMTAtN2Y1Mi00YTJhLTkyZmEtNmY3YWRmZGE4NDk0IiwiY2xpZW50X2lkIjoiY2xpZW50MSJ9.nfnI-DwvRCHwv9Mp-5MdOvAiq9aU_6x8FhAkDI1xTFzEg-oORxM7VWNmi04UrjWJaNxQL75-oN6j-6foF5FHD6eCb42GFq0mWZQcDQeo0_woi3vqPfLMKQeUiX_kWsiZ6jg7WKwolw5_swbA0N79DTDLjMNo9yB0BdYgOIo3xDYDu4wq0TyeLMUrKQ2-wLHqK0389yUBiICIc2OWX0rjkJ4_Ehhv9W2HpdKhxiSJB7RtKu1024oqUhasDXKT1p6Abiq1ohjjF7Jc-wVcxoDCGXrbfHH2GWTmcf5MRdOmpnVfFyOW88PgCl7tgcVWw9Ere9JQP450CAAyvsrz0YLiTQ",
    "expires_in": 43199,
    "scope": "read",
    "generatedInZone": "Europe/Berlin",
    "jti": "7f041eff-9fc2-4fe8-8d3e-ba693e611ce9"
}


