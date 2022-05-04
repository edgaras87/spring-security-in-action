# Notes

## principal
The user requesting access to the application is called a principal.

## security context
The instance storing the Authentication object is called the security context. Once
the AuthenticationManager completes the authentication process successfully, it stores the
Authentication instance for the rest of the request.

## SecurityContext management
Spring Security offers three strategies to manage the SecurityContext with an object in the role of a manager. 
It’s named the SecurityContextHolder.

### MODE_THREADLOCAL
Allows each thread to store its own details in the security context. In a threadper-request web application, 
this is a common approach as each request has an individual thread.

### MODE_INHERITABLETHREADLOCAL
Similar to MODE_THREADLOCAL but also instructs Spring Security to copy the security context to the next thread in case 
of an asynchronous method. This way, we can say that the new thread running the @Async method inherits the security context.

### MODE_GLOBAL
Makes all the threads of the application see the same security context instance

All the threads access the same security context. This implies that these all
have access to the same data and can change that information. Because of this, race conditions can
occur, and you have to take care of synchronization.

## DelegatingSecurityContextRunnable and DelegatingSecurityContextCallable. 
These classes decorate the tasks you execute asynchronously and also take 
the responsibility to copy the details from security context such that your 
implementation can access those from the newly created thread. 

If you have a return value, then you can use the Callable<T>, which is
DelegatingSecurityContextCallable<T>

Both classes represent tasks executed asynchronously, as any other Runnable or Callable


## Forwarding the security context with Executor
An alternative to decorating tasks is to use a particular type of Executor
to deal with the security context propagation to a new thread, 
and manage propagation from the thread pool instead of from the task itself.

### DelegatingSecurityContextExecutor
Implements the Executor interface and is designed to decorate an Executor object with
the capability of forwarding the security context to the threads created by its pool.

### DelegatingSecurityContextExecutorService
Implements the ExecutorService interface and is designed to decorate an ExecutorService
object with the capability of forwarding the security context to the threads created by its
pool.

### DelegatingSecurityContextScheduledExecutorService
Implements the ScheduledExecutorService interface and is designed to decorate a 
ScheduledExecutorService object with the capability of forwarding the security context
to the threads created by its pool.

### DelegatingSecurityContextRunnable
Implements the Runnable interface and represents a task that is executed on a
different thread without returning a response. Above a normal Runnable, it is also
able to propagate a security context to use on the new thread.

### DelegatingSecurityContextCallable
Implements the Callable interface and represents a task that is executed on a
different thread and that will eventually return a response. Above a normal Callable, it
is also able to propagate a 

# Form-based login authentications

## AuthenticationSuccessHandler and AuthenticationFailureHandler 
These interfaces let you implement an object through which you can apply the logic
executed for authentication.







