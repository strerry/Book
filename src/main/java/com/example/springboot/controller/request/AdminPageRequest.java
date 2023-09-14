package com.example.springboot.controller.request;


import lombok.Data;

@Data
public class AdminPageRequest extends BaseRequest{    //查询的类
    private String username;
    private String phone;
    private String email;

}
