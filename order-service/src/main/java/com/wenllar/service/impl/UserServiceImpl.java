package com.wenllar.service.impl;

import api.SockService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wenllar.dataObject.User;
import com.wenllar.mapper.UserMapper;
import com.wenllar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reflect.PorxyFactory;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired(required = false)
    UserMapper userMapper;

    @Override
    public List<User> selectAllFromDefaultDataSource() {
        return userMapper.selectAllFromDefaultDataSource();
    }

    @Override
    public List<User> selectAllFromDataSource() {
        return userMapper.selectAllFromDataSource();
    }

    @Override
    public List<User> selectAllFromHikariDataSource() {
        return userMapper.selectAllFromHikariDataSource();
    }

    @Override
    public String test(String name) {
        User user = new User();
        user.setUserName("金中国");
        user.setPassword("333333");
        // 模拟下单数据库操作
        userMapper.insert(user);
        SockService sockService = PorxyFactory.getProxy(SockService.class);
        sockService.deductSock(1);
        return "success";
    }


}
