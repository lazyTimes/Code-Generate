package com.generate.typeconvert;

import java.util.Map;

/**
 * @author zhaoxudong
 * @version v1.0.0
 * @Package : com.generate.typeconvert
 * @Description : 类型转化数据工厂
 * @Create on : 2021/6/10 18:24
 **/
public class TypeConvertFactory implements DbTypeConvert{

    private static final DbTypeConvert bean = new DbTypeConvertBean();

    @Override
    public Map<String, String> getTypeMapping(String databaseType) {
        return bean.getTypeMapping(databaseType);
    }
}
