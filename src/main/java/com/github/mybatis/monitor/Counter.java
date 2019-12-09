package com.github.mybatis.monitor;

/**
 *@author Wang.YuLiang
 **/
public interface Counter {

    /**
     * 计数增加
     * @param i 增加值
     */
    void count(int i);

    /**
     * 返回当前计数值
     * @return 当前计数值
     */
    int getCount();

    /**
     * 计数自增
     */
    void increment();

}
