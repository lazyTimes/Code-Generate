package com.generate.typeconvert;

import com.generate.enums.DbEnum;

import java.util.*;

/**
 * @author zhaoxudong
 * @version v1.0.0
 * @Package : com.generate.typeconvert
 * @Description : postgresql 数据库类型转化map
 * @Create on : 2021/6/10 18:14
 **/
public class DbTypeConvertBean implements DbTypeConvert {

    @Override
    public Map<String, String> getTypeMapping(String databaseType) {
        DbEnum enumOrNullByName = DbEnum.getEnumOrNullByName(databaseType);
        if (Objects.isNull(enumOrNullByName)) {
            return Collections.emptyMap();
        }
        if (enumOrNullByName == DbEnum.POSTGRESQL) {
            return Postgresql.getPostgresqlTypeMapping();
        }
        return Mysql.getMysqlTypeMapping();
    }

    private static class Mysql {

        private static final Map<String, String> MYSQL_TYPE_MAPPING = new HashMap<>(48);

        static {
            // ===================== mysql ===================== //
            MYSQL_TYPE_MAPPING.put("int", "Integer");
            MYSQL_TYPE_MAPPING.put("char", "String");
            MYSQL_TYPE_MAPPING.put("varchar", "String");
            MYSQL_TYPE_MAPPING.put("datetime", "Date");
            // 使用java.util.Timestamp 时间戳对象
            MYSQL_TYPE_MAPPING.put("timestamp", "Timestamp");
            MYSQL_TYPE_MAPPING.put("bit", "Integer");
            MYSQL_TYPE_MAPPING.put("tinyint", "Integer");
            MYSQL_TYPE_MAPPING.put("smallint", "Integer");
            MYSQL_TYPE_MAPPING.put("year", "Date");
            MYSQL_TYPE_MAPPING.put("blob", "String");
            MYSQL_TYPE_MAPPING.put("date", "Date");
            MYSQL_TYPE_MAPPING.put("bigint", "Long");
            MYSQL_TYPE_MAPPING.put("decimal", "BigDecimal");
            MYSQL_TYPE_MAPPING.put("double", "Double");
            MYSQL_TYPE_MAPPING.put("float", "Float");
            MYSQL_TYPE_MAPPING.put("numeric", "Integer");
            MYSQL_TYPE_MAPPING.put("text", "String");
            MYSQL_TYPE_MAPPING.put("mediumtext", "String");
            MYSQL_TYPE_MAPPING.put("longtext", "String");
            MYSQL_TYPE_MAPPING.put("time", "Date");
            // ===================== mysql ===================== //
        }

        public static Map<String, String> getMysqlTypeMapping() {
            return MYSQL_TYPE_MAPPING;
        }
    }

    public static class Oracle {
    }

    /**
     * 特殊要求；但凡Postgresql数据库请务必填写注释，否则无法生成指定字段。
     * 具体原因请尝试使用如下的sql：
     * <pre>
     SELECT A
     .attname AS COLUMN_NAME,
     T.typname AS data_type,
     d.description AS column_comment,
     btrim( SUBSTRING ( format_type ( A.atttypid, A.atttypmod ) FROM '(.*)' ), '()' ) AS character_maximum_length,
     CASE

     WHEN A.attnotnull = 'f' THEN
     'NO'
     WHEN A.attnotnull = 't' THEN
     'YES' ELSE'NO'
     END AS NULLABLE
     FROM
     pg_class C,
     pg_attribute A,
     pg_type T,
     pg_description d
     WHERE
     C.relname = 'test_postgresql' <-- 更改此处的表名称
     AND A.attnum > 0
     AND A.attrelid = C.oid
     AND A.atttypid = T.oid
     AND d.objoid = A.attrelid
     AND d.objsubid = A.attnum
     * </pre>
     */
    public static class Postgresql {
        private static final Map<String, String> POSTGRESQL_TYPE_MAPPING = new HashMap<>(48);

        static {
            // ===================== mysql ===================== //
            // postgresql 按照 int2 int4 int8进行范围限制
            POSTGRESQL_TYPE_MAPPING.put("int", "Integer");
            POSTGRESQL_TYPE_MAPPING.put("int2", "Integer");
            POSTGRESQL_TYPE_MAPPING.put("int4", "Integer");
            POSTGRESQL_TYPE_MAPPING.put("int8", "Long");
            POSTGRESQL_TYPE_MAPPING.put("char", "String");
            POSTGRESQL_TYPE_MAPPING.put("varchar", "String");
            POSTGRESQL_TYPE_MAPPING.put("timestamp", "Timestamp");
            POSTGRESQL_TYPE_MAPPING.put("bit", "Integer");
            // postgresql json类型，通常需要手写mybatis type convert。对于数据类型进行转化
            POSTGRESQL_TYPE_MAPPING.put("json", "JSON");
            POSTGRESQL_TYPE_MAPPING.put("date", "Date");
            POSTGRESQL_TYPE_MAPPING.put("decimal", "BigDecimal");
            POSTGRESQL_TYPE_MAPPING.put("double", "Double");
            POSTGRESQL_TYPE_MAPPING.put("float", "Float");
            POSTGRESQL_TYPE_MAPPING.put("numeric", "Integer");
            POSTGRESQL_TYPE_MAPPING.put("text", "String");
            // 布尔类型用string 存储为 f,t
            POSTGRESQL_TYPE_MAPPING.put("bool", "String");
            POSTGRESQL_TYPE_MAPPING.put("money", "String");
            POSTGRESQL_TYPE_MAPPING.put("uuid", "String");
            POSTGRESQL_TYPE_MAPPING.put("point", "String");
            POSTGRESQL_TYPE_MAPPING.put("longtext", "String");
            POSTGRESQL_TYPE_MAPPING.put("time", "Date");
            // ===================== mysql ===================== //
        }

        public static Map<String, String> getPostgresqlTypeMapping() {
            return POSTGRESQL_TYPE_MAPPING;
        }
    }


}
