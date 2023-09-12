package com.example.springboot.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.example.springboot.controller.request.BaseRequest;
import com.example.springboot.controller.request.UserPageRequest;
import com.example.springboot.entity.User;
import com.example.springboot.mapper.UserMapper;
import com.example.springboot.service.IUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserService implements IUserService {     //实现IU的service
    @Autowired   //导入userMapper
    UserMapper userMapper;

    @Override  //实现接口中的方法
    public List<User> list() {
        return userMapper.list();
    }

    @Override
    public Object page(UserPageRequest userPageRequest) {
        PageHelper.startPage(userPageRequest.getPageNum(), userPageRequest.getPageSize());
        List<User> users = userMapper.listByCondition(userPageRequest);
        return new PageInfo<>(users);
    }

    @Override
    public void save(User user) {
        Date date = new Date();
        // 日期和uid  当做卡号来处理,用户名会重复,用hashCode()进行编码来简化会员号，返回一个纯数字
        user.setUsername(DateUtil.format(date, "yyyyMMdd") + Math.abs(IdUtil.fastSimpleUUID().hashCode()));
        //user.setAccount(0);
        userMapper.save(user);
    }

    @Override
    public User getById(Integer id) {
        return userMapper.getById(id);
    }

    @Override
    public void update(User user) {
        user.setUpdatetime(new Date());   //手动去设置一个更新时间的数据
        userMapper.updateById(user);   //通过id去更新 会员数据
    }

    @Override
    public void deleteById(Integer id) {
        userMapper.deleteById(id);
    }
}
    /*@Override
    public PageInfo<User> page(BaseRequest baseRequest) {
        //String name=userPageRequest.getName();
        //String phone=userPageRequest.getPhone();
        PageHelper.startPage(baseRequest.getPageNum(), baseRequest.getPageSize());
        List<User> users = userMapper.listByCondition(baseRequest);
        return new PageInfo<>(users);
    }









    @Override
    public void handleAccount(User user) {
        Integer score = user.getScore();
        if (score == null) {
            return;
        }
        Integer id = user.getId();
        User dbUser = userMapper.getById(id);
        dbUser.setAccount(dbUser.getAccount() + score);
        userMapper.updateById(dbUser);
    }*/

