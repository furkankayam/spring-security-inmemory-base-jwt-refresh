package com.bos.bos.service;

import com.bos.bos.config.*;
import com.bos.bos.dto.CreateUserRequest;
import com.bos.bos.dto.UserResponse;
import com.bos.bos.dto.converter.*;
import com.bos.bos.model.User;
import com.bos.bos.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

//Class:6
@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository.findByUsername(username);

    }

    public UserResponse createUser(CreateUserRequest request) {

        /*User newUser = User.builder()
                .id(new User().getId())
                .username(request.username())
                .password(passwordEncoderConfig.passwordEncoder().encode(request.password()))
                .authorities(request.authorities())
                .isEnabled(true)
                .accountNonExpired(true)
                .credentialsNonExpired(true)
                .accountNonLocked(true)
                .build();*/
        User user = userConverter.toUser(request);

        userRepository.save(user);

        return userConverter.toUserResponse(user);
    }

}
