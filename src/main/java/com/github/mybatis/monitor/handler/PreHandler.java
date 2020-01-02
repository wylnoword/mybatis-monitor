package com.github.mybatis.monitor.handler;

import java.sql.Statement;

/**
 * 预处理器
 */
public interface PreHandler {
    String process(Statement statement);
}
