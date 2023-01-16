package com.user.controller;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import com.user.entity.User;
import com.user.services.UserService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	private Logger logger= LoggerFactory.getLogger(UserController.class);
	
	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody User user){
		User user1= userService.saveUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(user1);
	}
	
	@GetMapping("/{userId}")
	@CircuitBreaker(name="ratingHotelBreaker",fallbackMethod="ratingHotelFallback")
	public ResponseEntity<User> getSingleUser(@PathVariable String userId){
		User allUser = userService.getUser(userId);
		return ResponseEntity.ok(allUser);
	}
	 
	public ResponseEntity<User> ratingHotelFallback(String userId, Exception ex){
		logger.info("Fallback is execuited becouse some service is down: ",ex.getMessage());
		User user = User
				    .builder()
				    .email("dummy@gmail.com")
				    .name("dummy")
				    .about("This is dummy user because some service is down")
				    .userId("12345")
				    .build();
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<User>> getAllUser(){
		List<User> allUser = userService.getAllUser();
		return ResponseEntity.ok(allUser);
	}


}
