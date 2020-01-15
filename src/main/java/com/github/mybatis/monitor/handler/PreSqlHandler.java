package com.github.mybatis.monitor.handler;

import org.apache.ibatis.logging.jdbc.PreparedStatementLogger;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.sql.Statement;

/**
 * 预先定义 危险  SQL的处理器逻辑
 */
public class PreSqlHandler implements PreHandler {

    public String process(Statement statement) {
        MetaObject metaObjectStat = SystemMetaObject.forObject(statement);
        PreparedStatementLogger statementLogger = (PreparedStatementLogger)metaObjectStat.getValue("h");
        Statement statement1 = statementLogger.getPreparedStatement();
        String sql = statement1.toString();
        //sql预处理器逻辑定义
        if(sql.contains("select")&&sql.contains("where")){
            System.err.println("危险SQL");
            System.err.println(sql);
        }
        return sql;
    }
}
