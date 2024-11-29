package com.example.bookstore.mapper;

import com.example.bookstore.dto.UserRequest;
import com.example.bookstore.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.6.2, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toUser(UserRequest userRequest) {
        if ( userRequest == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.email( userRequest.getEmail() );
        user.fullName( userRequest.getFullName() );
        user.sdt( userRequest.getSdt() );
        user.password( userRequest.getPassword() );

        return user.build();
    }
}
