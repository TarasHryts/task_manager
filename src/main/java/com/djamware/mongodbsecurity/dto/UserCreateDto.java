package com.djamware.mongodbsecurity.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserCreateDto {
    private String username;
    private String email;
    private String password;
    private String passwordConfirm;
}
