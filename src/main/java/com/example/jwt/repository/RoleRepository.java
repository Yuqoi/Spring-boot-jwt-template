
package com.example.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jwt.entity.Role;
import com.example.jwt.entity.Roles;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Optional<Role> findByRoleName(Roles roleName);
}
