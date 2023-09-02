package com.wenllar;

import api.NotifycationService;
import api.SockService;
import com.wenllar.notify.NotifyServiceImpl;
import com.wenllar.sock.service.SockServiceImpl;
import lombok.SneakyThrows;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import registry.URL;
import registry.impl.LocalRegistry;
import registry.impl.RemoteRegistry;
import server.impl.HttpJettyServer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableTransactionManagement
@MapperScan("com.wenllar.mapper")
public class Application {

    public static void main(String[] args) throws Exception {
//        ExecutorService executorService = new ThreadPoolExecutor(1, 1, 30L, TimeUnit.MINUTES, new LinkedBlockingDeque<>(15), new ThreadPoolExecutor.AbortPolicy());
//        executorService.execute(new Runnable() {
//            @SneakyThrows
//            @Override
//            public void run() {
//                // 本地服务注册
//                LocalRegistry.regist(NotifycationService.class.getName(), NotifyServiceImpl.class.getName());
//
//                // 远程服务注册
//                String host = "localhost";
//                int port = 8089;
//                URL url = URL.builder().host(host).port(port).build();
//                RemoteRegistry.regist(NotifycationService.class.getName(), url);
//                // 注册成功后，启动应用
//                //new HttpTomcatServer().startServer(host, port);
//                new HttpJettyServer().startServer(host, port);
//            }
//        });

       SpringApplication.run(Application.class,args);

    }
}
