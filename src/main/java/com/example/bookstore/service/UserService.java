package com.example.bookstore.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.bookstore.configuration.AppException;
import com.example.bookstore.configuration.BaseRole;
import com.example.bookstore.configuration.ErrorCode;
import com.example.bookstore.dto.AUpdateUserRequest;
import com.example.bookstore.dto.PageCustom;
import com.example.bookstore.dto.PasswordResetRequest;
import com.example.bookstore.dto.ProfileProjection;
import com.example.bookstore.dto.UserDetail;
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
	public User creatUser(UserRequest userRequest) {
		// TODO Auto-generated method stub
		
		User user=userMapper.toUser(userRequest);
		user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
		Role role=roleRepository.findByAuthorization(BaseRole.ROLE_CUSTOMER.name()).orElseThrow(()->new AppException(ErrorCode.ROLE_NOT_EXISTED));
		
		user.setRoles(List.of(role));
		
		return userRepository.save(user);
		
	}
	
	@Value("${spring.admin.id}")
	private String id;
	
	@Override
	public void creatAdmin() {
		if(!userRepository.existsAsAdmin(BaseRole.ROLE_ADMIN.name())) {
			Role role=roleRepository.findByAuthorization(BaseRole.ROLE_ADMIN.name()).orElseThrow(()->new AppException(ErrorCode.ROLE_NOT_EXISTED));
			User admin=User.builder()
					.fullName("admin")
					.password(passwordEncoder.encode("Admin123."))
					.email("admin@gmail.com")
					.phoneNumber("0356415204")
		//			)
					.roles(List.of(role))
					.build(); 
			System.out.println(admin.getId());
			userRepository.save(admin); 
		};
	}
	@Override
	@Transactional
	public void resetByKey(PasswordResetRequest passwordResetRequest,boolean logoutAll) {
		// TODO Auto-generated method stub
		User user=findUserByEmail(passwordResetRequest.getEmail());
		user.setPassword(passwordEncoder.encode(passwordResetRequest.getPassword()));
		if(logoutAll) user.setVersion(user.getVersion()+1);
		userRepository.save(user);
		
	}
	
	@Override
	public User findUser(String emailOrPhoneNumber) {
		User user=userRepository.findByEmailOrPhoneNumber(emailOrPhoneNumber,emailOrPhoneNumber).orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));
		if(user.getVersion()==-1) throw new AppException(ErrorCode.USER_NOT_EXISTED);
		return user;
	}
	@Override
	public User findUserByEmail(String email) {
		User user=userRepository.findByEmail(email).orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));
		if(user.getVersion()==-1) throw new AppException(ErrorCode.USER_NOT_EXISTED);
		return user;
	}
	@Transactional
	@Override
	public List<String> genUser(int quantity) {
		// TODO Auto-generated method stub
		List<String> result=new ArrayList<String>();
		for(int i=1;i<=quantity;i++) {
			
			UserRequest userRequest=randomUser();
			creatUser(userRequest);
			
			result.add(userRequest.getEmail());
		}
		return result;
	}
	public UserRequest randomUser() {
		String email="";
		int random=0;
		do {
			random = ThreadLocalRandom.current().nextInt(100000, 1000000);
			email=random+"@gmail.com";
		}while(userRepository.existsByEmailOrPhoneNumber("","05"+random));
		UserRequest userRequest=UserRequest.builder()
				.email(email)
				.phoneNumber("05"+random)
				.password("Abc."+random)
				.fullName("f")
				.build();
		return userRequest;
	}
	@Override
	public void updateUserByAdmin(String id,AUpdateUserRequest aUpdateUserRequest) {
		// TODO Auto-generated method stub
		User temp=userRepository.findById(id).orElseThrow(()->new AppException(ErrorCode.USER_NOT_EXISTED));
		if(aUpdateUserRequest.getVersion()!=0) {
			System.out.println("update version single");
			temp.setVersion(aUpdateUserRequest.getVersion());
		}
		temp.getRoles().clear();
		for(String role: aUpdateUserRequest.getRoles()) {
			Role tempRole=roleRepository.findByAuthorization("ROLE_"+role.toUpperCase()).orElseThrow(()->new AppException(ErrorCode.ROLE_NOT_EXISTED));
			temp.getRoles().add(tempRole);
		}
		userRepository.save(temp);
	}
	@Override
	public List<UserDetail> getAll() {
		// TODO Auto-generated method stub
//		Pageable pageable=PageRequest.of(page, size);
		List<UserDetail> userInLists=new ArrayList<UserDetail>();
		List<User> users=userRepository.findAll();
		for (User u:users) {
			List<String> roles=u.getRoles().stream().map(r->r.getAuthorization().replaceAll("ROLE_", "")).collect(Collectors.toList());
//			for(Role r:u.getRoles()) System.out.println(r.getAuthorization());
			
			UserDetail userInList=UserDetail.builder()
					.id(u.getId())
					.fullName(u.getFullName())
					.email(u.getEmail())
					.phoneNumber(u.getPhoneNumber())
					.version(u.getVersion())
					.roles(roles)
					.createAt(u.getCreateAt())
					.updateAt(u.getUpdateAt())
					.slug(UserDetail.genSlug(u.getFullName()))
					.build();
			userInLists.add(userInList);
			
		};

		return userInLists;
					
	}
	@Override
	public void deleteUser(List<String> userIds,boolean deleted) {
		// TODO Auto-generated method stub
		int version=-1;
		if(!deleted) version=1; 
		userRepository.deleteByListId(new Date(), userIds,version);
	}
	@Override
	public ProfileProjection getProfile(String id) {
		// TODO Auto-generated method stub
		return userRepository.getProfile(id);
	}
	@Override
	public void updateProfile(String id, ProfileProjection profileProjection) {
		// TODO Auto-generated method stub
		User user=userRepository.findById(id).orElseThrow(()->new AppException(ErrorCode.USER_NOT_EXISTED))	;
		user.setEmail(profileProjection.getEmail());
		user.setPhoneNumber(profileProjection.getPhoneNumber());
		user.setFullName(profileProjection.getFullName());
		user.setUrlAvatar(profileProjection.getUrlAvatar());
		userRepository.save(user);
	}
	@Override
	public void changePassword(String id, String currentPass, String newPass,boolean logoutAll) {
		// TODO Auto-generated method stub
		User user=userRepository.findById(id).orElseThrow(()->new AppException(ErrorCode.USER_NOT_EXISTED));
		boolean check=passwordEncoder.matches(currentPass, user.getPassword());
		if(!check) throw new AppException(ErrorCode.PASSWORD_INVALD);
		user.setPassword(passwordEncoder.encode(newPass));
		if(logoutAll) user.setVersion(user.getVersion()+1);
		userRepository.save(user);
	}
	
	

}
