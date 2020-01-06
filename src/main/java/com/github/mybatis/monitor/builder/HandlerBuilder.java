package com.github.mybatis.monitor.builder;

import com.github.mybatis.monitor.handler.PreHandler;
import com.github.mybatis.monitor.handler.PreSqlHandler;
import com.github.mybatis.monitor.handler.PreStatHandler;
import com.github.mybatis.monitor.handler.PreStopWatchHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * 处理器建造
 */
public class HandlerBuilder {
    //使用配置 if控制builder构造器
    private static List<PreHandler> preHandlerList = new ArrayList<PreHandler>();

    public HandlerBuilder buildSqlHandler(){
        preHandlerList.add(new PreSqlHandler());
        return  this;
    }

    public HandlerBuilder buildStatHandler(){
        preHandlerList.add(new PreStatHandler());
        return  this;
    }

    public HandlerBuilder buildTimeHandler(){
        preHandlerList.add(new PreStopWatchHandler());
        return  this;
    }

    public List<PreHandler> build(){
        return preHandlerList;
    }

}
