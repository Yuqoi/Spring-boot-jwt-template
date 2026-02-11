package com.example.jwt.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.jwt.entity.User;
import com.example.jwt.request.LoginRequest;
import com.example.jwt.service.UserService;
import com.example.jwt.utils.ObjectToStringConverter;
import java.time.LocalDate;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.TestcontainersConfiguration;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
public class LoginIntegrationTest {
  @Autowired MockMvc mockMvc;

  @Autowired private UserService userService;

  @Autowired private PasswordEncoder passwordEncoder;

  @BeforeEach
  void setup() throws Exception {
    userService.deleteAllUsers();

    userService.saveUser(
        User.builder()
            .username("Yuqoi")
            .email("email@gmail.com")
            .createdAt(LocalDate.now())
            .updatedAt(LocalDate.now())
            .password(passwordEncoder.encode("123456"))
            .build());
  }

  @Test
  void shouldLoginToAccount() throws Exception {
    LoginRequest request = new LoginRequest("email@gmail.com", "123456");
    mockMvc
        .perform(
            post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ObjectToStringConverter.asJsonString(request)))
        .andExpect(status().isOk());
  }

  @Test
  void shouldReturnErrorForWrongData() throws Exception {
    LoginRequest request = new LoginRequest("emailasda@gmail.com", "123456");
    mockMvc
        .perform(
            post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ObjectToStringConverter.asJsonString(request)))
        .andExpect(status().isNotFound());
  }

  @Test
  void shouldReturnExceptionForNullBody() throws Exception {
    LoginRequest request = new LoginRequest(null, null);
    mockMvc
        .perform(
            post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ObjectToStringConverter.asJsonString(request)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.details").value(IsNull.nullValue()));
  }
}
