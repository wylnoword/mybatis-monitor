package com.github.mybatis.monitor.handler;

import java.sql.Statement;

/**
 *@author Wang.YuLiang
 */
public interface PostHandler {
    String process(Statement statement);

}
