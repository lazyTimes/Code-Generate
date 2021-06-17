package com.generate.model;

import java.util.List;
import java.util.Map;

/**
 * @author zhaoxudong
 * @version v1.0.0
 * @Package : com.generate.model
 * @Description : web配置实体对象
 * @Create on : 2021/6/16 10:08
 **/
public class WebEngineConfig {

    public static class WebGenerateParam {
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
     * Ip
     */
    private String ip;

    /**
     * 当前使用数据库
     */
    private String dataBaseType;

    /**
     * port
     */
    private String port;

    /**
     * driver
     */
    private String driver;

    /**
     * 数据库名
     */
    private String dataBase;

    /**
     * 编码
     */
    private String encoding;

    /**
     * 用户名
     */
    private String loginName;

    /**
     * 密码
     */
    private String passWord;

    /***
     * 需要处理的表名 以 ; 划分
     */
    private String include;

    /***
     * 需要处理的表名Map
     */
    private Map<String, String> includeMap;

    /***
     * 需要处理的自定义Handle名 以 ; 划分
     */
    private String customHandleInclude;

    /***
     * 需要处理的自定义Handle名
     */
    private Map<String, String> customHandleIncludeMap;

    /**
     * 项目名
     */
    private String projectName;

    /**
     * 包名
     */
    private String packageName;

    /**
     * 作者名
     */
    private String authorName;

    /**
     * 文件根目录
     */
    private String rootPath;

    /**
     * 项目根目录
     */
    private String projectPath;

    /**
     * 生成的具体参数，后续根据此内容变量进行处理
     */
    private List<WebGenerateParam> webGenerateParams;

    /**
     * 生成选项
     */
    private List<String> matters;

    public List<String> getMatters() {
        return matters;
    }

    public void setMatters(List<String> matters) {
        this.matters = matters;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDataBaseType() {
        return dataBaseType;
    }

    public void setDataBaseType(String dataBaseType) {
        this.dataBaseType = dataBaseType;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getDataBase() {
        return dataBase;
    }

    public void setDataBase(String dataBase) {
        this.dataBase = dataBase;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getInclude() {
        return include;
    }

    public void setInclude(String include) {
        this.include = include;
    }

    public Map<String, String> getIncludeMap() {
        return includeMap;
    }

    public void setIncludeMap(Map<String, String> includeMap) {
        this.includeMap = includeMap;
    }

    public String getCustomHandleInclude() {
        return customHandleInclude;
    }

    public void setCustomHandleInclude(String customHandleInclude) {
        this.customHandleInclude = customHandleInclude;
    }

    public Map<String, String> getCustomHandleIncludeMap() {
        return customHandleIncludeMap;
    }

    public void setCustomHandleIncludeMap(Map<String, String> customHandleIncludeMap) {
        this.customHandleIncludeMap = customHandleIncludeMap;
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

    public String getProjectPath() {
        return projectPath;
    }

    public void setProjectPath(String projectPath) {
        this.projectPath = projectPath;
    }

    public List<WebGenerateParam> getWebGenerateParams() {
        return webGenerateParams;
    }

    public void setWebGenerateParams(List<WebGenerateParam> webGenerateParams) {
        this.webGenerateParams = webGenerateParams;
    }

    @Override
    public String toString() {
        return "WebEngineConfig{" +
                "ip='" + ip + '\'' +
                ", dataBaseType='" + dataBaseType + '\'' +
                ", port='" + port + '\'' +
                ", driver='" + driver + '\'' +
                ", dataBase='" + dataBase + '\'' +
                ", encoding='" + encoding + '\'' +
                ", loginName='" + loginName + '\'' +
                ", passWord='" + passWord + '\'' +
                ", include='" + include + '\'' +
                ", includeMap=" + includeMap +
                ", customHandleInclude='" + customHandleInclude + '\'' +
                ", customHandleIncludeMap=" + customHandleIncludeMap +
                ", projectName='" + projectName + '\'' +
                ", packageName='" + packageName + '\'' +
                ", authorName='" + authorName + '\'' +
                ", rootPath='" + rootPath + '\'' +
                ", projectPath='" + projectPath + '\'' +
                ", webGenrateParams=" + webGenerateParams +
                '}';
    }
}
