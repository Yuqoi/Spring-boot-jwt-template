
package com.example.jwt.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.jwt.entity.AccessToken;
import com.example.jwt.entity.User;
import com.example.jwt.repository.AccessTokenRepository;

@Service
public class AccessTokenService {

    @Autowired
    private AccessTokenRepository repository;

    @Autowired
    private UserService userService;

    public AccessToken create(User user) {
        AccessToken at = new AccessToken(
                null,
                UUID.randomUUID().toString(),
                user, LocalDateTime.now().plusHours(1L));
        user.setAccessToken(at);
        return at;
    }

    public AccessToken create(String email) {
        User user = userService.findUser(email);
        return create(user);
    }

    public AccessToken updateAccessToken(String email) {
        Optional<AccessToken> optional = repository.findByUser_Email(email);

        if (optional.isEmpty()) {
            return create(email);
        }

        AccessToken accessToken = optional.get();
        accessToken.setAccessToken(UUID.randomUUID().toString());
        accessToken.setExpiresAt(LocalDateTime.now().plusHours(1L));
        repository.save(accessToken);

        return accessToken;
    }

    public void saveAccessToken(AccessToken ac) {
        repository.save(ac);
    }
}
