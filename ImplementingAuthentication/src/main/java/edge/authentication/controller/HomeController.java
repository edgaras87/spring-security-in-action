package edge.authentication.controller;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.scheduling.annotation.Async;
import org.springframework.security.concurrent.DelegatingSecurityContextCallable;
import org.springframework.security.concurrent.DelegatingSecurityContextExecutorService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class HomeController {
	
	// SecurityContextHolder.getContext() MODE_THREADLOCAL
	
	@GetMapping(path = "/home1")
	public String home() {
		
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication a = context.getAuthentication();
		return "Hello " + a.getName() + "!!! This is authentication project.";
	}
	
	@GetMapping(path = "/home2")
	public String home(Authentication a) {
		return "Hello " + a.getName() + "!!! This is authentication project.";
	}	
	
	// SecurityContextHolder.getContext() MODE_INHERITABLETHREADLOCAL
	
	// @Async, the method is executed on a separate thread.
	@GetMapping(path = "/bye")
	@Async
	public String bye() {
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication a = context.getAuthentication();
		return "bye bye " + a.getName() + "...";
	}
	
	// DelegatingSecurityContextCallable
	
	@GetMapping(path = "/ciao")
	public String ciau() throws Exception {
		Callable<String> task = () -> {
			SecurityContext context = SecurityContextHolder.getContext();
			return context.getAuthentication().getName();
		};
		
		ExecutorService e = Executors.newCachedThreadPool();
		
		
		try {
			/**
			 *  NullPointerException!!! Inside the newly created thread
			 *  	to run the callable task, the authentication does not exist
			 *  	anymore, and the security context is empty
			 */  
			//return "Ciao, " + e.submit(task).get() + "!";
			
			var contextTask = new DelegatingSecurityContextCallable<>(task);
			return "Ciao, " + e.submit(contextTask).get() + "!";
		} finally {
			e.shutdown();
		}
	}
	
	// DelegatingSecurityContextExecutorService
	
	@GetMapping("/hola")
	public String hole() throws Exception {
 		Callable<String> task = () -> {
 			SecurityContext context = SecurityContextHolder.getContext();
 			return context.getAuthentication().getName();
 		};
 		
 		ExecutorService e = Executors.newCachedThreadPool();
 		e = new DelegatingSecurityContextExecutorService(e);
 		
 		try {
			return "Hola, " + e.submit(task).get() + "!";
		} finally {
			e.shutdown();
		}
	}
	
}
