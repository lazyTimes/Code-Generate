package com.generate.util;

import cn.hutool.core.collection.CollectionUtil;
import com.generate.bean.ClassInfo;
import com.generate.bean.FieldInfo;
import com.generate.bean.GlobleConfig;
import com.generate.strategy.sqlgen.DbGenerateAble;
import com.generate.strategy.sqlgen.PostgreSqlDbGenerate;
import com.generate.typeconvert.DbTypeConvertBean;
import com.generate.typeconvert.TypeConvertFactory;
import org.apache.commons.lang3.StringUtils;

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

    private static final DbGenerateAble DB_GENERATE_ABLE = new PostgreSqlDbGenerate();

    private static final TypeConvertFactory TYPE_CONVERT_FACTORY = new TypeConvertFactory();

    /***
     * 根据指定库获取单表相关参数
     * @param tableName   表名
     */
    public static ClassInfo parseClassInfo(String tableName) throws SQLException {
        // tableSql
        String tableInfoSql = getTableInfoSql(tableName);
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
        processPostgreSqlTableInfo(tableResult, fieldList);
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

    private static void processPostgreSqlTableInfo(ResultSet tableResult, List<FieldInfo> fieldList) throws SQLException {
        while (tableResult.next()) {
            FieldInfo fieldInfo = new FieldInfo();
            fieldInfo.setColumnName(tableResult.getString(1));
            fieldInfo.setFieldClass(TYPE_CONVERT_FACTORY.getTypeMapping(GlobleConfig.getGlobleConfig().getDataBaseType()).get(tableResult.getString(2)));
            String fieldName = StringUtil.underlineToCamelCaseAndReplaceDbFieldName(tableResult.getString(1));
            fieldInfo.setFieldName(fieldName);
            fieldInfo.setFieldComment(tableResult.getString(3));
            // 维护表结构字段 2 data_type,4 6 length, 5 nullAble
            fieldInfo.setDataType(tableResult.getString(2));
            // postgresql 如果没有长度类型，则默认为0
            fieldInfo.setMaxLength(StringUtils.isNotBlank(tableResult.getString(4)) ? tableResult.getString(4) : "0");
            fieldInfo.setNullAble(tableResult.getString(5));
            fieldList.add(fieldInfo);
        }
    }

    /**
     * 处理mysql数据库查询字段信息
     *
     * @param tableResult
     * @param fieldList
     * @throws SQLException
     */
    private static void processMysqlTableInfo(ResultSet tableResult, List<FieldInfo> fieldList) throws SQLException {
        while (tableResult.next()) {
            FieldInfo fieldInfo = new FieldInfo();
            fieldInfo.setColumnName(tableResult.getString(1));
            fieldInfo.setFieldClass(TYPE_CONVERT_FACTORY.getTypeMapping(GlobleConfig.getGlobleConfig().getDataBaseType()).get(tableResult.getString(2)));
            String fieldName = StringUtil.underlineToCamelCaseAndReplaceDbFieldName(tableResult.getString(1));
            fieldInfo.setFieldName(fieldName);
            fieldInfo.setFieldComment(tableResult.getString(3));
            // 维护表结构字段 2 data_type,4 6 length, 7 nullAble
            fieldInfo.setDataType(tableResult.getString(2));
            fieldInfo.setMaxLength(StringUtils.isNotBlank(tableResult.getString(4)) ? tableResult.getString(4) : tableResult.getString(6));
            fieldInfo.setNullAble(tableResult.getString(7));
            fieldList.add(fieldInfo);
        }
    }

    /***
     * 根据指定库获取所有表名
     */
    public static List<String> getAllTableNames() throws SQLException {
        // result
        List<String> result = new ArrayList<>();
        // sql
        String sql = getTables();
        Statement statement = DBUtil.getConnection().createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            result.add(rs.getString(1));
        }
        return result;
    }

    /***
     * TableInfo SQL
     * @param tableName tableName
     */
    // todo: mysql设置对应的数据库配置。由于不同的数据库属性可能不一样，需要区别对待
    private static String getTableInfoSql(String tableName) {
        return DB_GENERATE_ABLE.genAllTableInfoSql(tableName);
    }

    /***
     * 获取所有Tables SQL
     */
    // todo: mysql设置对应的数据库配置。由于不同的数据库属性可能不一样，需要区别对待
    private static String getTables() {
        return DB_GENERATE_ABLE.genAllTables();
    }


}
