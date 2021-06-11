package com.generate.strategy.sqlgen;

import com.generate.bean.FieldInfo;
import com.generate.bean.GlobleConfig;
import com.generate.typeconvert.TypeConvertFactory;
import com.generate.util.StringUtil;
import org.apache.commons.lang3.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.List;

/**
 * @author zhaoxudong
 * @version v1.0.0
 * @Package : com.mysql.strategy
 * @Description : 允许进行mysql的语句构建
 * @Create on : 2021/6/9 10:18
 **/
public class MysqlDbProcess implements DbProcessAble {

    private static final TypeConvertFactory TYPE_CONVERT_FACTORY = new TypeConvertFactory();

    @Override
    public String genAllTableInfoSql(String... params) {
        return MessageFormat.format("select column_name,data_type,column_comment,numeric_precision," +
                "numeric_scale,character_maximum_length,is_nullable nullable from information_schema.columns " +
                "where table_name = \"{0}\" and table_schema = \"{1}\"", params[0], GlobleConfig.getGlobleConfig().getDataBase());
    }

    @Override
    public String genAllTables(String... params) {
        return MessageFormat.format("select table_name from information_schema.tables where table_schema=\"{0}\" and table_type=\"{1}\";",
                GlobleConfig.getGlobleConfig().getDataBase(), "base table");
    }

    @Override
    public void processTableInfo(ResultSet tableResult, List<FieldInfo> fieldList) throws SQLException {
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
}
