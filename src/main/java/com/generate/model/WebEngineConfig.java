package com.generate.model;

import com.generate.model.dto.GenerateTableParamDto;

import java.util.List;

/**
 * @author zhaoxudong
 * @version v1.0.0
 * @Package : com.generate.model
 * @Description : web配置实体对象
 * @Create on : 2021/6/16 10:08
 **/
public class WebEngineConfig {

    static class WebGenrateParam{
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

        @Override
        public String toString() {
            return "WebGenrateParam{" +
                    "fields='" + fields + '\'' +
                    ", tableName='" + tableName + '\'' +
                    ", template='" + template + '\'' +
                    ", queryField='" + queryField + '\'' +
                    '}';
        }
    }

    /**
     * 数据库类型
     */
    private String dataBaseType;

    /**
     * 数据库名称
     */
    private String database;

    /**
     * ip 地址
     */
    private String ip;

    /**
     * 包含具体的对象
     * 这里需要重新构建
     */
    private List<WebGenrateParam> include;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 包名称 .分割
     */
    private String packageName;

    /**
     * 作者名称
     */
    private String authorName;

    /**
     * 项目输出路径
     */
    private String rootPath;

    /**
     * 自定义模块
     */
    private String customHandleInclude;

    /**
     * 数据库驱动（一般固定由前台写死）
     */
    private String driver;

    /**
     * 端口号
     */
    private String port;

    public String getDataBaseType() {
        return dataBaseType;
    }

    public void setDataBaseType(String dataBaseType) {
        this.dataBaseType = dataBaseType;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public List<WebGenrateParam> getInclude() {
        return include;
    }

    public void setInclude(List<WebGenrateParam> include) {
        this.include = include;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    public String getCustomHandleInclude() {
        return customHandleInclude;
    }

    public void setCustomHandleInclude(String customHandleInclude) {
        this.customHandleInclude = customHandleInclude;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "WebEngineConfig{" +
                "dataBaseType='" + dataBaseType + '\'' +
                ", database='" + database + '\'' +
                ", ip='" + ip + '\'' +
                ", include=[]" + include +
                ", projectName='" + projectName + '\'' +
                ", packageName='" + packageName + '\'' +
                ", authorName='" + authorName + '\'' +
                ", rootPath='" + rootPath + '\'' +
                ", customHandleInclude='" + customHandleInclude + '\'' +
                ", driver='" + driver + '\'' +
                ", port='" + port + '\'' +
                '}';
    }
}
