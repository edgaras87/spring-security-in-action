package edge.project3.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edge.project3.data.OtpRepository;
import edge.project3.data.UserRepository;
import edge.project3.module.Otp;
import edge.project3.module.User;
import edge.project3.util.GenerateCodeUtil;


@Service
public class UserService {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OtpRepository otpRepository;
	
	
	public void addUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}
	
	public void auth(User user) {
		Optional<User> o = userRepository.findUserByUsername(user.getUsername());
		
		if (o.isPresent()) {
			User u = o.get();
			if (passwordEncoder.matches(user.getPassword(), u.getPassword())) {
				renewOtp(user);
			} else {
				throw new BadCredentialsException("Bad credentials.");
			}
		}
		
	}
	
	private void renewOtp(User user) {
		String code = GenerateCodeUtil.generateCode();
		Optional<Otp> userOtp = otpRepository.findOtpByUsername(user.getUsername());
		
		if (userOtp.isPresent()) {
			Otp otp = userOtp.get();
			otp.setCode(code);
		} else {
			Otp otp = new Otp();
			otp.setUsername(user.getUsername());
			otp.setCode(code);
			otpRepository.save(otp);
		}
	}
	
	public boolean check(Otp otpToValidate) {
		
		Optional<Otp> userOtp = otpRepository.findOtpByUsername(otpToValidate.getUsername());
		if (userOtp.isPresent()) {
			Otp otp = userOtp.get();
			if (otpToValidate.getCode().equals(otp.getCode())) {
				return true;
			}
		}
		
		return false;
	}
	
}
