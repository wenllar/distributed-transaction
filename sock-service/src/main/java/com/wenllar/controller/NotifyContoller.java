package com.wenllar.controller;

import api.NotifycationService;
import com.wenllar.holder.BatchTransactionHolder;
import com.wenllar.transactional.WenllarGlobalTransactionManger;
import dto.TransactionResult;
import dto.TransactionStatus;
import dto.WenllarBatchTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotifyContoller {

    NotifycationService notifycationService;

    @Autowired
    public void setNotifycationService(NotifycationService notifycationService) {
        this.notifycationService = notifycationService;
    }

    @RequestMapping("/notify")
    public String notify(@RequestParam(name = "Transaction-Group-ID") String groupId, @RequestParam(name = "action") String action){
        TransactionResult transactionResult = new TransactionResult();
        transactionResult.setGroupId(groupId);
        TransactionStatus result = null;
        for(TransactionStatus transactionStatus: TransactionStatus.values()){
            if(transactionStatus.getValue().equals(action)){
                result = transactionStatus;
                break;
            }
        }
        transactionResult.setTransactionStatus(result);
        this.notifycationService.notify(transactionResult);
        return transactionResult.getTransactionStatus().getValue();
    }
}
