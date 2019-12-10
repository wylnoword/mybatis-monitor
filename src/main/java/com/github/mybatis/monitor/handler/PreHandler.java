package com.github.mybatis.monitor.handler;

import java.sql.Statement;

public interface PreHandler {
    String process(Statement statement);
}
