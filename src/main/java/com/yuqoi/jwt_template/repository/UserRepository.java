package com.yuqoi.jwt_template.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.yuqoi.jwt_template.dto.UserDto;
import com.yuqoi.jwt_template.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@Query("""
		select
			new com.yuqoi.jwt_template.dto.UserDto(u.username, u.createdAt)
		from users u
	""")
	List<UserDto> findUsers();

}
