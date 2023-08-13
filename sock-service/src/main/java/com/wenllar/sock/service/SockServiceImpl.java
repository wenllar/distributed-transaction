package com.wenllar.sock.service;

import api.SockService;

public class SockServiceImpl implements SockService {
    @Override
    public void deductSock(Integer deductNum) {
        System.out.println("库存服务: 开始扣减库存");
        try {
            int num = 1/0;
        } catch (Exception e) {
            System.out.println("库存服务: 扣减库存失败，事务开始回滚");
            e.printStackTrace();
        }
    }
}
