package com.generate.util;

import com.generate.enums.DbEnum;
import org.apache.commons.lang3.StringUtils;


/**
 * 字符串操作工具类
 * */
public class StringUtil {



    /**
     * 首字母大写
     * 20210610 更具备效率的写法
     * @param str
     * @return
     */
    public static String upperCaseFirst(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }
        return new String(ch);
    }

    /**
     * 首字母小写
     *
     * @param str
     * @return
     */
    public static String lowerCaseFirst(String str) {
        return (str != null && str.length() > 1) ? str.substring(0, 1).toLowerCase() + str.substring(1) : "";
    }

    /**
     * 下划线转为驼峰式的的同时，将根据指定数据库
     * @param fieldName
     * @return
     */
    public static String underlineToCamelCaseAndReplaceDbFieldName( String fieldName) {
        return DbCheckUtils.containsKeyWordNameAndConcat(underlineToCamelCase(fieldName), "Key");
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
        String key = result.toString();
        if (DbCheckUtils.containsMysqlKeyWord(key)){
            return key + "Key";
        }
        return key;
    }


}
