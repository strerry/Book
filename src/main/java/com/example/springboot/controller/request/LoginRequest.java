package com.example.springboot.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {      //登录所需要的用户名和密码
    private String username;
    private String password;
}
