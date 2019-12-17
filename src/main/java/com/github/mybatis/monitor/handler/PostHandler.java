package com.github.mybatis.monitor.handler;

import java.sql.Statement;

public interface PostHandler {
    String process(Statement statement);

}
