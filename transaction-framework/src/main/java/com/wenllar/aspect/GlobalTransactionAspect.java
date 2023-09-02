package com.wenllar.aspect;

import com.wenllar.holder.BatchTransactionHolder;
import com.wenllar.holder.BatchTransactionServiceCountHolder;
import dto.WenllarBatchTransaction;
import com.wenllar.annotation.GlobalTransaction;
import com.wenllar.holder.GroupIdHolder;
import com.wenllar.transactional.WenllarGlobalTransactionManger;
import dto.TransactionStatus;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Aspect
@Component
public class GlobalTransactionAspect implements Ordered {
    @Around("@annotation( com.wenllar.annotation.GlobalTransaction)")
    public String around(ProceedingJoinPoint joinPoint) {
        // 创建和注册全局事务
        String groupId = GroupIdHolder.get();
        String result = "";
        if(Objects.isNull(groupId)){
            groupId = WenllarGlobalTransactionManger.createGlobalTransaction();
        }


        // 创建分支事务
        WenllarBatchTransaction wenllarBatchTransaction = WenllarGlobalTransactionManger.createBatchTransaction(groupId);
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        GlobalTransaction annotation = signature.getMethod().getAnnotation(GlobalTransaction.class);
        boolean isEnd = annotation.isEnd();
        try {
            // 执行@Transactional切面的逻辑，也就是加了@Transactiona注解的方法的逻辑
          result = (String) joinPoint.proceed(); // Spring事务

            // 注册分支事务: 若无异常，分支事务提交
            WenllarGlobalTransactionManger.registerBatchTransaction(wenllarBatchTransaction, TransactionStatus.commit, isEnd);
        } catch (Throwable throwable) {
            //  注册分支事务: 若有异常，分支事务回滚
            WenllarGlobalTransactionManger.registerBatchTransaction(wenllarBatchTransaction, TransactionStatus.rollback, isEnd);
            throw new RuntimeException(throwable);
        }
        GroupIdHolder.remove();

        return result;
    }

    @Override
    public int getOrder() {
        return 10000;
    }
}

