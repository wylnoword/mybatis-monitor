package com.github.mybatis.monitor.handler;

import java.sql.Statement;

public class PreSqlHandler implements PreHandler {

    public String process(Statement statement) {
        String sql = statement.toString();
        if(sql.contains("update")&&!sql.contains("where")){
            System.err.println("危险SQL");
            System.err.println(sql);
        }
        return sql;
    }
}
