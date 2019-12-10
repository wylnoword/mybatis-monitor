package com.github.mybatis.monitor.handler;

import java.sql.Statement;

public class PreStopWatchHandler implements PreHandler{

    public String process(Statement statement) {
        return Long.toString(System.currentTimeMillis());
    }
}
