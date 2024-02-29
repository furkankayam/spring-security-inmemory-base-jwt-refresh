package com.basicauth.service;

import com.basicauth.dto.CreateUserRequest;
import com.basicauth.model.User;
import com.basicauth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

//Veritabaniyla olan iliski sadece
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public Optional<User> getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User createUser(CreateUserRequest userRequest) {
        User newUser = User.builder()
                .name(userRequest.name())
                .username(userRequest.username())
                .password(passwordEncoder.encode(userRequest.password()))
                .authorities(userRequest.authorities())
                .accountNonExpired(true)
                .isCredentialsNonExpired(true)
                .isEnabled(true)
                .accountNonLocked(true)
                .build();

        return userRepository.save(newUser);
    }

}
