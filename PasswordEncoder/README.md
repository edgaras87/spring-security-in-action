# Notes

## Encoding vs. encrypting vs. hashing

### encoding
Encoding refers to any transformation of a given input. For example, if we have a function x that 
reverses a string, function x -> y applied to ABCD produces DCBA.

### encrypting
Encryption is a particular type of encoding where, to obtain the output, you
provide both the input value and a key. The key makes it possible for
choosing afterward who should be able to reverse the function (obtain the
input from the output). The simplest form of representing encryption as a
function looks like this:
(x, k) -> y
where x is the input, k is the key, and y is the result of the encryption

### dencrypting
Decryption is the reverse function for obtaining the input from the output (y, k) -> x. 

### symmetric key
If the key used for encryption is the same as the one used for decryption, we usually call it a symmetric key
### asymmetric keys
If we have two different keys for encryption ((x, k1) -> y) and decryption
((y, k2) -> x), then we say that the encryption is done with asymmetric
keys. Then (k1, k2) is called a key pair. The key used for encryption, k1, is
also referred to as the public key, while k2 is known as the private one. This
way, only the owner of the private key can decrypt the data.

### hashing
Hashing is a particular type of encoding, except the function is only one
way. That is, from an output y of the hashing function, you cannot get back
the input x. However, there should always be a way to check if an output y
corresponds to an input x, so we can understand the hashing as a pair of
functions for encoding and matching. If hashing is x -> y, then we should
also have a matching function (x,y) -> boolean.
Sometimes the hashing function could also use a random value added to
the input: (x, k) -> y. We refer to this value as the salt. The salt makes
the function stronger, enforcing the difficulty of applying a reverse function
to obtain the input from the result