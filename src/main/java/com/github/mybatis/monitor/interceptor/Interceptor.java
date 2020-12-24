package com.github.mybatis.monitor.interceptor;

import com.github.mybatis.monitor.DefaultInterceptor;
import com.github.mybatis.monitor.log.SQLLogger;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.logging.jdbc.PreparedStatementLogger;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMap;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 *  MyBatis默认支持对4大对象（Executor，StatementHandler，ParameterHandler，ResultSetHandler）上的方法执行拦截，具体支持的方法为：
 *      Executor (update, query, flushStatements, commit, rollback, getTransaction, close, isClosed)，主要用于sql重写。
 *      ParameterHandler (getParameterObject, setParameters)，用于参数处理。
 *      ResultSetHandler (handleResultSets, handleOutputParameters)，用于结果集二次处理。
 *      StatementHandler (prepare, parameterize, batch, update, query)，用于jdbc层的控制。
 *@author Wang.YuLiang
 */
//过滤注解 四大处理对象过滤
@Intercepts({
        @Signature(type = StatementHandler.class, method = "query", args = {Statement.class, ResultHandler.class}),
        @Signature(type = StatementHandler.class, method = "getParameterHandler",args = {}),
        @Signature(type = StatementHandler.class, method = "prepare",args = {Connection.class,Integer.class}),
        @Signature(type = StatementHandler.class, method = "update",args ={Statement.class} )
})

public class Interceptor implements DefaultInterceptor {
    //处理时间阈值
    private long threshold;


    public Object intercept(Invocation invocation) throws Throwable {
        long begin = System.currentTimeMillis();
        Object ret = invocation.proceed();
        long end=System.currentTimeMillis();
        long runTime = end - begin;
        Object[] args = invocation.getArgs();
        Statement stat = (Statement) args[0];
        System.out.println(runTime+"ms");
        if(runTime>=threshold){
            MetaObject metaObjectStat = SystemMetaObject.forObject(stat);
            PreparedStatementLogger statementLogger = (PreparedStatementLogger)metaObjectStat.getValue("h");
            Statement statement = statementLogger.getPreparedStatement();
//            new SQLLogger()
            System.out.println("sql语句： "+statement.toString()+" 执行时间为："+runTime+"毫秒，已经超过阈值！");
        }
        return ret;
    }

    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    public void setProperties(Properties properties) {
        this.threshold = Long.parseLong(properties.getProperty("threshold"));
    }

    private DataSource checkDataSource(Statement statement) throws SQLException {
        DatabaseMetaData metaData = statement.getConnection().getMetaData();
        String driverVersion = metaData.getDriverVersion();
        return  null;
    }

}
