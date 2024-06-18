package com.campusMatch.CM.user.dto;

import lombok.Data;

@Data
public class UserDto {

    private int id;
    private String username;
    private String password;
    private String role;
    private String email;

}
