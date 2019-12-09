package com.github.mybatis.monitor.interceptor;

import com.github.mybatis.monitor.DefaultInterceptor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.logging.jdbc.PreparedStatementLogger;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.ResultHandler;

import java.sql.Statement;
import java.util.Properties;

/**
 *@author Wang.YuLiang
 */
@Intercepts({
        @Signature(type= StatementHandler.class,method="query",args={Statement.class, ResultHandler.class})
//	@Signature(type=StatementHandler.class,method="query",args={MappedStatement.class,Object.class, RowBounds.class, ResultHandler.class})
})

public class Interceptor implements DefaultInterceptor {

    private long threshold;

    public Object intercept(Invocation invocation) throws Throwable {
        long begin = System.currentTimeMillis();
        Object ret = invocation.proceed();
        long end=System.currentTimeMillis();
        long runTime = end - begin;
        if(runTime>=threshold){
            Object[] args = invocation.getArgs();
            Statement stat = (Statement) args[0];
            MetaObject metaObjectStat = SystemMetaObject.forObject(stat);
            PreparedStatementLogger statementLogger = (PreparedStatementLogger)metaObjectStat.getValue("h");
            Statement statement = statementLogger.getPreparedStatement();
            System.out.println("sql语句：“"+statement.toString()+"”执行时间为："+runTime+"毫秒，已经超过阈值！");
        }
        return ret;
    }

    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    public void setProperties(Properties properties) {
        this.threshold = Long.parseLong(properties.getProperty("threshold"));
    }
}
