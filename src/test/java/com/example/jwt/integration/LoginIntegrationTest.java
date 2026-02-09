package com.example.jwt.integration;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.core.IsNull;
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

import com.example.jwt.repository.UserRepository;
import com.example.jwt.request.LoginRequest;
import com.example.jwt.request.RegisterRequest;
import com.example.jwt.utils.ObjectToStringConverter;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
public class LoginIntegrationTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setup() throws Exception {
        userRepository.deleteAll();

        RegisterRequest request = new RegisterRequest("Yuqoi", "email@gmail.com", "123456");
        mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ObjectToStringConverter.asJsonString(request)))
                .andReturn();
    }

    @Test
    void shouldLoginToAccount() throws Exception {
        LoginRequest request = new LoginRequest("email@gmail.com", "123456");
        MvcResult result = mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ObjectToStringConverter.asJsonString(request)))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void shouldReturnErrorForWrongData() throws Exception {
        LoginRequest request = new LoginRequest("emailasda@gmail.com", "123456");
        MvcResult result = mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ObjectToStringConverter.asJsonString(request)))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    void shouldReturnExceptionForNullBody() throws Exception {
        LoginRequest request = new LoginRequest(null, null);
        mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ObjectToStringConverter.asJsonString(request)))
                .andExpect(status().isBadRequest())
                // .andExpect(content().json("{'details'}"));
                .andExpect(jsonPath("$.details").value(IsNull.nullValue()));

    }

}
