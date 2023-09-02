package com.wenllar.holder;

public class TransactionCountHolder {

    static ThreadLocal<Integer> transactionCount = new InheritableThreadLocal<>();


    public static void set(Integer count){
        transactionCount.set(count);
    }

    public static Integer get(){
        return transactionCount.get();
    }

    public static void remove(){
        transactionCount.remove();
    }

    public static Integer increase() {
        int i = (transactionCount.get() == null ? 0 : transactionCount.get()) + 1;
        transactionCount.set(i);
        return i;
    }
}
