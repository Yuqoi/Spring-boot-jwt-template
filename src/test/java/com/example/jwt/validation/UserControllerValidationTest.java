package com.example.jwt.validation;

import static com.example.jwt.utils.ObjectToStringConverter.asJsonString;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.jwt.controller.AuthController;
import com.example.jwt.exception.GlobalExceptionHandler;
import com.example.jwt.request.RegisterRequest;
import com.example.jwt.response.DefaultResponse;
import com.example.jwt.service.AuthService;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import tools.jackson.databind.ObjectMapper;

@Import(GlobalExceptionHandler.class)
@WebMvcTest(AuthController.class)
public class UserControllerValidationTest {

  @Autowired private MockMvc mockMvc;

  @MockitoBean private AuthService authService;

  @Test
  void shouldReturnExceptionForMissingUsername() throws Exception {
    template(new RegisterRequest(null, "username@gmail.com", "Password"));
  }

  @Test
  void shouldReturnExceptionForTooLongName() throws Exception {
    template(
        new RegisterRequest(
            "asdasdasdasdasdasdasdasdasdasdasdasdasd", "username@gmail.com", "Password"));
  }

  @Test
  void shouldReturnExceptionForWrongEmail() throws Exception {
    template(new RegisterRequest("abacvasc", "usernamecom", "Password"));
  }

  @Test
  void shouldReturnExceptionForNullEmail() throws Exception {
    template(new RegisterRequest("asdasdasdasdasdasdasdasdasdasdasdasdasd", null, "Password"));
  }

  @Test
  void shouldReturnExceptionForNullPassword() throws Exception {
    template(new RegisterRequest("adasdasd", "username@gmail.com", null));
  }

  @Test
  void shouldReturnExceptionForPasswordTooLong() throws Exception {
    template(
        new RegisterRequest("adasdasd", "username@gmail.com", "asdasdasdasdasdasdasdasdasdasdasd"));
  }

  @Test
  void shouldReturnBadRequestForNullBody() throws Exception {
    mockMvc
        .perform(post("/api/v1/auth/register").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(
            result ->
                assertInstanceOf(
                    HttpMessageNotReadableException.class, result.getResolvedException()))
        .andExpect(content().json("{'message':'Required body is missing'}"))
        .andExpect(jsonPath("$.details").value(IsNull.nullValue()));
  }

  private void template(RegisterRequest register) throws Exception {
    MvcResult result =
        mockMvc
            .perform(
                post("/api/v1/auth/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(register)))
            .andExpect(status().isBadRequest())
            .andReturn();
    ObjectMapper objMappper = new ObjectMapper();
    String stringResponse = result.getResponse().getContentAsString();
    DefaultResponse gottenResponse = objMappper.readValue(stringResponse, DefaultResponse.class);

    assertNull(gottenResponse.details());
    assertInstanceOf(MethodArgumentNotValidException.class, result.getResolvedException());
  }
}
