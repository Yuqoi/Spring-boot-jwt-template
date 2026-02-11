package com.example.jwt.controller;

import com.example.jwt.entity.jwt_related.UserDetailsImpl;
import com.example.jwt.request.LoginRequest;
import com.example.jwt.request.RegisterRequest;
import com.example.jwt.response.DefaultResponse;
import com.example.jwt.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

  @Autowired private AuthService authService;

  @PostMapping("/register")
  public ResponseEntity<DefaultResponse> register(
      @RequestBody @Valid RegisterRequest registerRequest) throws Exception {
    return ResponseEntity.ok(authService.register(registerRequest));
  }

  @PostMapping("/login")
  public ResponseEntity<DefaultResponse> login(@RequestBody @Valid LoginRequest loginRequest)
      throws Exception {
    return ResponseEntity.ok(authService.login(loginRequest));
  }

  @GetMapping("/refresh")
  public ResponseEntity<DefaultResponse> getRefreshToken(
      @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam String token)
      throws Exception {
    return ResponseEntity.ok(authService.getJwtTokenFromRefreshToken(userDetails, token));
  }
}
