package com.ngocthong.userservice.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.ngocthong.userservice.Enum.Role;
import com.ngocthong.userservice.Enum.TokenType;
import com.ngocthong.userservice.data.Token;
import com.ngocthong.userservice.data.TokenRepository;
import com.ngocthong.userservice.data.User;
import com.ngocthong.userservice.data.UserRepository;
import com.ngocthong.userservice.model.AuthenticationRequest;
import com.ngocthong.userservice.model.AuthenticationResponse;
import com.ngocthong.userservice.model.RegisterRequest;
import com.ngocthong.userservice.model.ResponseObject;
import com.ngocthong.userservice.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	private final UserRepository repository;
	private final TokenRepository tokenRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	private final EmailSendService emailSendService;

	public AuthenticationResponse register(RegisterRequest request) {
		var user = User.builder()
				.id(UUID.randomUUID().toString())
				.userName(request.getUserName())
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.role(Role.valueOf(request.getRole()))
				.status("OK")
				.phoneNumber("null")
				.address("null")
				.fullName("null")
				.build();
		var savedUser = repository.save(user);
		var jwtToken = jwtService.generateToken(user);
		var refreshToken = jwtService.generateRefreshToken(user);
		saveUserToken(savedUser, jwtToken);
		return AuthenticationResponse.builder()
				.role(savedUser.getRole().toString())
				.userId(savedUser.getId())
				.userName(savedUser.getUsername())
				.accessToken(jwtToken)
				.refreshToken(refreshToken)
				.build();
	}


	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.getEmail(),
						request.getPassword()
				)
		);
		var user = repository.findByEmail(request.getEmail());
		if (user.getStatus().equals("NOT_ACTIVE")) {
			return AuthenticationResponse.builder()
					.accessToken("")
					.refreshToken("")
					.role("")
					.build();
		}

		var jwtToken = jwtService.generateToken(user);
		var refreshToken = jwtService.generateRefreshToken(user);
		revokeAllUserTokens(user);
		saveUserToken(user, jwtToken);
		return AuthenticationResponse.builder()
				.accessToken(jwtToken)
				.refreshToken(refreshToken)
				.role(String.valueOf(user.getRole()))
				.userId(user.getId())
				.userName(user.getFullName())
				.build();
	}

	private void saveUserToken(User user, String jwtToken) {
		var token = Token.builder()
				.user(user)
				.token(jwtToken)
				.tokenType(TokenType.BEARER)
				.expired(false)
				.revoked(false)
				.build();
		tokenRepository.save(token);
	}

	private void revokeAllUserTokens(User user) {
		var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
		if (validUserTokens.isEmpty())
			return;
		validUserTokens.forEach(token -> {
			token.setExpired(true);
			token.setRevoked(true);
		});
		tokenRepository.saveAll(validUserTokens);
	}

	public void refreshToken(
			HttpServletRequest request,
			HttpServletResponse response
	) throws IOException {
		final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		final String refreshToken;
		final String userEmail;
		if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
			return;
		}
		refreshToken = authHeader.substring(7);
		userEmail = jwtService.extractUsername(refreshToken);
		if (userEmail != null) {
			var user = this.repository.findByEmail(userEmail);
			if (jwtService.isTokenValid(refreshToken, user)) {
				var accessToken = jwtService.generateToken(user);
				revokeAllUserTokens(user);
				saveUserToken(user, accessToken);
				var authResponse = AuthenticationResponse.builder()
						.accessToken(accessToken)
						.refreshToken(refreshToken)
						.build();
				new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
			}
		}
	}

	public AuthenticationResponse addEmployee(String json) {
		JsonNode jsonNode;
		JsonMapper jsonMapper = new JsonMapper();
		try {
			jsonNode = jsonMapper.readTree(json);
			Random random = new Random();
			var password = String.format("%08d", random.nextInt(100000000));
			String fullName = jsonNode.get("fullName") != null ? jsonNode.get("fullName").asText() : "null";
			String email = jsonNode.get("email") != null ? jsonNode.get("email").asText() : "null";
			String address = jsonNode.get("address") != null ? jsonNode.get("address").asText() : null;
			String phoneNumber = jsonNode.get("phoneNumber") != null ? jsonNode.get("phoneNumber").asText() : "null";
			String username = jsonNode.get("username") != null ? jsonNode.get("username").asText() : "null";

			var user = User.builder()
					.id(UUID.randomUUID().toString())
					.userName(username)
					.email(email)
					.password(passwordEncoder.encode(password))
					.role(Role.EMPLOYEE)
					.address(address)
					.fullName(fullName)
					.phoneNumber(phoneNumber)
					.status("OK")
					.build();
			var savedUser = repository.save(user);
			if (savedUser.getPassword() != null) {
				String[] cc = {};
				Map<String, Object> model = new HashMap<>();
				model.put("userName", savedUser.getUsername());
				model.put("password", password);
				emailSendService.sendMail(savedUser.getEmail(), cc, "Tài khoản truy cập Hair Salon của bạn đã được tạo", model);
			}
			var jwtToken = jwtService.generateToken(user);
			var refreshToken = jwtService.generateRefreshToken(user);
			saveUserToken(savedUser, jwtToken);
			return AuthenticationResponse.builder()
					.accessToken(jwtToken)
					.refreshToken(refreshToken)
					.build();
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	public ResponseEntity<Object> updateStatusUser(@RequestBody String json) {
		JsonNode jsonNode;
		JsonMapper jsonMapper = new JsonMapper();
		try {
			jsonNode = jsonMapper.readTree(json);
			String id = jsonNode.get("id") != null ? jsonNode.get("id").asText() : "";
			String status = jsonNode.get("status") != null ? jsonNode.get("status").asText() : "";

			Optional<User> userOptional = repository.findById(id);
			if (userOptional.isPresent()) {
				User updatedUser = userOptional.get();
				updatedUser.setStatus(status);
				repository.save(updatedUser);
				return ResponseEntity.status(HttpStatus.OK)
						.body(new ResponseObject("OK", "Successfully", ""));
			}

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject("Error", e.getMessage(), ""));
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseObject("ERROR", "Something went wrong", ""));
	}

	public ResponseEntity<Object> updateUserProfile(@RequestBody String json) {
		JsonNode jsonNode;
		JsonMapper jsonMapper = new JsonMapper();
		try {
			jsonNode = jsonMapper.readTree(json);
			String id = jsonNode.get("id") != null ? jsonNode.get("id").asText() : "";
			String fullName = jsonNode.get("fullName") != null ? jsonNode.get("fullName").asText() : "";
			String phoneNumber = jsonNode.get("phoneNumber") != null ? jsonNode.get("phoneNumber").asText() : "";
			String address = jsonNode.get("address") != null ? jsonNode.get("address").asText() : "";

			Optional<User> userOptional = repository.findById(id);
			if (userOptional.isPresent()) {
				User updatedUser = userOptional.get();
				updatedUser.setFullName(fullName);
				updatedUser.setPhoneNumber(phoneNumber);
				updatedUser.setAddress(address);
				repository.save(updatedUser);
				return ResponseEntity.status(HttpStatus.OK)
						.body(new ResponseObject("OK", "Successfully", ""));
			}

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject("Error", e.getMessage(), ""));
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseObject("ERROR", "Something went wrong", ""));
	}


}