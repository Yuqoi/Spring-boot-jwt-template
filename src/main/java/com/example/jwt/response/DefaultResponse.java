
package com.example.jwt.response;

import java.util.Optional;

public record DefaultResponse(
		String error,
		String message,
		Object details) {
}
