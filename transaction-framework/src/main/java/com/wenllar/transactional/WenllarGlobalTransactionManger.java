package com.wenllar.transactional;

import api.TransactionRegistService;
import com.wenllar.holder.BatchTransactionServiceCountHolder;
import com.wenllar.holder.GroupIdHolder;
import com.wenllar.holder.TransactionCountHolder;
import dto.WenllarBatchTransaction;
import com.wenllar.holder.BatchTransactionHolder;
import dto.TransactionStatus;
import reflect.PorxyFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WenllarGlobalTransactionManger {


    private static final Map<String, WenllarBatchTransaction> batchTransactionMap = new HashMap<>();

    /**
     * 创建全局事务
     * @return
     */
    public static String createGlobalTransaction(){
       String groupId = UUID.randomUUID().toString();

       // 注册全局事务
        TransactionRegistService proxy = PorxyFactory.getProxy(TransactionRegistService.class);
        proxy.registGlobalTransaction(groupId);
        GroupIdHolder.set(groupId);
        BatchTransactionServiceCountHolder.set(2);
        return groupId;
    }

    /**
     * 创建分支事务
     * @return
     */
    public static WenllarBatchTransaction createBatchTransaction(String groupId){
        String transactionId = UUID.randomUUID().toString();
        WenllarBatchTransaction batchTransaction = new WenllarBatchTransaction(groupId, transactionId);
        batchTransactionMap.put(groupId, batchTransaction);
        BatchTransactionHolder.set(batchTransaction);
        TransactionCountHolder.increase();
        return batchTransaction;
    }



    /**
     * 注册分支事务
     * @return
     */
    public static  void registerBatchTransaction(WenllarBatchTransaction wenllarBatchTransaction, TransactionStatus transactionStatus, boolean isEnd){
        wenllarBatchTransaction.setTransactionStatus(transactionStatus);
        wenllarBatchTransaction.setTransactionCounts(TransactionCountHolder.get());
        wenllarBatchTransaction.setBatchTransactionServiceCount(BatchTransactionServiceCountHolder.get());
        // 注册全局事务
        TransactionRegistService proxy = PorxyFactory.getProxy(TransactionRegistService.class);
        proxy.registBatchTransaction(wenllarBatchTransaction, isEnd);// TODO
    }

    public static WenllarBatchTransaction getBatchTransaction(String groupId) {
        return batchTransactionMap.get(groupId);
    }

    /**
     * 获取当前线程中的分支事务
     * @return
     */
    public static WenllarBatchTransaction getCurrentBatchTransaction(){
        return BatchTransactionHolder.get();
    }
}
