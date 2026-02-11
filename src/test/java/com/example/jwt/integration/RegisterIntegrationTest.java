package com.example.jwt.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.jwt.repository.UserRepository;
import com.example.jwt.request.RegisterRequest;
import com.example.jwt.response.DefaultResponse;
import com.example.jwt.utils.ObjectToStringConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.TestcontainersConfiguration;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@Slf4j
public class RegisterIntegrationTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private UserRepository userRepository;

  @BeforeEach
  void setup() {
    userRepository.deleteAll();
  }

  @Test
  void shouldCreateAnAccountAndReturnJwtToken() throws Exception {
    RegisterRequest request = new RegisterRequest("Yuqoi", "email@gmail.com", "test123");

    MvcResult res =
        mockMvc
            .perform(
                post("/api/v1/auth/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(ObjectToStringConverter.asJsonString(request)))
            .andExpect(status().isOk())
            .andReturn();

    String responseBody = res.getResponse().getContentAsString();
    ObjectMapper mapper = new ObjectMapper();
    DefaultResponse gottenResponse = mapper.readValue(responseBody, DefaultResponse.class);

    assertInstanceOf(DefaultResponse.class, gottenResponse);
    assertNull(gottenResponse.error());
  }

  @Test
  void shouldReturnErrorOfDuplicatedEmail() throws Exception {
    RegisterRequest request = new RegisterRequest("Yuqoi", "email@gmail.com", "test123");
    mockMvc
        .perform(
            post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ObjectToStringConverter.asJsonString(request)))
        .andExpect(status().isOk());

    RegisterRequest request2 = new RegisterRequest("Yuqosdas", "email@gmail.com", "test123");
    MvcResult result =
        mockMvc
            .perform(
                post("/api/v1/auth/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(ObjectToStringConverter.asJsonString(request2)))
            .andExpect(status().isNotFound())
            .andReturn();

    String responseBody = result.getResponse().getContentAsString();
    ObjectMapper mapper = new ObjectMapper();
    DefaultResponse gottenResponse = mapper.readValue(responseBody, DefaultResponse.class);

    assertInstanceOf(DefaultResponse.class, gottenResponse);
    assertEquals(
        "User is already created on those credentials [username/email]", gottenResponse.message());
  }

  @Test
  void shouldReturnErrorForDuplicatedUsername() throws Exception {
    RegisterRequest request = new RegisterRequest("Yuqoi", "email@gmail.com", "test123");
    mockMvc
        .perform(
            post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ObjectToStringConverter.asJsonString(request)))
        .andExpect(status().isOk());

    RegisterRequest request2 = new RegisterRequest("Yuqoi", "teeeest@gmail.com", "test123");
    MvcResult result =
        mockMvc
            .perform(
                post("/api/v1/auth/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(ObjectToStringConverter.asJsonString(request2)))
            .andExpect(status().isNotFound())
            .andReturn();

    String responseBody = result.getResponse().getContentAsString();
    ObjectMapper mapper = new ObjectMapper();
    DefaultResponse gottenResponse = mapper.readValue(responseBody, DefaultResponse.class);

    assertInstanceOf(DefaultResponse.class, gottenResponse);
    assertEquals(
        "User is already created on those credentials [username/email]", gottenResponse.message());
  }
}
