package com.wenllar.holder;

import dto.WenllarBatchTransaction;

public class BatchTransactionHolder {

    static final ThreadLocal<WenllarBatchTransaction> batchTransactionThreadLocal = new InheritableThreadLocal<>();

    public static void set(WenllarBatchTransaction batchTransaction){
        batchTransactionThreadLocal.set(batchTransaction);
    }

    public static WenllarBatchTransaction get(){
        return batchTransactionThreadLocal.get();
    }

    public static void remove(){
        batchTransactionThreadLocal.remove();
    }
}
