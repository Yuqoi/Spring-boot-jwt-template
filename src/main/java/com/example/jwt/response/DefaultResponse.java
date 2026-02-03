
package com.example.jwt.response;

import java.util.Optional;

import lombok.Getter;

public record DefaultResponse(
		Optional<String> error,
		String message,
		Optional<Object> details) {
}
