package com.bos.bos.dto.converter;

import com.bos.bos.config.PasswordEncoderConfig;
import com.bos.bos.dto.CreateUserRequest;
import com.bos.bos.dto.UserResponse;
import com.bos.bos.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserConverter {

    private final PasswordEncoderConfig passwordEncoderConfig;

    public UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .username(user.getUsername())
                .roles(user.getAuthorities())
                .build();
    }

    public User toUser(CreateUserRequest request) {
        User user = new User();
        user.setName(request.name());
        user.setUsername(request.username());
        user.setPassword(passwordEncoderConfig.passwordEncoder().encode(request.password()));
        user.setAuthorities(request.authorities());
        return user;
    }

}
