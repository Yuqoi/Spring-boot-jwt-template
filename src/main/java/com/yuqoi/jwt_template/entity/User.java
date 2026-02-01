package com.yuqoi.jwt_template.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class User {
	@Id
	private Long id;

	private String username;
	private String email;
	private String password;
	private LocalDate createdAt;
}

