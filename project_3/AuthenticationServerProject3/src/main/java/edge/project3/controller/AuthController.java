package edge.project3.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edge.project3.module.Otp;
import edge.project3.module.User;
import edge.project3.service.UserService;

@RestController
public class AuthController {

	@Autowired
	private UserService userService;
	
	@PostMapping(path = "/user/add")
	public void addUser(@RequestBody User user) {
		userService.addUser(user);
	}
	
	@PostMapping(path = "/user/auth")
	public void auth(@RequestBody User user) {
		userService.auth(user);
	}
	
	@PostMapping(path = "/otp/check")
	public void check(@RequestBody Otp otp, HttpServletResponse response) {
		if (userService.check(otp)) {
			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		}
	}
	
	
}
