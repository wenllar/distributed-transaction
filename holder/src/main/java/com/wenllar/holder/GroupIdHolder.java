package com.wenllar.holder;

public class GroupIdHolder {

    static final ThreadLocal<String> groupId = new InheritableThreadLocal<>();

    public static void set(String grpId){
        groupId.set(grpId);
    }

    public static String get(){
        return groupId.get();
    }


    public static void remove(){
        groupId.remove();
    }
}
