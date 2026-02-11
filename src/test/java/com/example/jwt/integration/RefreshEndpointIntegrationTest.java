package com.example.jwt.integration;

import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.jwt.entity.User;
import com.example.jwt.request.LoginRequest;
import com.example.jwt.service.UserService;
import com.example.jwt.utils.ObjectToStringConverter;
import com.jayway.jsonpath.JsonPath;
import java.time.LocalDate;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.TestcontainersConfiguration;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@Slf4j
public class RefreshEndpointIntegrationTest {
  @Autowired private MockMvc mockMvc;
  @Autowired private UserService userService;
  @Autowired private PasswordEncoder passwordEncoder;

  private String jwtToken = null;
  private String accessToken = null;

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
    LoginRequest request = new LoginRequest("email@gmail.com", "123456");
    MvcResult result =
        mockMvc
            .perform(
                post("/api/v1/auth/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(ObjectToStringConverter.asJsonString(request)))
            .andReturn();

    String response = result.getResponse().getContentAsString();
    jwtToken = JsonPath.parse(response).read("$.details.jwtToken");
    accessToken = JsonPath.parse(response).read("$.details.accessToken");
    assumeTrue(jwtToken != null);
    assumeTrue(accessToken != null);
  }

  @Test
  void shouldReturnNewAccessToken() throws Exception {
    String oldAccessToken = accessToken;
    mockMvc
        .perform(
            get("/api/v1/auth/refresh")
                .header("authorization", "Bearer " + jwtToken)
                .param("token", accessToken))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }
}
