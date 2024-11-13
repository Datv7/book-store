package com.example.bookstore.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import com.example.bookstore.configuration.AppException;
import com.example.bookstore.configuration.CustomJwtDecoder;
import com.example.bookstore.configuration.ErrorCode;
import com.example.bookstore.dto.AuthenicationRequest;
import com.example.bookstore.dto.UserRequest;
import com.example.bookstore.entity.LogoutToken;
import com.example.bookstore.entity.User;
import com.example.bookstore.repository.LogoutTokenRepository;
import com.example.bookstore.repository.UserRepository;
import com.example.bookstore.service.Iservice.IAuthenicationService;
import com.example.bookstore.service.Iservice.INotification;
import com.example.bookstore.service.Iservice.IUserService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.shaded.gson.JsonObject;
import com.nimbusds.jwt.JWTClaimsSet;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthenicationService implements IAuthenicationService{
	
	@Value("${jwt.secret}")
	private String secret;
	@Value("${jwt.expiration.access}")
	private int accessExpiration;
	
	@Value("${jwt.expiration.refresh}")
	private int refreshExpiration;

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private CustomJwtDecoder customJwtDecoder;
	@Autowired
	private LogoutTokenRepository logoutTokenRepository;
	@Autowired
	private INotification iNotification;
	@Autowired
	private IUserService iUserService;
	
	@Override
	public String[] login(AuthenicationRequest authenicationRequest) {
		// TODO Auto-generated method stub
		User temp= iUserService.findUser(authenicationRequest.getIdentifier());
		boolean passwordCorrect=passwordEncoder.matches(authenicationRequest.getPassword(), temp.getPassword());
		if(!passwordCorrect) throw new AppException(ErrorCode.UNAUTHENTICATED);
		
		String[] token= {genToken(temp, accessExpiration),genToken(temp, refreshExpiration),temp.getFullName()};
		return token;
	}
	
	public String genToken(User user,int expire) {
		JWSHeader header=new JWSHeader(JWSAlgorithm.HS512);
		JWTClaimsSet claimsSet= new JWTClaimsSet.Builder()
				.subject(user.getId())
				.expirationTime(Date.from(
						Instant.now().plus(expire,ChronoUnit.SECONDS)))
				.jwtID(UUID.randomUUID().toString())
				.claim("scope", genScope(user))
				.claim("versionUser", user.getVersion())
				.build();
		Payload payload=new Payload(claimsSet.toJSONObject());
		JWSObject object=new JWSObject(header, payload);
		try {
			object.sign(new MACSigner(secret.getBytes()));
			return object.serialize();
		} catch (JOSEException e) {
			log.error("generate token fail!");
			throw new AppException(ErrorCode.TOKEN_GENERATION_ERROR);
		}
		
		
	}
	
	public String genScope(User user) {
		StringJoiner joiner=new StringJoiner(" ");
		user.getRoles().forEach(role -> joiner.add(role.getAuthorization()));
		
		return joiner.toString();
	}

	@Override
	public void logout(String accessToken) {
		// TODO Auto-generated method stub
		Jwt jwt= customJwtDecoder.decode(accessToken);
		String fullName=jwt.getSubject();
		Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
		System.out.println(authentication.getName());
		if(!fullName.equals(authentication.getName())) throw new AppException(ErrorCode.UNAUTHORIZED);
		logoutTokenRepository.save(new LogoutToken(jwt.getId(),Date.from(jwt.getExpiresAt())));
		
		
	}
	
	@Value("${jwt.expiration.remainingTime}")
	private int remainingTime;
	@Override
	public String[] refresh(String refreshToken) {
		Jwt jwt= customJwtDecoder.decode(refreshToken);
		Date date=Date.from(jwt.getExpiresAt());
		Date date2=new Date();
		long remainingTime2=TimeUnit.MILLISECONDS.toSeconds(date.getTime()-date2.getTime());
		System.out.println("ok");
		String[] token= {genTokenAsRefresh(jwt,accessExpiration),null};
		log.info(token[1]);
		if(remainingTime2<remainingTime) 
			token[1]=genTokenAsRefresh(jwt,refreshExpiration);

		return token;
	}
	
	public String genTokenAsRefresh(Jwt jwt,int expire) {
		
		JWSHeader header=new JWSHeader(JWSAlgorithm.HS512);
		JWTClaimsSet claimsSet= new JWTClaimsSet.Builder()
				.subject(jwt.getSubject())
				.expirationTime(Date.from(
						Instant.now().plus(expire,ChronoUnit.SECONDS)))
				.jwtID(UUID.randomUUID().toString())
				.claim("scope", jwt.getClaimAsString("scope"))
				.claim("versionUser",((Long) jwt.getClaim("versionUser")).intValue())
				.build();
		Payload payload=new Payload(claimsSet.toJSONObject());
		JWSObject object=new JWSObject(header, payload);
		try {
			object.sign(new MACSigner(secret.getBytes()));
		} catch (JOSEException e) {
			log.error("generate token fail!");
			throw new AppException(ErrorCode.TOKEN_GENERATION_ERROR);
		}
		return object.serialize();
	}

	@Override
	public String forgotPass(String email, String resetPasswordPage) {
		// TODO Auto-generated method stub
		iUserService.findUserByEmail(email);
		String token=UUID.randomUUID().toString();
		iNotification.sendNotification(email,token, resetPasswordPage);
		return token;
	}
	@Override
	public int register(UserRequest userRequest) {
		if(
			userRepository.existsByEmailOrSdt(userRequest.getEmail(), userRequest.getSdt())
			) throw new AppException(ErrorCode.USER_EXISTED);
		int otp = ThreadLocalRandom.current().nextInt(100000, 1000000);
		iNotification.sendOtp(otp, userRequest.getEmail());
		
		return otp;
	}
}
