package com.bos.bos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//Class:10
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequestDto {

    private String username;
    private String password;

}
