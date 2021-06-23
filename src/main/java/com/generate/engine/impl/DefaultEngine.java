package com.generate.engine.impl;

import cn.hutool.core.map.MapUtil;
import com.generate.bean.ClassInfo;
import com.generate.bean.ConfigurationInfo;
import com.generate.engine.AbstractEngine;
import com.generate.enums.GenMouduleEnum;
import com.generate.util.StringUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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

    private final ConfigurationInfo config;

    private final Configuration configuration;

    public DefaultEngine(ConfigurationInfo config) {
        this.config = Objects.requireNonNull(config);
        configuration = AbstractEngine.getFreeMakerConfiguration();
    }

    /***
     * 生成Class文件
     * @param classInfo      ClassInfo
     * @param templateName   模板地址
     * @param classSuffix    文件后缀
     */
    void genClass(ClassInfo classInfo, String templateName, String parentPackage, String classSuffix) {
        // 构建文件地址
        String path = config.getPackageName().replace(".", SPACER);
        // Example: F:\code\Demo\src\main\java\com\demo\controller\ScriptDirController.java
        String filePath = concat(config.getProjectPath(), SRC_MAIN_JAVA, path, SPACER
                , parentPackage.replace(".", SPACER), SPACER, classInfo.getClassName(), classSuffix);

        processTemplate(classInfo, templateName, filePath);
    }


    /***
     * 处理模板的参数固定方法，默认使用此配置，子类可以重写此部分内容
     * @param classInfo
     * @param templateName
     * @param filePath
     */
    @Override
    protected void processTemplate(ClassInfo classInfo, String templateName, String filePath) {
        logger.info("生成文件地址:{}", filePath);
        try {
            File file = new File(filePath);
            file.getParentFile().mkdirs();
            Writer writer = new FileWriter(new File(filePath));
            // 获取生成的模板文件名称
            Template template = getTemplate(templateName);
            template.process(genTemplateParam(classInfo), writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            logger.error("文件读写异常，异常原因为:{}", e.getLocalizedMessage());
            e.printStackTrace();
        } catch (TemplateException e) {
            logger.error("没有找到对应的模板文件，异常原因为:{}", e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    private Template getTemplate(String templateName) throws IOException {
        return configuration.getTemplate(templateName);
    }

    @Override
    protected Map<String, Object> genTemplateParam(ClassInfo classInfo) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("classInfo", classInfo);
        params.put("authorName", config.getAuthorName());
        params.put("packageName", config.getPackageName());
        params.put("projectName", config.getProjectName());
        params.put("genConfig", config);
        return params;
    }

    @Override
    public void genFix(ClassInfo classInfo) {
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
        String controller = MapUtil.getStr(classInfo.getTemplate(), GenMouduleEnum.CONTROLLER.getKey());
        genClass(classInfo, concat(CODE_GENERATE_FILE_PREFIX, SPACER, BACK_FILE_PREFIX, SPACER, getTemplatePath(controller, CONTROLLER)), CONTROLLER_FILE_PREFIX, concat("Controller", GENERATE_JAVA_FILE_SUFFIX));
    }

    @Override
    public void genService(ClassInfo classInfo) {
        String service = MapUtil.getStr(classInfo.getTemplate(), GenMouduleEnum.SERVICE.getKey());
        String serviceImpl = MapUtil.getStr(classInfo.getTemplate(), GenMouduleEnum.SERVICEIMPL.getKey());
        genClass(classInfo, concat(CODE_GENERATE_FILE_PREFIX, SPACER, BACK_FILE_PREFIX, SPACER, getTemplatePath(service, SERVICE)), SERVICE_PARENT_FOLDER, concat("Service", GENERATE_JAVA_FILE_SUFFIX));
        genClass(classInfo, concat(CODE_GENERATE_FILE_PREFIX, SPACER, BACK_FILE_PREFIX, SPACER, getTemplatePath(serviceImpl, SERVICE_IMPL)), SERVICE_IMPL_PARENT_FOLDER, concat("ServiceImpl", GENERATE_JAVA_FILE_SUFFIX));
    }

    @Override
    public void genRepositoryClass(ClassInfo classInfo) {
        String responsitory = MapUtil.getStr(classInfo.getTemplate(), GenMouduleEnum.MAPPER.getKey());
        genClass(classInfo, concat(CODE_GENERATE_FILE_PREFIX, SPACER, BACK_FILE_PREFIX, SPACER, getTemplatePath(responsitory, MAPPER)), MAPPER_PARENT_FOLDER, concat("Mapper", GENERATE_JAVA_FILE_SUFFIX));
    }

    @Override
    public void genRepositoryXml(ClassInfo classInfo) {
        String responsitoryXml = MapUtil.getStr(classInfo.getTemplate(), GenMouduleEnum.MAPPERXML.getKey());
        String filePath = concat(getRootPath(), SRC_MAIN_RESOURCE, SPACER, MAPPER_PARENT_FOLDER, SPACER
                , classInfo.getClassName(), MAPPER_XML_SUFFIX);
        processTemplate(classInfo, concat(CODE_GENERATE_FILE_PREFIX, SPACER, BACK_FILE_PREFIX, SPACER, getTemplatePath(responsitoryXml, MAPPER_IMPL)), filePath);
    }

    @Override
    public void genEntity(ClassInfo classInfo) {
        String entity = MapUtil.getStr(classInfo.getTemplate(), GenMouduleEnum.ENTITY.getKey());
        genClass(classInfo, concat(CODE_GENERATE_FILE_PREFIX, SPACER, BACK_FILE_PREFIX, SPACER, getTemplatePath(entity, MODEL)), ENTITY_FILE_PREFIX, GENERATE_JAVA_FILE_SUFFIX);
    }

    /**
     * 获取模板路径，如果存在参数，则根据参数获取，如果不存在，则根据系统默认配置进行生成
     *
     * @param config
     * @param defualt
     * @return
     */
    private String getTemplatePath(String config, String defualt) {
        return StringUtils.isNotBlank(config) ? config : defualt;
    }

    @Override
    public void genConfig(ClassInfo classInfo) {
        // 构建文件地址
        String rootPath = getRootPath();
        // POM依赖
        ClassInfo pom = new ClassInfo();
        pom.setClassName("pom");
        processTemplate(pom, concat(CODE_GENERATE_FILE_PREFIX, SPACER, COMMON_FILE_PREFIX, SPACER, POM), concat(rootPath, SPACER, pom.getClassName(), GENERATE_XML_FILE_SUFFIX));
        // logback日志
        ClassInfo log = new ClassInfo();
        log.setClassName("logback-spring");
        processTemplate(log, concat(CODE_GENERATE_FILE_PREFIX, SPACER, COMMON_FILE_PREFIX, SPACER, LOGBACK_SPRING), concat(rootPath, SRC_MAIN_RESOURCE, log.getClassName(), GENERATE_XML_FILE_SUFFIX));
        // 配置文件
        ClassInfo properties = new ClassInfo();
        properties.setClassName("application");
        processTemplate(properties, concat(CODE_GENERATE_FILE_PREFIX, SPACER, COMMON_FILE_PREFIX, SPACER, APPLICATION_CONFIG), concat(rootPath, SRC_MAIN_RESOURCE, properties.getClassName(), GENERATE_PROPRETIES_FILE_SUFFIX));
    }

    @Override
    public void genVue(ClassInfo classInfo) {
        throw new UnsupportedOperationException("不支持生成当前模板");
    }

    private String getRootPath() {
        return concat(config.getRootPath(), SPACER, config.getProjectName());
    }
}
