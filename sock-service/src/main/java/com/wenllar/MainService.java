package com.wenllar;

import api.HelloService;
import api.SockService;
import com.wenllar.sock.service.SockServiceImpl;
import registry.URL;
import registry.impl.LocalRegistry;
import registry.impl.RemoteRegistry;
import server.impl.HttpJettyServer;

public class MainService {

//    public static void main(String[] args) throws Exception {
//        // 本地服务注册
//        LocalRegistry.regist(SockService.class.getName(), SockServiceImpl.class.getName());
//
//        // 远程服务注册
//        String host = "localhost";
//        int port = 8089;
//        URL url = URL.builder().host(host).port(port).build();
//        RemoteRegistry.regist(SockService.class.getName(), url);
//        // 注册成功后，启动应用
//        //new HttpTomcatServer().startServer(host, port);
//        new HttpJettyServer().startServer(host, port);
//    }
}
