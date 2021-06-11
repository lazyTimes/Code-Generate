package com.generate.strategy.sqlgen;

import com.generate.enums.DbEnum;

/**
 * @author zhaoxudong
 * @version v1.0.0
 * @Package : com.generate.strategy.sqlgen
 * @Description : 生成sql策略选择器
 * @Create on : 2021/6/11 10:26
 **/
public class GenerateContext {

    /**
     * 根据数据库选择获取对应的
     * @param dbType 数据库类型
     * @return
     */
    public DbProcessAble genGenerator(String dbType){
        DbEnum enumOrNullByName = DbEnum.getEnumOrNullByName(dbType);
        if (enumOrNullByName == DbEnum.POSTGRESQL) {
            return new PostgreSqlDbProcess();
        }
        return new MysqlDbProcess();
    }

}
