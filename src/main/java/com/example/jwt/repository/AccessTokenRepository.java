
package com.example.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.config.web.server.ServerHttpSecurity.AuthorizeExchangeSpec.Access;
import org.springframework.stereotype.Repository;

import com.example.jwt.entity.AccessToken;

import java.util.Optional;

@Repository
public interface AccessTokenRepository extends JpaRepository<AccessToken, Long> {
	@Query("select a from access_token a where a.user.email = ?1")
	Optional<AccessToken> findByUser_Email(String email);
}
