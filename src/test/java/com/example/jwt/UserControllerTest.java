package com.example.jwt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.example.jwt.controller.UserController;
import com.example.jwt.dto.UserDto;
import com.example.jwt.service.UserService;

import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

@WebMvcTest(UserController.class)
// @Import(WebSecurityConfig.class)
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private UserService userService;

	private List<UserDto> userList = new ArrayList<>();

	@BeforeEach
	void setup() {
		userList.addAll(
				List.of(
						new UserDto("yuqoi", LocalDate.now())));

	}

	@Test
	void shouldCreateMockMvc() {
		assertNotNull(mockMvc);
	}

	@Test
	void shouldReturnAllUsersDto() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		when(userService.getUsers()).thenReturn(userList);
		MvcResult res = mockMvc.perform(get("/api/v1/users"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andReturn();

		String json = res.getResponse().getContentAsString();
		List<UserDto> jsonResult = mapper.readValue(json, new TypeReference<List<UserDto>>() {
		});
		assertNotNull(jsonResult);
		assertEquals(1, jsonResult.size());
	}
}
