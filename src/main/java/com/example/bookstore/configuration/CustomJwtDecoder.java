package com.example.bookstore.configuration;

import javax.crypto.spec.SecretKeySpec;
import javax.security.sasl.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import com.example.bookstore.entity.User;
import com.example.bookstore.repository.LogoutTokenRepository;
import com.example.bookstore.repository.UserRepository;

@Component
public class CustomJwtDecoder implements JwtDecoder{
	
	@Value("${jwt.secret}")
	private String secret;
	@Autowired
	private LogoutTokenRepository logoutTokenRepository;
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public Jwt decode(String token) throws JwtException {
		// TODO Auto-generated method stub
		SecretKeySpec keySpec=new SecretKeySpec(secret.getBytes(), "HS512");
		NimbusJwtDecoder nimbusJwtDecoder= NimbusJwtDecoder.withSecretKey(keySpec)
						.macAlgorithm(MacAlgorithm.HS512)
						.build();
		Jwt jwt= nimbusJwtDecoder.decode(token);
		String id=(String)jwt.getId();
		System.out.println("id");
		int versionUser=((Long)jwt.getClaim("versionUser")).intValue();
		System.out.println("ver");
		User temp= userRepository.findById(jwt.getSubject()).orElseThrow(()-> new BadCredentialsException(""));
		int version=temp.getVersion();
		if(version==-1) throw new BadCredentialsException("");
		if(logoutTokenRepository.existsById(id) || version!=versionUser) throw new BadCredentialsException("");
		
		 
		return jwt;
	}

}
