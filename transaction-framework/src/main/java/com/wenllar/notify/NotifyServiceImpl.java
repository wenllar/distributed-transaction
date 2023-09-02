package com.wenllar.notify;

import api.NotifycationService;
import com.wenllar.holder.*;
import dto.WenllarBatchTransaction;
import com.wenllar.transactional.WenllarGlobalTransactionManger;
import dto.TransactionResult;
import dto.TransactionStatus;
import org.springframework.stereotype.Component;

@Component
public class NotifyServiceImpl implements NotifycationService {
    @Override
    public void notify(TransactionResult transactionResult) {
        System.out.println("接收到分布式事务管理器的指令-----command:" + transactionResult.toString());
        // 对事务进行操作

        WenllarBatchTransaction batchTransaction = WenllarGlobalTransactionManger.getBatchTransaction(transactionResult.getGroupId());
        if (transactionResult.getTransactionStatus().equals(TransactionStatus.rollback)) {
            batchTransaction.setTransactionStatus(TransactionStatus.rollback);
        } else if (transactionResult.getTransactionStatus().equals(TransactionStatus.rollback)) {
            batchTransaction.setTransactionStatus(TransactionStatus.commit);
        }

        // 唤醒 WenllarConnection的commit 方法中，最终的事务处理逻辑
        synchronized (batchTransaction.getLock()) {
            batchTransaction.getLock().notify();
        }

        TransactionCountHolder.remove();
        GroupIdHolder.remove();
        ConnectionHolder.remove();
        BatchTransactionServiceCountHolder.remove();
    }
}
