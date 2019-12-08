package com.github.mybatis.monitor;

import org.apache.ibatis.plugin.Interceptor;

import java.sql.Statement;

/**
 * @author Wang.YuLiang
 **/

public interface DefaultInterceptor extends Interceptor {

    /**
     * 对SQL执行语句拦截
     * @param statement 拦截的执行对象
     * @return 拦截原因
     */
    Object interceptStatement(Statement statement);
}
