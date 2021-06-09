package com.mysql.strategy;

import com.mysql.bean.GlobleConfig;

import java.text.MessageFormat;

/**
 * @author zhaoxudong
 * @version v1.0.0
 * @Package : com.mysql.strategy
 * @Description : PostgreSql 数据库相关语句生成
 * @Create on : 2021/6/9 10:20
 **/
public class PostgreSqlDbGenerate implements DbGenerateAble{
    @Override
    public String genAllTableInfoSql(String... params) {
        return String.format("select a.attname as column_name,t.typname as data_type,d.description as column_comment, btrim(SUBSTRING(format_type(a.atttypid,a.atttypmod) from '\\(.*\\)'), '()') as character_maximum_length, case when a.attnotnull ='f' THEN 'NO' WHEN a.attnotnull = 't' then 'YES' else 'NO' END  as nullable from pg_class c, pg_attribute a , pg_type t, pg_description d where  c.relname = '%s' and a.attnum>0 and a.attrelid = c.oid and a.atttypid = t.oid and  d.objoid=a.attrelid and d.objsubid=a.attnum", params[0]);
    }

    @Override
    public String genAllTables(String... params) {
        return "SELECT relname AS TABLE_NAME FROM pg_class C WHERE relkind = 'r' AND relname NOT LIKE'pg_%' AND relname NOT LIKE'sql_%' ORDER BY relname";
    }
}
