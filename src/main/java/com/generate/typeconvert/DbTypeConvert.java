package com.generate.typeconvert;

import java.util.Map;

/**
 * @author zxd
 * @version v1.0.0
 * @Package : com.generate.typeconvert
 * @Description : 数据库类型转化接口规范
 * @Create on : 2021/6/10 18:19
 **/
public interface DbTypeConvert {
    /**
     * 根据指定数据库获取对应的映射map
     *
     * @return
     */
    Map<String, String> getTypeMapping(String databaseType);
}
