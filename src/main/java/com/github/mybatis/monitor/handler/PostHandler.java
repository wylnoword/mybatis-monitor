package com.github.mybatis.monitor.handler;

import java.sql.Statement;

/**
 *@author Wang.YuLiang
 * 后置处理器
 */
public interface PostHandler {
    String process(Statement statement);

}
