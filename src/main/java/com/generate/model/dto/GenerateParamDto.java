package com.generate.model.dto;

import java.util.List;

/**
 * @author zhaoxudong
 * @version v1.0.0
 * @Package : com.generate.model.dto
 * @Description : 生成sql参数配置对象
 * @Create on : 2021/6/15 12:01
 **/
public class GenerateParamDto {

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
    private List<GenerateTableParamDto> include;

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

    private GenerateParamDto(Builder builder) {
        this.dataBaseType = builder.dataBaseType;
        this.database = builder.database;
        this.ip = builder.ip;
        this.include = builder.include;
        this.projectName = builder.projectName;
        this.packageName = builder.packageName;
        this.authorName = builder.authorName;
        this.rootPath = builder.rootPath;
        this.customHandleInclude = builder.customHandleInclude;
        this.driver = builder.driver;
        this.port = builder.port;
    }

    public static class Builder{
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
        private List<GenerateTableParamDto> include;

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

        public Builder dataBaseType(String dataBaseType) {
            this.dataBaseType = dataBaseType;
            return this;
        }

        public Builder database(String database) {
            this.database = database;
            return this;
        }

        public Builder ip(String ip) {
            this.ip = ip;
            return this;
        }

        public Builder include(List<GenerateTableParamDto> include) {
            this.include = include;
            return this;
        }

        public Builder projectName(String projectName) {
            this.projectName = projectName;
            return this;
        }

        public Builder packageName(String packageName) {
            this.packageName = packageName;
            return this;
        }

        public Builder authorName(String authorName) {
            this.authorName = authorName;
            return this;
        }

        public Builder rootPath(String rootPath) {
            this.rootPath = rootPath;
            return this;
        }

        public Builder customHandleInclude(String customHandleInclude) {
            this.customHandleInclude = customHandleInclude;
            return this;
        }

        public Builder driver(String driver) {
            this.driver = driver;
            return this;
        }

        public Builder port(String port) {
            this.port = port;
            return this;
        }

        public GenerateParamDto build(){
            return new GenerateParamDto(this);
        }
    }

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

    public List<GenerateTableParamDto> getInclude() {
        return include;
    }

    public void setInclude(List<GenerateTableParamDto> include) {
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
}
