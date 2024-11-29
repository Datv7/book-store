package com.example.bookstore.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.example.bookstore.entity.Role;
import com.example.bookstore.repository.RoleRepository;
import com.example.bookstore.service.Iservice.IUserService;

@Configuration
public class AppConfig {
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
	
	@Value("${spring.redis.host}")
	private String redisHost;
	
	@Value("${spring.redis.port}")
	private int redisPort;
	
	@Value("${spring.redis.password}")
	private String redisPassword;
	@Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(redisHost);
        redisStandaloneConfiguration.setPort(redisPort);
        redisStandaloneConfiguration.setPassword(RedisPassword.of(redisPassword));
        
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }
	@Bean
	public RedisTemplate<String, Object> redisTemplate(){
		RedisTemplate<String, Object> redisTemplate=new RedisTemplate<String, Object>();
		redisTemplate.setConnectionFactory(redisConnectionFactory());
		
		redisTemplate.setKeySerializer(new StringRedisSerializer());	
		redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
		return redisTemplate;
	}
	
	
}
