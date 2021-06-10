package com.generate.strategy;

/**
 * @author zhaoxudong
 * @version v1.0.0
 * @Package : com.mysql.strategy
 * @Description : 允许数据库构建基本的表信息
 * @Create on : 2021/6/9 10:10
 **/
public interface DbGenerateAble {

    /**
     * 创建获取所有表详细信息的SQL语句
     * @param params 需要请求参数
     * @return
     */
    String genAllTableInfoSql(String... params);

    /**
     * 创建获取所有表信息的sql语句
     * 注意：和上面的方法需要区分
     * @param params 需要请求参数
     * @return
     */
    String genAllTables(String... params);
}
