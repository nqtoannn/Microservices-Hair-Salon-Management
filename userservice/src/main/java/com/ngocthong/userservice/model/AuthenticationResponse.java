package com.ngocthong.userservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ngocthong.userservice.data.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

	@JsonProperty("access_token")
	private String accessToken;
	@JsonProperty("refresh_token")
	private String refreshToken;
	private String role;
	private String userId;
	private String userName;

}
