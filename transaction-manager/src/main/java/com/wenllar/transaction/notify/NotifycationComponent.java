package com.wenllar.transaction.notify;

import api.NotifycationService;
import com.wenllar.holder.GroupIdHolder;
import com.wenllar.holder.constant.Constants;
import dto.TransactionResult;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import reflect.PorxyFactory;
import registry.URL;
import registry.impl.RemoteRegistry;

import java.util.Arrays;
import java.util.List;

@Component
public class NotifycationComponent {

    public void notify(TransactionResult transactionResult){
        // dubbo调用无法 嵌入springboot的线程，因为dubbo是另外开的线程
//        List<URL> service = RemoteRegistry.getService(NotifycationService.class.getName());
//        if(CollectionUtils.isEmpty(service)){
//            throw new NullPointerException();
//        }
//        for(URL url: service){
//            NotifycationService notifycationService = PorxyFactory.getProxy(NotifycationService.class, url.getHost(), url.getPort());
//            notifycationService.notify(transactionResult);
//        }


        String server1_url = "http://127.0.0.1:8080/notify";
        String server2_url = "http://127.0.0.1:8096/notify";
        List<String> serverUrlList = Arrays.asList(server1_url, server2_url);
        // 封装参数，千万不要替换为Map与HashMap，否则参数无法传递
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<String, Object>();
        paramMap.add(Constants.GROUP_ID, transactionResult.getGroupId());
        paramMap.add("action", transactionResult.getTransactionStatus().getValue());

        // 1、使用postForObject请求接口
//        String result = restTemplate.postForObject(url, paramMap, String.class);
//        System.out.println("result1==================" + result);

        // 2、使用postForEntity请求接口
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constants.GROUP_ID, GroupIdHolder.get());
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<MultiValueMap<String, Object>>(paramMap, headers);
        System.out.println("--------------- 事务管理器，给各个分支事务所在服务发送 事务指令---------------");
        System.out.println(paramMap);
        for(String url : serverUrlList){
            ResponseEntity<String> response2 = new RestTemplate().postForEntity(url, httpEntity, String.class);
            System.out.println("result2====================" + response2.getBody());
        }

    }

}
