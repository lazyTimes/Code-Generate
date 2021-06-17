package com.generate.bean;

import lombok.Data;

import java.util.List;

/**
 * @author zhaoxudong
 * @version v1.0.0
 * @Package : com.generate.bean
 * @Description : web参数需要的请求实体对象
 * @Create on : 2021/6/17 18:06
 **/
@Data
public class WebClassInfo {

    // TODO 20210618 处理模板的参数，根据传递的参数模板

    /***
     * 表名
     */
    private String tableName;

    /**
     * class类名
     */
    private String className;

    /**
     * class实体参数名 如: classInfo
     */
    private String modelName;

    /**
     * class注释
     */
    private String classComment;

    /***
     * 主键字段及值 (默认以持有字段的 index=0 为主键)
     */
    private FieldInfo key;

    /**
     * 持有字段
     */
    private List<FieldInfo> fieldList;


}
