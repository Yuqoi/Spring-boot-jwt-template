package com.example.jwt.service;

import com.example.jwt.entity.AccessToken;
import com.example.jwt.entity.Role;
import com.example.jwt.entity.Roles;
import com.example.jwt.entity.User;
import com.example.jwt.repository.AccessTokenRepository;
import com.example.jwt.repository.RoleRepository;
import com.example.jwt.repository.UserRepository;
import com.example.jwt.request.RegisterRequest;
import com.example.jwt.response.JwtDetailsResponse;
import com.example.jwt.response.RegisterResponse;
import com.example.jwt.service.jwt.JwtUtils;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccessTokenRepository accessTokenRepository;

    @Autowired
    private JwtUtils jwtUtils;

    public RegisterResponse register(RegisterRequest request) {

        Optional<Role> optionalRole = roleRepository.findByRoleName(Roles.ROLE_USER);
        if (optionalRole.isEmpty()) {
            throw new RuntimeException("Role not found");
        }
        Role userRole = optionalRole.get();

        User newUser = User.builder()
                .username(request.username())
                .email(request.email())
                .password(encoder.encode(request.password()))
                .roles(Set.of(userRole))
                .createdAt(LocalDate.now())
                .updatedAt(LocalDate.now())
                .build();

        AccessToken accessToken = AccessToken.createAccessToken(newUser);

        userRepository.save(newUser);
        accessTokenRepository.save(accessToken);

        String jwtToken = jwtUtils.generateJwtToken(newUser.getEmail());
        String accessTokenStr = accessToken.getAccessToken();

        return new RegisterResponse(
                null, "Authenticated successfully", new JwtDetailsResponse(
                        accessTokenStr,
                        jwtToken));
    }

}
