package com.generate.config;

import java.io.File;

/**
 * @author zhaoxudong
 * @version v1.0.0
 * @Package : com.mysql.config
 * @Description : 系统配置类
 * @Create on : 2021/6/8 15:46
 **/
public final class SystemConfig {
    /**系统空格 */
    public static final String SPACER = File.separator;
    /** freemarker模板保存地址 */
    public static final String TEMPLATE_BASE_PACKAGE = "/templates/";
    /**系统配置文件 */
    public static final String APPLICATION_PROPERTIES_FILE = "application.properties";
    /** 生成具体文件的JAVA文件后缀名 */
    public static final String GENERATE_JAVA_FILE_SUFFIX = ".java";
    /** 模板文件存放地址的通用前缀 */
    public static final String CODE_GENERATE_FILE_PREFIX = "code-generator";
    /** layui 文件地址前缀 */
    public static final String LAYUI_HTML_FILE_PREFIX = "layui-html";
     /** markdown 文件地址前缀 */
    public static final String MARK_DOWN_FILE_PREFIX = "markdown-file";
     /** 后端文件模板 文件地址前缀 */
    public static final String BACK_FILE_PREFIX = "mybatis";
    /** 其他项目必须文件 文件地址前缀 */
    public static final String COMMON_FILE_PREFIX = "common";
    /**  JAVA地址 */
    public static final String SRC_MAIN_JAVA = SPACER + "src" + SPACER + "main" + SPACER + "java" + SPACER;
     /**  RESOURCE地址 */
    public static final String SRC_MAIN_RESOURCE = SPACER + "src" + SPACER + "main" + SPACER + "resources" + SPACER;

    /**
     * freemarker 模板文件名称配置
     */
    public static class FreeMarkerFtlFileConfig {
        // === 后端配置 ===
        public static final String CONTROLLER = "controller.ftl";
        public static final String MODEL = "model.ftl";
        public static final String SERVICE = "service.ftl";
        public static final String SERVICE_IMPL = "service_impl.ftl";
        public static final String MAPPER = "mapper.ftl";
        public static final String MAPPER_IMPL = "mapper_xml.ftl";
        // === ------- ===

        // === markdown ===
        public static final String MARK_DOWN = "DataMd.ftl";
        // === ------- ===

        // === 前端配置 ===
        public static final String API_RESULT = "ApiResult.ftl";
        public static final String APPLICATION = "Application.ftl";
        public static final String APPLICATION_CONFIG = "applicationConfig.ftl";
        public static final String LOGBACK_SPRING = "logback-spring.ftl";
        public static final String PAGE_LIST = "PageList.ftl";
        public static final String POM = "pom.ftl";
        public static final String RESULT_CODE = "ResultCode.ftl";
        // === ------- ===


    }
}
