
package com.example.jwt;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.example.jwt.entity.User;
import com.example.jwt.repository.UserRepository;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class UserRepositoryTest {

//	@Autowired
//	private UserRepository userRepository;
//
//	@BeforeEach
//	void setupRepository() {
//		userRepository.deleteAll();
//		List<User> customers = List.of(
//				new User(1L, "John", "john@mail.com", "test123", LocalDate.now()));
//		userRepository.saveAll(customers);
//	}
//
//	@Test
//	void shouldReturnExactCount() throws Exception {
//		assertEquals(1, userRepository.count());
//	}

}
