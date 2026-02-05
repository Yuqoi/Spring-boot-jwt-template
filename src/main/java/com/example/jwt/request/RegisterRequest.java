package com.example.jwt.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
		@NotBlank(message = "Username is required") @Size(max = 30) String username,
		@Email @NotBlank(message = "Email is required") String email,
		@NotBlank(message = "Password is required") @Size(max = 32) String password) {
}
