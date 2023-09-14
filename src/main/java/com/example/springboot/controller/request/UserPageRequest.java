package com.example.springboot.controller.request;


import lombok.Data;

@Data
public class UserPageRequest extends BaseRequest{    //查询的类
    private String name;
    private String phone;

}
