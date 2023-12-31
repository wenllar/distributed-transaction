package com.wenllar.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wenllar.dataObject.User;

import java.util.List;

public interface OrderService extends IService<User> {


    List<User> selectAllFromDefaultDataSource();
    List<User> selectAllFromDataSource();
    List<User> selectAllFromHikariDataSource();
    String createOrder(String name);
}
