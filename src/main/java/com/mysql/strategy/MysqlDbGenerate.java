package com.mysql.strategy;

import com.mysql.bean.GlobleConfig;

import java.text.MessageFormat;

/**
 * @author zhaoxudong
 * @version v1.0.0
 * @Package : com.mysql.strategy
 * @Description : 允许进行mysql的语句构建
 * @Create on : 2021/6/9 10:18
 **/
public class MysqlDbGenerate implements DbGenerateAble {
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
}
