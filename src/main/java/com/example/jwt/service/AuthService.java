package com.example.jwt.service;

import com.example.jwt.entity.AccessToken;
import com.example.jwt.entity.Role;
import com.example.jwt.entity.Roles;
import com.example.jwt.entity.User;
import com.example.jwt.entity.jwt_related.UserDetailsImpl;
import com.example.jwt.exception.AccessTokenExpiredException;
import com.example.jwt.exception.UserAlreadyCreatedException;
import com.example.jwt.exception.UserNotFoundException;
import com.example.jwt.request.LoginRequest;
import com.example.jwt.request.RegisterRequest;
import com.example.jwt.response.DefaultResponse;
import com.example.jwt.response.JwtDetailsResponse;
import com.example.jwt.service.jwt.JwtUtils;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

  @Autowired private PasswordEncoder encoder;
  @Autowired private AccessTokenService accessTokenService;
  @Autowired private AuthenticationManager authenticationManager;
  @Autowired private UserService userService;
  @Autowired private JwtUtils jwtUtils;
  @Autowired private RoleService roleService;

  @Transactional
  public DefaultResponse register(RegisterRequest request) throws Exception {
    boolean exists = userService.existsByUsernameOrEmail(request.username(), request.email());
    if (exists) {
      throw new UserAlreadyCreatedException(
          "User is already created on those credentials [username/email]");
    }

    Role userRole = roleService.findRoleByName(Roles.ROLE_USER);
    User newUser =
        User.builder()
            .username(request.username())
            .email(request.email())
            .password(encoder.encode(request.password()))
            .roles(Set.of(userRole))
            .createdAt(LocalDate.now())
            .updatedAt(LocalDate.now())
            .build();

    AccessToken accessToken = accessTokenService.create(newUser);
    userService.saveUser(newUser);

    String jwtToken = jwtUtils.generateJwtToken(newUser.getEmail());
    String accessTokenStr = accessToken.getAccessToken();

    return new DefaultResponse(
        null, "Authenticated successfully", new JwtDetailsResponse(accessTokenStr, jwtToken));
  }

  public DefaultResponse login(LoginRequest loginRequest) throws Exception {
    Authentication authentication;
    try {
      authentication =
          authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                  loginRequest.email(), loginRequest.password()));
    } catch (Exception ex) {
      // throwing basic exception because i dont want user to know which specific error he got.
      throw new UserNotFoundException("Authentication failed for these credentials");
    }
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwtToken = jwtUtils.generateJwtToken(authentication);
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    String accessTokenStr =
        accessTokenService.updateAccessToken(userDetails.getUsername()).getAccessToken();

    return new DefaultResponse(
        null, "successfully authenticated", new JwtDetailsResponse(accessTokenStr, jwtToken));
  }

  @Transactional
  public DefaultResponse getJwtTokenFromRefreshToken(
      UserDetailsImpl userDetails, String oldAccessToken) throws Exception {
    User user = userService.findUser(userDetails.getUsername());
    boolean isAccessTokenNotExpired =
        accessTokenService.isAccessTokenNotExpired(oldAccessToken, user, LocalDateTime.now());
    if (!isAccessTokenNotExpired) {
      throw new AccessTokenExpiredException("Access token is expired or User is not right");
    }

    String jwtToken = jwtUtils.generateJwtToken(user.getEmail());
    String accessTokenStr = accessTokenService.updateAccessToken(user.getEmail()).getAccessToken();

    return new DefaultResponse(
        null, "JWT Token obtained successfully", new JwtDetailsResponse(accessTokenStr, jwtToken));
  }
}
