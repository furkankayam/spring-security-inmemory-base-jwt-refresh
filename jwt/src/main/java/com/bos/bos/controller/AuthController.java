package com.bos.bos.controller;

import com.bos.bos.dto.*;
import com.bos.bos.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

//Class:12
//ClassRefresh:4
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsServiceImpl userService;

    @PostMapping("/generateToken")
    public JwtResponseDto AuthenticateAndGetToken(@RequestBody AuthRequestDto request){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        if(authentication.isAuthenticated()){
            return JwtResponseDto.builder()
                    .accessToken(jwtService.GenerateToken(request.getUsername()))
                    .build();
        }

        log.info("invalid username " + request.getUsername());

        throw new UsernameNotFoundException("invalid username {} " + request.getUsername());
    }

    /*@PreAuthorize("hasAuthority('ROLE_ADMIN')")*/
    @GetMapping
    public String deneme() {
        return "Deneme";
    }


    @GetMapping("/ping")
    public String test() {
        try {
            return "Welcome";
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<UserResponse> saveUser(@RequestBody CreateUserRequest userRequest) {

        UserResponse userResponse = userService.createUser(userRequest);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);

    }

}
