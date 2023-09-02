package com.wenllar.transaction;

import api.TransactionRegistService;
import com.wenllar.transaction.store.TransactionRegistServiceImpl;
import registry.URL;
import registry.impl.LocalRegistry;
import registry.impl.RemoteRegistry;
import server.impl.HttpJettyServer;

public class MainService {
    public static void main(String[] args) throws Exception {
        // 本地服务注册
        LocalRegistry.regist(TransactionRegistService.class.getName(), TransactionRegistServiceImpl.class.getName());

        // 远程服务注册
        String host = "localhost";
        int port = 8093;
        URL url = URL.builder().host(host).port(port).build();
        RemoteRegistry.regist(TransactionRegistService.class.getName(), url);
        // 注册成功后，启动应用
        //new HttpTomcatServer().startServer(host, port);
        new HttpJettyServer().startServer(host, port);
    }
}
