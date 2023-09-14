package com.example.springboot.exception;

//自定义异常把错误信息暴露出去，用来我自己想告诉系统的异常，处理业务异常
public class ServiceException extends RuntimeException{

    private String code;    //自定义错误码

    public String getCode() {
        return code;
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String code, String message) {
        super(message);
        this.code = code;
    }

}
