package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.controller.dto.LoginDTO;
import com.example.springboot.controller.request.AdminPageRequest;
import com.example.springboot.controller.request.LoginRequest;
import com.example.springboot.controller.request.PasswordRequest;
import com.example.springboot.entity.Admin;
import com.example.springboot.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080") //解决跨域错误
@RestController
@RequestMapping("/admin")    //统一的前缀来标识这是一个api接口
public class AdminController {     //以json方式返回数据

    @Autowired
    IAdminService adminService;

    @PostMapping("/login")      //这个接口用来登录
    public Result login(@RequestBody LoginRequest request) {
        LoginDTO login = adminService.login(request);
        return Result.success(login);
    }

    @PutMapping("/password")   //这个接口帮忙去修改密码
    public Result password(@RequestBody PasswordRequest request) {
        adminService.changePass(request);
        return Result.success();
    }

    @PostMapping("/save")     //这个接口用来新增
    public Result save(@RequestBody Admin obj) {
        adminService.save(obj);
        return Result.success();
    }

    @PutMapping("/update")   //帮忙更新数据的接口
    public Result update(@RequestBody Admin obj) {
        adminService.update(obj);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")     //删除数据的接口
    public Result delete(@PathVariable Integer id){
        adminService.deleteById(id);
        return Result.success();
    }

    @GetMapping("/{id}")     //通过id查询的接口，帮助获得数据，只能返回一个数据
    public Result getById(@PathVariable Integer id) {
        Admin obj = adminService.getById(id);
        return Result.success(obj);
    }

    @GetMapping("/list")
    public Result list() {
        List<Admin> list = adminService.list();   //admins存到data里面  success(Object data)
        return Result.success(list);
    }

    @GetMapping("/page")     //分页
    public Result page(AdminPageRequest PageRequest) {    //不能是base，会传进来两个值
        return Result.success(adminService.page(PageRequest));
    }
}


