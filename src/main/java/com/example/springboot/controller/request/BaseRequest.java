package com.example.springboot.controller.request;

import lombok.Data;

@Data
public class BaseRequest {     //基类
    //通用的分页的个数以及当前的页码
    private Integer pageNum = 1;
    private Integer pageSize = 10;
}
