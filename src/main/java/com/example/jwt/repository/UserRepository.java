package com.example.jwt.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.jwt.dto.UserDto;
import com.example.jwt.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	@Query("""
				select
					new com.example.jwt.dto.UserDto(u.username, u.createdAt)
				from users u
			""")
	public List<UserDto> getUsers();

	public Optional<User> findByEmail(String email);

	boolean existsByUsernameOrEmail(String username, String email);
}
