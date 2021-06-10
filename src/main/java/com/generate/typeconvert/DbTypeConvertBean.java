package com.generate.typeconvert;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhaoxudong
 * @version v1.0.0
 * @Package : com.generate.typeconvert
 * @Description : postgresql 数据库类型转化map
 * @Create on : 2021/6/10 18:14
 **/
public class DbTypeConvertBean implements DbTypeConvert{

    private static final Map<String, String> MYSQL_TYPE_MAPPING = new HashMap<>(48);

    @Override
    public Map<String, String> getMysqlTypeMapping() {
        return new Default().getMysqlTypeMapping();
    }

    private class Default extends DbTypeConvertBean {
        @Override
        public Map<String, String> getMysqlTypeMapping() {
            return super.getMysqlTypeMapping();
        }
    }

    private static class Mysql extends DbTypeConvertBean{
        // todo: mysql设置对应的数据库配置。由于不同的数据库属性可能不一样，需要区别对待
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

        public Map<String, String> getMysqlTypeMapping() {
            return MYSQL_TYPE_MAPPING;
        }
    }
}
