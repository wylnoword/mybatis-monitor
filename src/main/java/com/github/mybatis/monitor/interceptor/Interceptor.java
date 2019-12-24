package com.github.mybatis.monitor.interceptor;

import com.github.mybatis.monitor.DefaultInterceptor;
import com.github.mybatis.monitor.builder.HandlerBuilder;
import com.github.mybatis.monitor.handler.PreHandler;
import com.github.mybatis.monitor.handler.PreSqlHandler;
import com.github.mybatis.monitor.handler.PreStatHandler;
import com.github.mybatis.monitor.handler.PreStopWatchHandler;
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
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

/**
 *@author Wang.YuLiang
 */
@Intercepts({
        @Signature(type= StatementHandler.class,method="query",args={Statement.class, ResultHandler.class})
//	@Signature(type=StatementHandler.class,method="query",args={MappedStatement.class,Object.class, RowBounds.class, ResultHandler.class})
})

public class Interceptor implements DefaultInterceptor {
    //处理时间阈值
    private long threshold;

    private ConcurrentHashMap<Statement, AtomicLong> sqlTime;


    public Object intercept(Invocation invocation) throws Throwable {
        HandlerBuilder handlerBuilder = new HandlerBuilder();
        List<PreHandler> preHandlerList = handlerBuilder
                .buildSqlHandler()
                .buildStatHandler()
                .buildTimeHandler()
                .build();
        Object[] args = invocation.getArgs();
        Statement stat = (Statement) args[0];
        for (PreHandler preHandler : preHandlerList) {
            String process = preHandler.process(stat);
            //输出处理结果
            //todo 使用log进行记录
            System.out.println(process);
        }
        long begin = System.currentTimeMillis();
        Object ret = invocation.proceed();
        long end=System.currentTimeMillis();
        long runTime = end - begin;
        if(runTime>=threshold){
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
