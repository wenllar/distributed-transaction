package com.wenllar.holder;

import dto.WenllarBatchTransaction;

public class BatchTransactionServiceCountHolder {

    static final ThreadLocal<Integer> batchTransactionThreadLocal = new InheritableThreadLocal<>();

    public static void set(Integer counts){
        batchTransactionThreadLocal.set(counts);
    }

    public static Integer get(){
        return batchTransactionThreadLocal.get();
    }

    public static void remove(){
        batchTransactionThreadLocal.remove();
    }
}
