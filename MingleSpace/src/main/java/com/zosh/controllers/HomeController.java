package com.zosh.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
	
	@GetMapping
	public String homeControllerHandler() {
		
		return "This is a Home Controller";
	}
	
	@GetMapping("/home")
	public String homeControllerHandler2() {
		return "This is Second Home Controller";
	}
	
	@GetMapping("/welcome")
	public String WelcomeHandler() {
		return "Welcome Mr. Hitarth Panchal ";
	}
}
