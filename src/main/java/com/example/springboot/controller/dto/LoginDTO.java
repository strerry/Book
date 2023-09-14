package com.example.springboot.controller.dto;

import lombok.Data;

@Data
public class LoginDTO {          //封装要返回的一些基本数据信息,密码不要在登录界面返回，因为不安全
    private Integer id;
    private String  username;
    private String phone;
    private String email;
    private String token;

}
