
package com.example.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.jwt.entity.AccessToken;

@Repository
public interface AccessTokenRepository extends JpaRepository<AccessToken, Long> {

}
