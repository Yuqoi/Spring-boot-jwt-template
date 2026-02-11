
package com.example.jwt.dto;

import java.time.LocalDate;

public record UserDto(String username, LocalDate createdAt) {
}
