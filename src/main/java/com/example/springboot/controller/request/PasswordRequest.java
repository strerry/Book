package com.example.springboot.controller.request;

import lombok.Data;

@Data
public class PasswordRequest {       //请求密码的类
    private String username;
    private String password;
    private String newPass;  //新密码
}
