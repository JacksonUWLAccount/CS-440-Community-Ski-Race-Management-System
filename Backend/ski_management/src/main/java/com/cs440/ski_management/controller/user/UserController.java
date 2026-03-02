package com.cs440.ski_management.controller.user;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.cs440.ski_management.dao.user.User;


@RestController
@RequestMapping("/api")
public class UserController {
	
	private final User userDAO = new User();	
	public record loginRequest(String username, String password) {}
	public record SignupRequest(String username, String email, String password, String firstName, String lastName) {}

	@PostMapping("/login")
	public ResponseEntity<Boolean> login(@RequestBody loginRequest req){
		return ResponseEntity.ok(userDAO.login(req.username(), req.password));
	}
	
	@PostMapping("/signup")
	public ResponseEntity<Boolean> signup(@RequestBody SignupRequest req){
		return ResponseEntity.ok(userDAO.signup(req.username(),req.email(), req.password,req.firstName(), req.lastName));
	}	
}
