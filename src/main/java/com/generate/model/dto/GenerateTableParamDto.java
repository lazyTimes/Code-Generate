package com.generate.model.dto;

/**
 * @author zhaoxudong
 * @version v1.0.0
 * @Package : com.generate.model.dto
 * @Description : 构建表的参数dto
 * @Create on : 2021/6/15 15:09
 **/
public class GenerateTableParamDto {

    /**
     * 字段用逗号分割
     */
    private String fields;

    /**
     * 表名称
     */
    private String tableName;

    /**
     * 使用模板名称
     */
    private String template;

    /**
     * 查询字段
     */
    private String queryField;


    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getQueryField() {
        return queryField;
    }

    public void setQueryField(String queryField) {
        this.queryField = queryField;
    }
}
