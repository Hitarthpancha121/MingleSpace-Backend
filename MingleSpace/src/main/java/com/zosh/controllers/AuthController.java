package com.zosh.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import com.zosh.config.JwtProvider;
import com.zosh.models.User;
import com.zosh.repository.UserRepository;
import com.zosh.request.LoginRequest;
import com.zosh.response.AuthResponse;
import com.zosh.service.CustomerUserDetailsService;
import com.zosh.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private CustomerUserDetailsService customerUserDetails;

	// the api is like /auth/signup
	
	@PostMapping("/signup")
	public AuthResponse createUser(@RequestBody User user) throws Exception {
		
			User isExist=userRepository.findByEmail(user.getEmail());
			if(isExist!=null) {
				throw new Exception ("Email Already Used with another Account");
			}
		
			User newUser=new User();
         
			newUser.setEmail(user.getEmail());
			newUser.setFirstName(user.getFirstName());
			newUser.setLastName(user.getLastName());
			newUser.setPassword(passwordEncoder.encode(user.getPassword()));
			
			User savedUser=userRepository.save(newUser);
			
			Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(),savedUser.getPassword());
			
			String token=JwtProvider.genrateToken(authentication);
			
			AuthResponse res=new AuthResponse(token,"Register Success ");
			
			return res;
	}
	
	@PostMapping("/signin")
	public AuthResponse signin(@RequestBody LoginRequest loginRequest) {
		Authentication authentication =authenticate(loginRequest.getEmail(),loginRequest.getPassword());
		String token=JwtProvider.genrateToken(authentication);
		
		AuthResponse res=new AuthResponse(token,"Login Success ");
		
		return res;
	}

	private Authentication authenticate(String email, String password) {
		UserDetails userDetails=customerUserDetails.loadUserByUsername(email);
		
		if(userDetails==null) {
			throw new BadCredentialsException("Invalid Username");
		}
		if(!passwordEncoder.matches( password,userDetails.getPassword())) {
			throw new BadCredentialsException("Password not Matched");
		}
		return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities()  );
	}
}
