
package com.example.jwt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.example.jwt.entity.Role;
import com.example.jwt.entity.Roles;
import com.example.jwt.entity.User;
import com.example.jwt.repository.RoleRepository;
import com.example.jwt.repository.UserRepository;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserRepositoryTest {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	// @Autowired
	// private UserRepository userRepository;
	//
	// @BeforeEach void setupRepository() {
	// userRepository.deleteAll();
	// List<User> customers = List.of(
	// new User(1L, "John", "john@mail.com", "test123", LocalDate.now()));
	// userRepository.saveAll(customers);
	// }
	//
	// @Test
	// void shouldReturnExactCount() throws Exception {
	// assertEquals(1, userRepository.count());
	// }
	private User testUser;

	@BeforeEach
	public void setUp() {
		roleRepository.deleteAll();
		 roleRepository.save(new Role(null, Roles.ROLE_USER));
		 roleRepository.save(new Role(null, Roles.ROLE_ADMIN));

//		testUser = new User();
//		testUser.setEmail("testuser@gmail.com");
//		testUser.setUsername("testuser");
//		testUser.setPassword("password");
//		userRepository.save(testUser);
	}

	@Test
	void shouldCreateRoles() {
		assertNotNull(roleRepository.findByRoleName(Roles.ROLE_USER).get());
		assertNotNull(roleRepository.findByRoleName(Roles.ROLE_ADMIN).get());
	}

	@Test
	void givenUser_whenSaved_thenCanBeFoundById() {
		User savedUser = userRepository.findById(testUser.getId()).orElse(null);
		assertNotNull(savedUser);
		// assertEquals(testUser.getUsername(), savedUser.getUsername());
		// assertEquals(testUser.getPassword(), savedUser.getPassword());
	}

//	@AfterEach
//	public void tearDown() {
//		userRepository.delete(testUser);
//	}
}
