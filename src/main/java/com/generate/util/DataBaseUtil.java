package com.generate.util;

import cn.hutool.core.collection.CollectionUtil;
import com.generate.bean.ClassInfo;
import com.generate.bean.FieldInfo;
import com.generate.bean.GlobleConfig;
import com.generate.strategy.sqlgen.GenerateContext;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * ******************************
 * author：      柯贤铭
 * createTime:   2020/1/9 18:03
 * description:  库解析工具类
 * 解析表和字段
 * version:      V1.0
 * ******************************
 */
public class DataBaseUtil {

    private static final GenerateContext GENERATE_CONTEXT = new GenerateContext();


    /***
     * 根据指定库获取单表相关参数
     * @param tableName   表名
     */
    public static ClassInfo parseClassInfo(String tableName) throws SQLException {
        // tableSql
        String tableInfoSql = GENERATE_CONTEXT.genGenerator(GlobleConfig.getGlobleConfig().getDataBaseType()).genAllTableInfoSql(tableName);
        Statement statement = DBUtil.getConnection().createStatement();
        ResultSet tableResult = statement.executeQuery(tableInfoSql);
        // 构建ClassInfo信息
        ClassInfo classInfo = new ClassInfo();
        classInfo.setTableName(tableName);
        // className信息
        String className = StringUtil.upperCaseFirst(StringUtil.underlineToCamelCaseAndReplaceDbFieldName(tableName));
        classInfo.setClassName(className);
        classInfo.setModelName(StringUtil.lowerCaseFirst(className));
        classInfo.setClassComment(className);
        List<FieldInfo> fieldList = new ArrayList<>();
        // 1 column_name, 2 data_type 3 column_comment
        // TODO 面向MYSQL具体信息编程
        // 暂时改为使用 postgresql 数据库处理
        processTableInfo(tableResult, fieldList);
        classInfo.setFieldList(fieldList);
        // 设置主键字段
        if (CollectionUtil.isEmpty(fieldList)) {
            classInfo.setKey(new FieldInfo());
        } else {
            classInfo.setKey(fieldList.get(0));
        }
        tableResult.close();
        statement.close();
        return classInfo;
    }

    private static void processTableInfo(ResultSet tableResult, List<FieldInfo> fieldList) throws SQLException {
        GENERATE_CONTEXT.genGenerator(GlobleConfig.getGlobleConfig().getDataBaseType())
                .processTableInfo(tableResult, fieldList);
    }

    /***
     * 根据指定库获取所有表名
     */
    public static List<String> getAllTableNames() throws SQLException {
        // result
        List<String> result = new ArrayList<>();
        // sql
        String sql = GENERATE_CONTEXT.genGenerator(GlobleConfig.getGlobleConfig().getDataBaseType()).genAllTables();
        Statement statement = DBUtil.getConnection().createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            result.add(rs.getString(1));
        }
        return result;
    }

}
