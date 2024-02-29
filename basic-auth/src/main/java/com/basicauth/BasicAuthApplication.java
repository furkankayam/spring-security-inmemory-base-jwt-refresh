package com.basicauth;

import com.basicauth.dto.CreateUserRequest;
import com.basicauth.model.Role;
import com.basicauth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Set;

@SpringBootApplication
@RequiredArgsConstructor
public class BasicAuthApplication implements CommandLineRunner {

	private final UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(BasicAuthApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		createDummyData();
	}

	private void createDummyData() {
		CreateUserRequest request = CreateUserRequest.builder()
				.name("furkan")
				.username("furkan")
				.password("1234")
				.authorities(Set.of(Role.ROLE_USER))
				.build();
		userService.createUser(request);

		CreateUserRequest request1 = CreateUserRequest.builder()
				.name("ayse")
				.username("ayse")
				.password("1234")
				.authorities(Set.of(Role.ROLE_ADMIN))
				.build();
		userService.createUser(request1);

		CreateUserRequest request2 = CreateUserRequest.builder()
				.name("a")
				.username("a")
				.password("1234")
				.authorities(Set.of(Role.ROLE_MOD))
				.build();
		userService.createUser(request2);
	}

}
