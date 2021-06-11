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

}
