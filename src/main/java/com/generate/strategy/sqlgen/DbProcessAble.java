package com.generate.strategy.sqlgen;

import com.generate.bean.FieldInfo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author zxd
 * @version v1.0.0
 * @Package : com.mysql.strategy
 * @Description : 允许数据库构建基本的表信息
 * @Create on : 2021/6/9 10:10
 **/
public interface DbProcessAble {

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

    /**
     * sql结果处理方法
     * @param tableResult
     * @param fieldList
     * @throws SQLException
     */
    void processTableInfo(String databaseType, ResultSet tableResult, List<FieldInfo> fieldList) throws SQLException;
}
