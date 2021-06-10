package com.generate.util;

import com.generate.bean.GlobleConfig;
import com.generate.enums.DbEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhaoxudong
 * @version v1.0.0
 * @Package : com.generate.util
 * @Description : 和数据库相关的工具类，包含数据库关键字过滤方法
 * @Create on : 2021/6/10 16:25
 **/
public class DbCheckUtils {


    /***
     * MySQL 关键字及保留字
     */
//    public final static String KEY_WORD = "ADD;ALL;ALTER;ANALYZE;AND;AS;ASC;ASENSITIVE;BEFORE;BETWEEN;BIGINT;BINARY;BLOB;BOTH;BY;CALL;CASCADE;CASE;CHANGE;CHAR;CHARACTER;CHECK;COLLATE;COLUMN;CONDITION;CONNECTION;CONSTRAINT;CONTINUE;CONVERT;CREATE;CROSS;CURRENT_DATE;CURRENT_TIME;CURRENT_TIMESTAMP;CURRENT_USER;CURSOR;DATABASE;DATABASES;DAY_HOUR;DAY_MICROSECOND;DAY_MINUTE;DAY_SECOND;DEC;DECIMAL;DECLARE;DEFAULT;DELAYED;DELETE;DESC;DESCRIBE;DETERMINISTIC;DISTINCT;DISTINCTROW;DIV;DOUBLE;DROP;DUAL;EACH;ELSE;ELSEIF;ENCLOSED;ESCAPED;EXISTS;EXIT;EXPLAIN;FALSE;FETCH;FLOAT;FLOAT4;FLOAT8;FOR;FORCE;FOREIGN;FROM;FULLTEXT;GOTO;GRANT;GROUP;HAVING;HIGH_PRIORITY;HOUR_MICROSECOND;HOUR_MINUTE;HOUR_SECOND;IF;IGNORE;IN;INDEX;INFILE;INNER;INOUT;INSENSITIVE;INSERT;INT;INT1;INT2;INT3;INT4;INT8;INTEGER;INTERVAL;INTO;IS;ITERATE;JOIN;KEY;KEYS;KILL;LABEL;LEADING;LEAVE;LEFT;LIKE;LIMIT;LINEAR;LINES;LOAD;LOCALTIME;LOCALTIMESTAMP;LOCK;LONG;LONGBLOB;LONGTEXT;LOOP;LOW_PRIORITY;MATCH;MEDIUMBLOB;MEDIUMINT;MEDIUMTEXT;MIDDLEINT;MINUTE_MICROSECOND;MINUTE_SECOND;MOD;MODIFIES;NATURAL;NOT;NO_WRITE_TO_BINLOG;NULL;NUMERIC;ON;OPTIMIZE;OPTION;OPTIONALLY;OR;ORDER;OUT;OUTER;OUTFILE;PRECISION;PRIMARY;PROCEDURE;PURGE;RAID0;RANGE;READ;READS;REAL;REFERENCES;REGEXP;RELEASE;RENAME;REPEAT;REPLACE;REQUIRE;RESTRICT;RETURN;REVOKE;RIGHT;RLIKE;SCHEMA;SCHEMAS;SECOND_MICROSECOND;SELECT;SENSITIVE;SEPARATOR;SET;SHOW;SMALLINT;SPATIAL;SPECIFIC;SQL;SQLEXCEPTION;SQLSTATE;SQLWARNING;SQL_BIG_RESULT;SQL_CALC_FOUND_ROWS;SQL_SMALL_RESULT;SSL;STARTING;STRAIGHT_JOIN;TABLE;TERMINATED;THEN;TINYBLOB;TINYINT;TINYTEXT;TO;TRAILING;TRIGGER;TRUE;UNDO;UNION;UNIQUE;UNLOCK;UNSIGNED;UPDATE;USAGE;USE;USING;UTC_DATE;UTC_TIME;UTC_TIMESTAMP;VALUES;VARBINARY;VARCHAR;VARCHARACTER;VARYING;WHEN;WHERE;WHILE;WITH;WRITE;X509;XOR;YEAR_MONTH;ZEROFILL";

    private final static Map<String, String> KEY_MAP = new HashMap<>(256);

    static {
        DbCheckUtils.parseKeyWords();
    }

    /***
     * 解析MySQL 关键字
     */
    private static void parseKeyWords() {
        String dataBaseType = GlobleConfig.getGlobleConfig().getDataBaseType();
        DbEnum enumOrNullByName = DbEnum.getEnumOrNullByName(dataBaseType);
        if (null == enumOrNullByName) {
            throw new RuntimeException("当前系统不支持此数据库类型! db = " + dataBaseType);
        }
        String[] keys = enumOrNullByName.getKeyWord().split(";");
        for (String key : keys) {
            KEY_MAP.put(key.toUpperCase(), key);
        }
    }

    /**
     * 校验当前字段是否匹配上mysql关键字
     *
     * @param key
     * @return
     */
    public static boolean containsMysqlKeyWord(String key) {
        // 处理字段为关键字的情况  isdelete -> isDeleteKey
        return KEY_MAP.containsKey(key.toUpperCase());
    }

    /**
     * 将关键字名称进行替换
     * 如果没有触发关键字，则返回原始数据
     *
     * @return
     */
    public static String containsKeyWordNameAndConcat(String keywordField, String concat) {
        if (!containsMysqlKeyWord(keywordField)) {
            return keywordField;
        }
        return keywordField + concat;
    }

}
