package com.mysql.engine.impl;

import com.mysql.bean.ClassInfo;
import com.mysql.bean.ConfigurationInfo;
import com.mysql.bean.GlobleConfig;
import com.mysql.config.SystemConfig;
import com.mysql.engine.AbstractEngine;
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

import static com.mysql.config.SystemConfig.*;
import static com.mysql.config.SystemConfig.FreeMarkerFtlFileConfig.*;
import static com.mysql.util.FormatUtil.concat;

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
        String filePath = config.getProjectPath() + SRC_MAIN_JAVA + path + SPACER
                + parentPackage.replace(".", SPACER) + SPACER + classInfo.getClassName() + classSuffix;
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
        genClass(apiResult, concat(CODE_GENERATE_FILE_PREFIX, SPACER, OTHER_FILE_PREFIX, SPACER, API_RESULT), OTHER_FILE_PREFIX, GENERATE_JAVA_FILE_SUFFIX);
        ClassInfo pageList = new ClassInfo();
        pageList.setClassName("PageList");
        genClass(pageList, concat(CODE_GENERATE_FILE_PREFIX, SPACER, OTHER_FILE_PREFIX, SPACER, PAGE_LIST), OTHER_FILE_PREFIX, GENERATE_JAVA_FILE_SUFFIX);
        ClassInfo resultCode = new ClassInfo();
        resultCode.setClassName("ResultCode");
        genClass(resultCode, concat(CODE_GENERATE_FILE_PREFIX, SPACER, OTHER_FILE_PREFIX, SPACER, RESULT_CODE), OTHER_FILE_PREFIX, GENERATE_JAVA_FILE_SUFFIX);
        // Application.class
        ClassInfo application = new ClassInfo();
        application.setClassName("Application");
        genClass(application, concat(CODE_GENERATE_FILE_PREFIX, SPACER, OTHER_FILE_PREFIX, SPACER, APPLICATION), "", GENERATE_JAVA_FILE_SUFFIX);
    }

    @Override
    public void genController(ClassInfo classInfo) {
        genClass(classInfo, "code-generator/mybatis/controller.ftl", "web", "Controller.java");
    }

    @Override
    public void genService(ClassInfo classInfo) {
        genClass(classInfo, "code-generator/mybatis/service.ftl", "service", "Service.java");
        genClass(classInfo, "code-generator/mybatis/service_impl.ftl", "service.impl", "ServiceImpl.java");
    }

    @Override
    public void genRepositoryClass(ClassInfo classInfo) {
        genClass(classInfo, "code-generator/mybatis/mapper.ftl", "dao", "Dao.java");
    }

    @Override
    public void genRepositoryXml(ClassInfo classInfo) {
        // 构建文件地址
        String rootPath = config.getRootPath() + File.separator + config.getProjectName();
        // Example: C:\Users\Administrator\Desktop\Codes\KerwinBoots\src\main\resources\mapper\ScriptDirMapper.xml
        String filePath = rootPath + SRC_MAIN_RESOURCE + SPACER + "mapper" + SPACER
                + classInfo.getClassName() + "Mapper.xml";
        processTemplate(classInfo, "code-generator/mybatis/mapper_xml.ftl", filePath);
    }

    @Override
    public void genEntity(ClassInfo classInfo) {
        genClass(classInfo, "code-generator/mybatis/model.ftl", "entity", GENERATE_JAVA_FILE_SUFFIX);
    }

    @Override
    public void genConfig() {
        // 构建文件地址
        String rootPath = config.getRootPath() + File.separator + config.getProjectName();
        // POM依赖
        ClassInfo pom = new ClassInfo();
        pom.setClassName("pom");
        processTemplate(pom, "code-generator/common/pom.ftl", rootPath + SPACER + pom.getClassName() + ".xml");
        // logback日志
        ClassInfo log = new ClassInfo();
        log.setClassName("logback-spring");
        processTemplate(log, "code-generator/common/logback-spring.ftl", rootPath + SRC_MAIN_RESOURCE + log.getClassName() + ".xml");
        // 配置文件
        ClassInfo properties = new ClassInfo();
        properties.setClassName("application");
        processTemplate(properties, "code-generator/common/applicationConfig.ftl", rootPath + SRC_MAIN_RESOURCE + properties.getClassName() + ".properties");
    }


}
