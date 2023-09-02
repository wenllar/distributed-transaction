package com.wenllar.transaction.store;

import api.TransactionRegistService;
import dto.WenllarBatchTransaction;
import com.wenllar.transaction.notify.NotifycationComponent;
import dto.TransactionResult;
import dto.TransactionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Component
public class TransactionRegistServiceImpl implements TransactionRegistService {

    // 事务组中的事务状态列表
    private static final Map<String, List<String>> transactionStatusMap = new HashMap<>();
    // 事务组是否已经接收到结束的标记
    private static final Map<String, Boolean> isEndMap = new HashMap<>();
    // 事务组中应该有的事务个数
    private static final Map<String, Integer> transactionCountMap = new HashMap<>();

    @Autowired
    NotifycationComponent notifycationComponent;

    @Override
    public String registGlobalTransaction(String groupId) {
        if( CollectionUtils.isEmpty(TransactionRegistry.GROUP_TRANSACTION.get(groupId)) ){
            TransactionRegistry.GROUP_TRANSACTION.put(groupId, new ArrayList<>());
        }
        return "OK";
    }

    @Override
    public String registBatchTransaction(WenllarBatchTransaction batchTransaction, boolean isEnd) {
        TransactionRegistry.GROUP_TRANSACTION.get(batchTransaction.getGroupId()).add(batchTransaction.getTransactionStatus());
        if (isEnd) {
            isEndMap.put(batchTransaction.getGroupId(), true);
        }

        /**
         * 确保已经收到所有微服务注册的分支事务
         */
        if(isEndMap.get(batchTransaction.getGroupId()) &&
                batchTransaction.getBatchTransactionServiceCount() == TransactionRegistry.GROUP_TRANSACTION.get(batchTransaction.getGroupId()).size()
        ){
            TransactionResult transactionResult = new TransactionResult();
            transactionResult.setGroupId(batchTransaction.getGroupId());
            transactionResult.setTransactionStatus(TransactionStatus.commit);
            TransactionRegistry.GROUP_TRANSACTION.get(batchTransaction.getGroupId()).stream().forEach(transactionStatus -> {
                if(TransactionStatus.rollback.equals(transactionStatus)){
                    transactionResult.setTransactionStatus(TransactionStatus.rollback);
                    return;
                }
            });
            new NotifycationComponent().notify(transactionResult);
        }
        return "OK";
    }
}
