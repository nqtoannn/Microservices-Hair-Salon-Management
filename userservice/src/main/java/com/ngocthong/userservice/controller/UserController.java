package com.ngocthong.userservice.controller;

import java.io.IOException;

import com.ngocthong.userservice.model.AuthenticationRequest;
import com.ngocthong.userservice.model.AuthenticationResponse;
import com.ngocthong.userservice.model.RegisterRequest;
import com.ngocthong.userservice.model.ResponseObject;
import com.ngocthong.userservice.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = "Authorization, Content-Type")
@RestController
@RequestMapping("/api/v1/auth")
public class UserController {

	@Autowired
	private final AuthenticationService service;

    public UserController(AuthenticationService service) {
        this.service = service;
    }

    @PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> register(
			@RequestBody RegisterRequest request
	) {
		return ResponseEntity.ok(service.register(request));
	}

	@PostMapping("/addEmployee")
	public ResponseEntity<AuthenticationResponse> addEmployee(
			@RequestBody String json
	) {
		return ResponseEntity.ok(service.addEmployee(json));
	}

	@PostMapping("/authenticate")
	public ResponseEntity<AuthenticationResponse> authenticate(
			@RequestBody AuthenticationRequest request
	) {
		return ResponseEntity.ok(service.authenticate(request));
	}

	@PutMapping("/updateStatusUser")
	public ResponseEntity<Object> updateUserStatus(
			@RequestBody String json
	) {
		return ResponseEntity.ok(service.updateStatusUser(json));
	}

	@PutMapping("/updateUserProfile")
	public ResponseEntity<Object> updateUserProfile(
			@RequestBody String json
	) {
		return ResponseEntity.ok(service.updateUserProfile(json));
	}



	@PostMapping("/refresh-token")
	public void refreshToken(
			HttpServletRequest request,
			HttpServletResponse response
	) throws IOException {
		service.refreshToken(request, response);
	}
}