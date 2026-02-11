package com.example.jwt.repository;

import com.example.jwt.entity.AccessToken;
import com.example.jwt.entity.User;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessTokenRepository extends JpaRepository<AccessToken, Long> {
  @Query("select a from access_token a where a.user.email = ?1")
  Optional<AccessToken> findByUser_Email(String email);

  @Query(
      "select (count(a) > 0) from access_token a where a.accessToken = ?1 and a.user = ?2 and"
          + " a.expiresAt > ?3")
  boolean existsByAccessTokenAndUserAndExpiresAtGreaterThan(
      String accessToken, User user, LocalDateTime expiresAt);
}
