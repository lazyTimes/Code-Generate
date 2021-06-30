package com.generate.strategy.sqlgen;

import com.generate.bean.FieldInfo;
import com.generate.bean.PropertiesConfig;
import com.generate.typeconvert.TypeConvertFactory;
import com.generate.util.DbCheckUtils;
import org.apache.commons.lang3.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author zxd
 * @version v1.0.0
 * @Package : com.mysql.strategy
 * @Description : PostgreSql 数据库相关语句生成
 * @Create on : 2021/6/9 10:20
 **/
public class PostgreSqlDbProcess implements DbProcessAble {

    private static final TypeConvertFactory TYPE_CONVERT_FACTORY = new TypeConvertFactory();

    @Override
    public String genAllTableInfoSql(String... params) {
        return String.format("select a.attname as column_name,t.typname as data_type,d.description as column_comment, btrim(SUBSTRING(format_type(a.atttypid,a.atttypmod) from '\\(.*\\)'), '()') as character_maximum_length, case when a.attnotnull ='f' THEN 'NO' WHEN a.attnotnull = 't' then 'YES' else 'NO' END  as nullable from pg_class c, pg_attribute a , pg_type t, pg_description d where  c.relname = '%s' and a.attnum>0 and a.attrelid = c.oid and a.atttypid = t.oid and  d.objoid=a.attrelid and d.objsubid=a.attnum", params[0]);
    }

    @Override
    public String genAllTables(String... params) {
        return "SELECT relname AS TABLE_NAME FROM pg_class C WHERE relkind = 'r' AND relname NOT LIKE'pg_%' AND relname NOT LIKE'sql_%' ORDER BY relname";
    }

    @Override
    public void processTableInfo(String databaseType, ResultSet tableResult, List<FieldInfo> fieldList) throws SQLException {
        while (tableResult.next()) {
            FieldInfo fieldInfo = new FieldInfo();
            fieldInfo.setColumnName(tableResult.getString(1));
            fieldInfo.setFieldClass(TYPE_CONVERT_FACTORY.getTypeMapping(databaseType).get(tableResult.getString(2)));
            String fieldName = DbCheckUtils.underlineToCamelCaseAndReplaceDbFieldName(tableResult.getString(1));
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
}
