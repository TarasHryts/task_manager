package com.djamware.mongodbsecurity.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserDto {
    private String email;
    private String password;
}
