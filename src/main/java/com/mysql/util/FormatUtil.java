package com.mysql.util;

/**
 * @author zhaoxudong
 * @version v1.0.0
 * @Package : com.mysql.util
 * @Description : 格式化工具
 * @Create on : 2021/6/9 16:32
 **/
public final class FormatUtil {

    /**
     * 对于请求参数进行拼接，如果无参数，返回空字符串
     * @return
     */
    public static String concat(String... param){
        if(param.length == 0){
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : param) {
            stringBuilder.append(s);
        }
        return stringBuilder.toString();
    }

}
