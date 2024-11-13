package com.example.bookstore.configuration;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	@Autowired
	private CustomJwtDecoder customJwtDecoder;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		String[] publicUrl={"/users/test","/auth/**","/swagger-ui/**","/v3/api-docs/**"};
		http.authorizeHttpRequests(configure-> configure
//				.anyRequest().permitAll() //bỏ comment dòng này để tắt 
				.requestMatchers("/users/cus").hasRole("CUSTOMER")
				.requestMatchers("/users/admin").hasRole("ADMIN")
				
				.requestMatchers("/auth/logout").authenticated()
				.requestMatchers(publicUrl).permitAll()
				);
		
		http.oauth2ResourceServer(oauth2->oauth2.jwt(jwtConfigurer->
			jwtConfigurer.decoder(customJwtDecoder)
			.jwtAuthenticationConverter(jwtAuthenticationConverter())
			));
		http.csrf(t->t.disable());
		return http.build();
	}
	@Bean
	public JwtAuthenticationConverter jwtAuthenticationConverter() {
		JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter=new JwtGrantedAuthoritiesConverter();
		grantedAuthoritiesConverter.setAuthorityPrefix("");
		JwtAuthenticationConverter converter=new JwtAuthenticationConverter();
		converter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
		return converter;
		
	}
	
}
