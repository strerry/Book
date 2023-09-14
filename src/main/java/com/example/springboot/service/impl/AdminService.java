package com.example.springboot.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.example.springboot.controller.dto.LoginDTO;
import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.controller.request.LoginRequest;
import com.example.springboot.controller.request.PasswordRequest;
import com.example.springboot.entity.Admin;
import com.example.springboot.entity.User;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.mapper.AdminMapper;
import com.example.springboot.mapper.CategoryMapper;
import com.example.springboot.mapper.UserMapper;
import com.example.springboot.service.IAdminService;
import com.example.springboot.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class AdminService implements IAdminService{     //实现IU的service
    @Autowired   //导入Mapper
    AdminMapper adminMapper;

    private static final String DEFAULT_PASS = "123";    //常量，默认密码 123
    private static final String PASS_SALT = "qingge";    //固定的字符串，用于加密

    @Override  //实现接口中的方法
    public List<Admin> list() {
        return adminMapper.list();
    }

    @Override
    public PageInfo<Admin> page(BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(), baseRequest.getPageSize());
        List<Admin> users = adminMapper.listByCondition(baseRequest);
        return new PageInfo<>(users);
    }

     @Override
    public void save(Admin obj) {
        // 默认密码 123,在新增管理员时没有设置密码，故要默认
        if (StrUtil.isBlank(obj.getPassword())) {    //设置默认密码前，看用户有没有传
            obj.setPassword(DEFAULT_PASS);      //没有传，设置默认密码
        }
        // 加密的工具类，设置md5加密，加盐,不易破解
        //obj.setPassword(SecureUtil.md5(obj.getPassword()+PASS_SALT));
        obj.setPassword(securePass(obj.getPassword()));

        try {
            adminMapper.save(obj);
        } catch (DuplicateKeyException e) {
            log.error("数据插入失败， username:{}", obj.getUsername(), e);
            throw new ServiceException("用户名重复");
        }

    }
     /*@Override
     public void save(Admin obj) {
         // 默认密码 123
         if (StrUtil.isBlank(obj.getPassword())) {
             obj.setPassword(DEFAULT_PASS);   //用户没传默认是123
         }
         try {
             adminMapper.save(obj);
         } catch (DuplicateKeyException e) {
             log.error("数据插入失败， username:{}", obj.getUsername(), e);
             throw new ServiceException("用户名重复");
         }
     }*/

    @Override
    public Admin getById(Integer id) {
        return adminMapper.getById(id);
    }

    @Override
    public void update(Admin user) {
        user.setUpdatetime((Date) new Date());   //手动去设置一个更新时间的数据
        adminMapper.updateById(user);   //通过id去更新 会员数据
    }

    @Override
    public void deleteById(Integer id) {
        adminMapper.deleteById(id);
    }

    //发生业务层，应该优先去抛这个业务，然后被全局的处理器去接住，统一去返回
    @Override
    public LoginDTO login(LoginRequest request) {      //登录成功把当前的对象传递出去
         /*
        //request.setPassword(SecureUtil.md5(request.getPassword()+PASS_SALT));
        // 把登录界面的密码，即前端传过来的密码加密使其与数据库的一致，
        //将一样的代码封装
        request.setPassword(securePass(request.getPassword()));
        Admin admin=adminMapper.getByUsernameAndPassword(request);
        if (admin == null) {
            throw new ServiceException("用户名或密码错误");
        }
        LoginDTO loginDTO=new LoginDTO();
        BeanUtils.copyProperties(admin,loginDTO);
        return loginDTO;*/


        Admin admin = null;
        try {
            admin = adminMapper.getByUsername(request.getUsername());
        } catch (Exception e) {
            log.error("根据用户名{} 查询出错", request.getUsername());
            throw new ServiceException("用户名错误");
        }

        if (admin == null) {
            throw new ServiceException("用户名或密码错误");
        }

        // 判断密码是否合法
        String securePass = securePass(request.getPassword());
        //String securePass=request.getPassword();
        if (!securePass.equals(admin.getPassword())) {
            throw new ServiceException("用户名或密码错误");
        }

        if (!admin.isStatus()) {
            throw new ServiceException("当前用户处于禁用状态，请联系管理员");
        }

        LoginDTO loginDTO = new LoginDTO();
        BeanUtils.copyProperties(admin, loginDTO);

        // 生成token
        String token = TokenUtils.genToken(String.valueOf(admin.getId()), admin.getPassword());
        loginDTO.setToken(token);
        return loginDTO;
    }
    /*@Override
    public LoginDTO login(LoginRequest request) {

        Admin admin = adminMapper.getByUsernameAndPassword(request);
        if (admin == null) {
            throw new ServiceException("用户名或密码错误");
        }
        LoginDTO loginDTO = new LoginDTO();
        BeanUtils.copyProperties(admin, loginDTO);

        //生成token
        String token = TokenUtils.genToken(String.valueOf(admin.getId()), admin.getPassword());
        loginDTO.setToken(token);

        return loginDTO;
    }*/

    private String securePass(String password) {
        return SecureUtil.md5(password + PASS_SALT);  //传入一个明文的密码，返回一个加密的方法，此函数用于封装
    }

    @Override
    public void changePass(PasswordRequest request) {    //改变密码
        // 注意 你要对新的密码进行加密
        request.setNewPass(securePass(request.getNewPass()));
        int count = adminMapper.updatePassword(request);
        if (count <= 0) {
            throw new ServiceException("修改密码失败");
        }
    }

}



