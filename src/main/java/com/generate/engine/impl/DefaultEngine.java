package com.generate.engine.impl;

import com.generate.bean.ClassInfo;
import com.generate.bean.ConfigurationInfo;
import com.generate.bean.GlobleConfig;
import com.generate.engine.AbstractEngine;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import static com.generate.config.SystemConfig.*;
import static com.generate.config.SystemConfig.FreeMarkerFtlFileConfig.*;
import static com.generate.util.FormatUtil.concat;

/**
 * ******************************
 * author：      柯贤铭
 * createTime:   2020/1/10 9:24
 * description:  默认实现类
 * version:      V1.0
 * ******************************
 */
public final class DefaultEngine extends AbstractEngine {

    private static Logger logger = LoggerFactory.getLogger(DefaultEngine.class);

    private ConfigurationInfo config;

    private Configuration configuration;

    public DefaultEngine() {
        config = GlobleConfig.getGlobleConfig();
        configuration = AbstractEngine.getFreeMakerConfiguration();
    }

    /***
     * 生成Class文件
     * @param classInfo      ClassInfo
     * @param templateName   模板地址
     * @param classSuffix    文件后缀
     */
    private void genClass(ClassInfo classInfo, String templateName, String parentPackage, String classSuffix) {
        // 构建文件地址
        String path = config.getPackageName().replace(".", SPACER);
        // Example: F:\code\Demo\src\main\java\com\demo\controller\ScriptDirController.java
        String filePath = concat(config.getProjectPath(), SRC_MAIN_JAVA, path, SPACER
                , parentPackage.replace(".", SPACER), SPACER, classInfo.getClassName(), classSuffix);
        logger.info("文件地址:{}", filePath);
        processTemplate(classInfo, templateName, filePath);
    }

    /***
     * FreeMarker 模板固定方法
     * @param classInfo
     * @param templateName
     * @param filePath
     */
    private void processTemplate(ClassInfo classInfo, String templateName, String filePath) {
        try {
            File file = new File(filePath);
            file.getParentFile().mkdirs();
            Writer writer = new FileWriter(new File(filePath));
            Template template = configuration.getTemplate(templateName);
            Map<String, Object> params = new HashMap<>(16);
            params.put("classInfo", classInfo);
            params.put("authorName", config.getAuthorName());
            params.put("packageName", config.getPackageName());
            params.put("projectName", config.getProjectName());
            params.put("genConfig", config);
            template.process(params, writer);
            writer.flush();
            writer.close();
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void genFix() {
        // 生成固定文件 ApiResult,PageList,ResultCode
        ClassInfo apiResult = new ClassInfo();
        apiResult.setClassName("ApiResult");
        genClass(apiResult, concat(CODE_GENERATE_FILE_PREFIX, SPACER, COMMON_FILE_PREFIX, SPACER, API_RESULT), COMMON_FILE_PREFIX, GENERATE_JAVA_FILE_SUFFIX);
        ClassInfo pageList = new ClassInfo();
        pageList.setClassName("PageList");
        genClass(pageList, concat(CODE_GENERATE_FILE_PREFIX, SPACER, COMMON_FILE_PREFIX, SPACER, PAGE_LIST), COMMON_FILE_PREFIX, GENERATE_JAVA_FILE_SUFFIX);
        ClassInfo resultCode = new ClassInfo();
        resultCode.setClassName("ResultCode");
        genClass(resultCode, concat(CODE_GENERATE_FILE_PREFIX, SPACER, COMMON_FILE_PREFIX, SPACER, RESULT_CODE), COMMON_FILE_PREFIX, GENERATE_JAVA_FILE_SUFFIX);
        // Application.class
        ClassInfo application = new ClassInfo();
        application.setClassName("Application");
        genClass(application, concat(CODE_GENERATE_FILE_PREFIX, SPACER, COMMON_FILE_PREFIX, SPACER, APPLICATION), "", GENERATE_JAVA_FILE_SUFFIX);
    }

    @Override
    public void genController(ClassInfo classInfo) {
        genClass(classInfo, concat(CODE_GENERATE_FILE_PREFIX, SPACER, BACK_FILE_PREFIX, SPACER, CONTROLLER), "controller", concat("Controller", GENERATE_JAVA_FILE_SUFFIX));
    }

    @Override
    public void genService(ClassInfo classInfo) {
        genClass(classInfo, concat(CODE_GENERATE_FILE_PREFIX, SPACER, BACK_FILE_PREFIX, SPACER, SERVICE), "service", concat("Service", GENERATE_JAVA_FILE_SUFFIX));
        genClass(classInfo, concat(CODE_GENERATE_FILE_PREFIX, SPACER, BACK_FILE_PREFIX, SPACER, SERVICE_IMPL), "service.impl", concat("ServiceImpl", GENERATE_JAVA_FILE_SUFFIX));
    }

    @Override
    public void genRepositoryClass(ClassInfo classInfo) {
        genClass(classInfo, concat(CODE_GENERATE_FILE_PREFIX, SPACER, BACK_FILE_PREFIX, SPACER, MAPPER), "mapper", concat("Mapper", GENERATE_JAVA_FILE_SUFFIX));
    }

    @Override
    public void genRepositoryXml(ClassInfo classInfo) {
        // 构建文件地址
        String rootPath = config.getRootPath() + SPACER + config.getProjectName();
        // Example: C:\Users\Administrator\Desktop\Codes\KerwinBoots\src\main\resources\mapper\ScriptDirMapper.xml
        String filePath = rootPath + SRC_MAIN_RESOURCE + SPACER + "mapper" + SPACER
                + classInfo.getClassName() + "Mapper.xml";
        processTemplate(classInfo, concat(CODE_GENERATE_FILE_PREFIX, SPACER, BACK_FILE_PREFIX, SPACER, MAPPER_IMPL), filePath);
    }

    @Override
    public void genEntity(ClassInfo classInfo) {
        genClass(classInfo, concat(CODE_GENERATE_FILE_PREFIX, SPACER, BACK_FILE_PREFIX, SPACER, MODEL), "entity", GENERATE_JAVA_FILE_SUFFIX);
    }

    @Override
    public void genConfig() {
        // 构建文件地址
        String rootPath = concat(config.getRootPath(), SPACER, config.getProjectName());
        // POM依赖
        ClassInfo pom = new ClassInfo();
        pom.setClassName("pom");
        processTemplate(pom, concat(CODE_GENERATE_FILE_PREFIX, SPACER, COMMON_FILE_PREFIX, SPACER, POM), concat(rootPath, SPACER, pom.getClassName(), ".xml"));
        // logback日志
        ClassInfo log = new ClassInfo();
        log.setClassName("logback-spring");
        processTemplate(log, concat(CODE_GENERATE_FILE_PREFIX, SPACER, COMMON_FILE_PREFIX, SPACER, LOGBACK_SPRING), concat(rootPath, SRC_MAIN_RESOURCE, log.getClassName(), ".xml"));
        // 配置文件
        ClassInfo properties = new ClassInfo();
        properties.setClassName("application");
        processTemplate(properties, concat(CODE_GENERATE_FILE_PREFIX, SPACER, COMMON_FILE_PREFIX, SPACER, APPLICATION_CONFIG), concat(rootPath, SRC_MAIN_RESOURCE, properties.getClassName(), ".properties"));
    }


}
