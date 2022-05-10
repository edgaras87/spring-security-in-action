# Notes

# Project 3
The architecture of the system has three components:

### The client 
This is the application consuming the backend. It could be a mobile app or the frontend of a
web application developed using a framework like Angular, ReactJS, or Vue.js. We don’t implement the
client part of the system, but keep in mind that it exists in a real-world application. Instead of using the
client to call endpoints, we use cURL.

### The authentication server 
This is an application with a database of user credentials. The purpose of this application is to authenticate 
users based on their credentials (username and password) and send them a one-time password (OTP) through SMS. 
Because we won’t actually send an SMS in this example, we’ll read the value of the OTP from the database directly

### The business logic server 
This is the application exposing endpoints that our client consumes. We want to secure access to these endpoints. 
Before calling an endpoint, the user must authenticate with their username and password and then send an OTP. The
user receives the OTP through an SMS message. Because this application is our target application, we secure it 
with Spring Security

## multi-factor authentication (MFA)
When the client authenticates the username and password, the business logic server sends a request for an OTP to the
authentication server. After successful authentication, the authentication server sends a randomly generated OTP to the
client via SMS. This way of identifying the user is called MFA.

## What are tokens? 
A token is just an access card, theoretically. When you visit an office building, you first go to the reception
desk. There, you identify yourself (authentication), and you receive an access card (token). You can use the access card to
open some doors, but not necessarily all doors. This way, the token authorizes your access and decides whether you’re
allowed to do something, like opening a particular door. 

## tokens bring more advantages

 -Tokens help you avoid sharing credentials in all requests.
 -You can define tokens with a short lifetime.
 -You can invalidate tokens without invalidating the credentials.
 -Tokens can also store details like user authorities that the client needs to send in the request.
 -Tokens help you delegate the authentication responsibility to another component in the system.

##  JSON Web Token (JWT)
A JWT is composed of three parts: the header, the body, and the signature. The header and the body are
JSON representations of the data stored in the token. To make these easy to send in a request header, they are
Base64 encoded. The last part of the token is the signature. The parts are concatenated with dots.
More about JWT https://github.com/jwtk/jjwt#overview

## Testing authentication server

### add user
$ curl -XPOST -H "content-type: application/json" -d "{\"username\":\"den\", \"password\":\"pas\"}" http://localhost:8080/user/add

### authenticate user by getting otp(one-time pass)
$ curl -XPOST -H "content-type: application/json" -d "{\"username\":\"den\", \"password\":\"pas\"}" http://localhost:8080/user/auth

### check otp
$ curl -s -v -XPOST -H "content-type: application/json" -d "{\"username\":\"den\", \"code\":\"8680\"}" http://localhost:8080/otp/check






