package com.wenllar.transaction.store;

import dto.TransactionStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public interface TransactionRegistry {

    /**
     * 全局事务
     */
    Map<String, ArrayList<TransactionStatus>> GROUP_TRANSACTION = new HashMap<>();


}
