package com.a00n.springsecurityauthform;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.a00n.springsecurityauthform.entities.Role;
import com.a00n.springsecurityauthform.entities.Todo;
import com.a00n.springsecurityauthform.entities.User;
import com.a00n.springsecurityauthform.repositories.RoleRepository;
import com.a00n.springsecurityauthform.repositories.TodoRepository;
import com.a00n.springsecurityauthform.repositories.UserRepository;

@SpringBootApplication
public class SpringSecurityAuthFormApplication {

	@Autowired
	private TodoRepository todoRepository;

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityAuthFormApplication.class, args);
	}

	@Bean
	CommandLineRunner init() {
		return args -> {
			Role admin = Role.builder().name("ROLE_ADMIN").build();
			Role user = Role.builder().name("ROLE_USER").build();
			Role manager = Role.builder().name("ROLE_MANAGER").build();
			admin = roleRepository.save(admin);
			user = roleRepository.save(user);
			manager = roleRepository.save(manager);
			System.out.println(admin);
			System.out.println(roleRepository.findAll());
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			var user1 = User.builder()
					.firstName("ayoub")
					.lastName("nouri")
					.username("ayoub").email("ayoub@gmail.com")
					// .password("$2a$10$gqHrslMttQWSsDSVRTK1OehkkBiXsJ/a4z2OURU./dizwOQu5Lovu")
					.password(passwordEncoder.encode("ayoub"))
					.roles(Set.of(admin, user, manager))
					.build();
			var user2 = User.builder()
					.firstName("user")
					.lastName("user")
					.username("user").email("user@gmail.com")
					// .password("$2a$10$gqHrslMttQWSsDSVRTK1OehkkBiXsJ/a4z2OURU./dizwOQu5Lovu")
					.password(passwordEncoder.encode("user"))
					.roles(Set.of(user, manager))
					.build();
			userRepository.save(user1);
			userRepository.save(user2);

			todoRepository.save(Todo.builder().name("todo 1").user(user1).build());
			todoRepository.save(Todo.builder().name("todo 2").user(user1).build());
			todoRepository.save(Todo.builder().name("todo 3").user(user2).build());

			System.out.println(userRepository.findAll());
		};
	}

}
