
package com.example.jwt.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
		@Email @NotBlank(message = "Email is required") String email,
		@NotBlank(message = "Password is required") @Size(min = 6, max = 32, message = "Password has to have 6-32 range") String password) {
}
