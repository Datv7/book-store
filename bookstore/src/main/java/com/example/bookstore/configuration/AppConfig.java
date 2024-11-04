package com.example.bookstore.configuration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.example.bookstore.entity.Role;
import com.example.bookstore.entity.User;
import com.example.bookstore.repository.RoleRepository;
import com.example.bookstore.repository.UserRepository;
import com.example.bookstore.service.imp.IUserService;

@Configuration
public class AppConfig {
	@Autowired
	private PasswordEncoder encoder;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private IUserService iUserService;
	@Bean
	public CommandLineRunner run() {
		return
			runner->{
				if(!roleRepository.existsByAuthorization(BaseRole.ROLE_CUSTOMER.name())) 
					roleRepository.save(
							Role.builder()
							.authorization(BaseRole.ROLE_CUSTOMER.name())
							.build()
							);
				if(!roleRepository.existsByAuthorization(BaseRole.ROLE_ADMIN.name())) 
					roleRepository.save(
							Role.builder()
							.authorization(BaseRole.ROLE_ADMIN.name())
							.build()
							);
				iUserService.creatAdmin();
			};
	}
	
}
