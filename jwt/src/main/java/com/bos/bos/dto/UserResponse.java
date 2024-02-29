package com.bos.bos.dto;

import com.bos.bos.model.Role;
import lombok.*;

import java.util.Set;

//Class:14
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserResponse {

    private String username;
    private Set<Role> roles;

}
