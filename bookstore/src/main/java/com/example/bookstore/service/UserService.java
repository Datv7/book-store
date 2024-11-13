package com.example.bookstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.bookstore.configuration.AppException;
import com.example.bookstore.configuration.BaseRole;
import com.example.bookstore.configuration.ErrorCode;
import com.example.bookstore.dto.AuthenicationRequest;
import com.example.bookstore.dto.UserRequest;
import com.example.bookstore.entity.Role;
import com.example.bookstore.entity.User;
import com.example.bookstore.mapper.UserMapper;
import com.example.bookstore.repository.RoleRepository;
import com.example.bookstore.repository.UserRepository;
import com.example.bookstore.service.Iservice.IUserService;
@Service
public class UserService implements IUserService{

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Override
	@Transactional
	public void creatUser(UserRequest userRequest) {
		// TODO Auto-generated method stub
		
		User user=userMapper.toUser(userRequest);
		user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
		Role role=roleRepository.findByAuthorization(BaseRole.ROLE_CUSTOMER.name());
		
		user.setRoles(List.of(role));
		
		user=userRepository.save(user);
		
	}
	@Transactional
	@Override
	public void creatAdmin() {
		if(!userRepository.existsByFullName("admin")) {
			Role role=roleRepository.findByAuthorization(BaseRole.ROLE_ADMIN.name());
			User admin=User.builder()
			.fullName("admin")
			.password(passwordEncoder.encode("admin"))
			.email("admin@gmail.com")
			.sdt("0123456789")
//			)
			.roles(List.of(role))
			.build(); 
			userRepository.save(admin);
		};
	}
	@Override
	@Transactional
	public void resetByKey(AuthenicationRequest authenicationRequest,boolean logoutAll) {
		// TODO Auto-generated method stub
		User user=findUserByEmail(authenicationRequest.getIdentifier());
		user.setPassword(passwordEncoder.encode(authenicationRequest.getPassword()));
		if(logoutAll) user.setVersion(user.getVersion()+1);
		userRepository.save(user);
		
	}
	
	@Override
	public User findUser(String identifier) {
		User user=userRepository.findByEmailOrSdt(identifier,identifier).orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));
		if(user.getVersion()==-1) throw new AppException(ErrorCode.USER_NOT_EXISTED);
		return user;
	}
	@Override
	public User findUserByEmail(String email) {
		User user=userRepository.findByEmail(email).orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));
		if(user.getVersion()==-1) throw new AppException(ErrorCode.USER_NOT_EXISTED);
		return user;
	}

}
