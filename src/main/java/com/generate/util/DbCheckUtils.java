package com.generate.util;

import com.generate.bean.PropertiesConfig;
import com.generate.enums.DbEnum;
import org.apache.commons.lang3.StringUtils;

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

    private final static Map<String, String> KEY_MAP = new HashMap<>(256);

    static {
        DbCheckUtils.parseKeyWords();
    }

    /***
     * 解析MySQL 关键字
     */
    private static void parseKeyWords() {
        String dataBaseType = PropertiesConfig.getConfig().getDataBaseType();
        DbEnum enumOrNullByName = DbEnum.getEnumOrNullByName(dataBaseType);
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

    /**
     * 下划线转为驼峰式的的同时，将根据指定数据库
     * @param fieldName
     * @return
     */
    public static String underlineToCamelCaseAndReplaceDbFieldName( String fieldName) {
        return containsKeyWordNameAndConcat(underlineToCamelCase(fieldName), "Key");
    }

    /**
     * 下划线，转换为驼峰式
     *
     * @param fieldName
     * @return
     */
    public static String underlineToCamelCase(String fieldName) {
        if (StringUtils.isBlank(fieldName)) {
            return fieldName;
        }
        StringBuilder result = new StringBuilder();
        boolean flag = false;
        for (int i = 0; i < fieldName.length(); i++) {
            char ch = fieldName.charAt(i);
            if ("_".charAt(0) == ch) {
                flag = true;
            } else {
                if (flag) {
                    result.append(Character.toUpperCase(ch));
                    flag = false;
                } else {
                    result.append(ch);
                }
            }
        }
        // 返回值
        return result.toString();
    }
}
