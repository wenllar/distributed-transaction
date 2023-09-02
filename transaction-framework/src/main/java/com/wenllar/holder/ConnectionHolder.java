package com.wenllar.holder;

import java.sql.Connection;

public class ConnectionHolder {

    static final ThreadLocal<Connection> connectionLocal = new ThreadLocal<>();

    public static void set(Connection connection){
        connectionLocal.set(connection);
    }

    public static Connection get(){
        return connectionLocal.get();
    }

    public static void remove(){
        connectionLocal.remove();
    }

}
