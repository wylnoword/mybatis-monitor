package com.github.mybatis.monitor.handler;

import java.sql.Statement;

public class PostStopWatchHandler implements PostHandler{

    public String process(Statement statement) {
        return Long.toString(System.currentTimeMillis());
    }
}
