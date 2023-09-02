package com.wenllar.sock.service;

import api.SockService;
import com.wenllar.annotation.GlobalTransaction;
import com.wenllar.dataObject.User;
import com.wenllar.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SockServiceImpl implements SockService {

    UserMapper userMapper;

    public SockServiceImpl(UserMapper userMapper){
       this.userMapper = userMapper;
    }

    @Override
    @GlobalTransaction(isEnd = true)
    @Transactional
    public String deductSock(Integer deductNum) {
        System.out.println("库存服务: 开始扣减库存");
        try {
            User user = new User();
            user.setId((long) (Math.floor(Math.random()* 1000000000)));
            user.setUserName("金中国");
            user.setPassword("333333");
            // 模拟减库存的数据库操作
            userMapper.insert(user);
        } catch (Exception e) {
            System.out.println("库存服务: 扣减库存失败，事务开始回滚");
            throw e;
        }

        return "Success";
    }
}
