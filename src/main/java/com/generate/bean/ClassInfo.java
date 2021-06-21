package com.generate.bean;

import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ******************************
 * author：      柯贤铭
 * createTime:   2020/1/9 17:08
 * description:  ClassInfo 表信息
 * version:      V1.0
 * ******************************
 */
@Data
public class ClassInfo{

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

    /**
     * 查询字段
     */
    private String queryFields;

    /**
     * 查询列，指定查询列将只生成查询列的相关信息
     * 否则按照表所有字段进行生成
     */
    private String fields;

    /**
     * 手动指定使用模板
     * 根据key value的配置获取所需的配置
     */
    private Map<String, String> template;
}