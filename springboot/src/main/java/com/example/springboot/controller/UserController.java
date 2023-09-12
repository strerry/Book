package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.controller.request.UserPageRequest;
import com.example.springboot.entity.User;
import com.example.springboot.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin  //解决跨域错误
@RestController
@RequestMapping("/user")
public class UserController {     //以json方式返回数据

    @Autowired
    IUserService userService;

    @PostMapping("/save")     //这个接口用来新增会员
    public Result save(@RequestBody User user) {
        userService.save(user);
        return Result.success();
    }

    @PutMapping("/update")   //帮忙更新数据的接口
    public Result update(@RequestBody User user) {
        userService.update(user);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")     //删除数据的接口
    public Result delete(@PathVariable Integer id){
        userService.deleteById(id);
        return Result.success();
    }

    @GetMapping("/{id}")     //通过id查询的接口，帮助获得数据，只能返回一个数据
    public Result getById(@PathVariable Integer id) {
        User user = userService.getById(id);
        return Result.success(user);
    }

    @GetMapping("/list")
    public Result list() {
        List<User> users = userService.list();   //users存到date里面  success(Object date)
        return Result.success(users);
    }

    @GetMapping("/page")     //分页
    public Result page(UserPageRequest userPageRequest) {
        return Result.success(userService.page(userPageRequest));
    }
}


    /*


    */