package com.wenllar.service.impl;

import api.SockService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wenllar.annotation.GlobalTransaction;
import com.wenllar.dataObject.User;
import com.wenllar.holder.BatchTransactionServiceCountHolder;
import com.wenllar.holder.GroupIdHolder;
import com.wenllar.holder.constant.Constants;
import com.wenllar.mapper.UserMapper;
import com.wenllar.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import reflect.PorxyFactory;

import java.util.List;

@Service
public class OrderServiceImpl extends ServiceImpl<UserMapper, User> implements OrderService {

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


    @Autowired
    RestTemplate restTemplate;

    @Override
    @Transactional
    @GlobalTransaction
    public String createOrder(String name) {
        User user = new User();
        user.setUserName("金中国");
        user.setPassword("333333");
        // 模拟下单数据库操作
        userMapper.insert(user);
        String result = "ERROR";
        // ****      不能使用以下dubbo调用，因为反射调用是无法执行事务 @Transactional的切面  和@GlobalTransaction的切面的  ****************/
//        SockService sockService = PorxyFactory.getProxyAndSetHeader(SockService.class);
//        String result = "ERROR";
//        try {
//            result = sockService.deductSock(1);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        result = requst2SockerService();

        int t = 1/0;

        return result;
    }

    private String requst2SockerService() {
        String url = "http://127.0.0.1:8096/deductSock";
        // 封装参数，千万不要替换为Map与HashMap，否则参数无法传递
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<String, Object>();
        paramMap.add("deductNum", "20");

        // 1、使用postForObject请求接口
//        String result = restTemplate.postForObject(url, paramMap, String.class);
//        System.out.println("result1==================" + result);

        // 2、使用postForEntity请求接口
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constants.GROUP_ID, GroupIdHolder.get());
        headers.add(Constants.EXIST_BATCH_TRANSACTION_COUNT, String.valueOf(BatchTransactionServiceCountHolder.get()));

        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(paramMap, headers);
        ResponseEntity<String> response2 = restTemplate.postForEntity(url, httpEntity, String.class);
        System.out.println("result2====================" + response2.getBody());
        GroupIdHolder.remove();

        // 3、使用exchange请求接口
//        ResponseEntity<String> response3 = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
//        System.out.println("result3====================" + response3.getBody());

        return response2.getBody();
    }


}
