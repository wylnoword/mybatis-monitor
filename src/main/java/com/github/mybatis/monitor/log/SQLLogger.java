package com.github.mybatis.monitor.log;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.jdbc.BaseJdbcLogger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class SQLLogger extends BaseJdbcLogger implements InvocationHandler {

    public SQLLogger(Log log, int queryStack) {
        super(log, queryStack);
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return null;
    }
}
