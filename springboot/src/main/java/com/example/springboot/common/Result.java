package com.example.springboot.common;

import lombok.Data;

@Data
public class Result {    //返回类，让返回的结果统一
    private static final String SUCCESS_CODE = "200";
    private static final String ERROR_CODE = "-1";

    //要返回前端的三个属性
    private String code;   //这次接口的响应结果可以体现
    private Object date;   //后台数据存到date，前端的所有接口可以从这里拿数据
    private String msg;   //message存放错误的信息

    public static Result success() {
        Result result = new Result();
        result.setCode(SUCCESS_CODE);
        return result;
    }//返回成功的信息

    public static Result success(Object date) {
        Result result = new Result();
        result.setCode(SUCCESS_CODE);
        result.setDate(date);
        return result;
    }//同名有参，重载

    public static Result error(String msg) {
        Result result = new Result();
        result.setCode(ERROR_CODE);
        result.setMsg(msg);
        return result;
    }//返回错误的信息

    public static Result error(String code,String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
}
