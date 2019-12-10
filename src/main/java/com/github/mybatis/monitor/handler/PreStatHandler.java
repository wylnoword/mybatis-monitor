package com.github.mybatis.monitor.handler;

import java.sql.Statement;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class PreStatHandler implements PreHandler{

    private ConcurrentHashMap<Statement, AtomicLong> concurrentHashMap = new ConcurrentHashMap<Statement, AtomicLong>();

    public String process(Statement statement) {
        if(concurrentHashMap.containsKey(statement)){
            return Long.toString(concurrentHashMap.get(statement).incrementAndGet());
        }
        else {
            concurrentHashMap.put(statement,new AtomicLong(1));
            return "1";
        }
    }
}
