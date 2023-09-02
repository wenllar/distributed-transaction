package com.wenllar.aspect;

import com.wenllar.connection.WenllarConnection;
import com.wenllar.transactional.WenllarGlobalTransactionManger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.sql.Connection;

@Aspect
@Component
public class DataSourceAspect {

    /**
     * 切的是一个接口，所以所有的实现类都会被切到
     * spring肯定会调用这个方法来生成一个本地事务
     * 所以point.proceed()返回的也是一个Connection
     */
    @Around("execution(* javax.sql.DataSource.getConnection(..))")
    public Connection around(ProceedingJoinPoint joinPoint) throws Throwable {
        Connection connection = null;
        // 如果当前线程中存在一个分支事务，那么则创建WenllarConnection
        if (WenllarGlobalTransactionManger.getCurrentBatchTransaction() != null) {
            connection = new WenllarConnection((Connection) joinPoint.proceed(), WenllarGlobalTransactionManger.getCurrentBatchTransaction());
        } else {
            connection = (Connection) joinPoint.proceed();
        }
        connection.setAutoCommit(false);
        return connection;
    }
}

