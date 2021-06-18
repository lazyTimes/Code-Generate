package com.generate.engine.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.generate.bean.ClassInfo;
import com.generate.bean.GenerateConfigConvert;
import com.generate.bean.PropertiesConfig;
import com.generate.config.FreeMarkerConfigLoader;
import com.generate.engine.AbstractEngine;
import com.generate.enums.GenMouduleEnum;
import com.generate.factory.ClassInfoFactory;
import com.generate.model.WebEngineConfig;
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
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.generate.config.SystemConfig.*;
import static com.generate.config.SystemConfig.FreeMarkerFtlFileConfig.*;
import static com.generate.config.SystemConfig.FreeMarkerFtlFileConfig.APPLICATION_CONFIG;
import static com.generate.util.FormatUtil.concat;

/**
 * @author zhaoxudong
 * @version v1.0.0
 * @Package : com.generate.engine.impl
 * @Description : 设置为web获取的形式
 * @Create on : 2021/6/16 10:06
 **/
public class WebEngine extends AbstractEngine {

    private static Logger logger = LoggerFactory.getLogger(WebEngine.class);

    private final WebEngineConfig webEngineConfig;

    private final Configuration configuration;

    private final DefaultEngine defaultEngine;

    public WebEngine(WebEngineConfig webEngineConfig) {
        this.webEngineConfig = Objects.requireNonNull(webEngineConfig);
        this.configuration = FreeMarkerConfigLoader.loadFreeMarkerVersion2323();
        defaultEngine = new DefaultEngine(GenerateConfigConvert.convertConfigInfo(webEngineConfig));
    }

    /***
     * 生成Class文件
     * @param classInfo      ClassInfo
     * @param templateName   模板地址
     * @param classSuffix    文件后缀
     */
    private void genClass(ClassInfo classInfo, String templateName, String parentPackage, String classSuffix) {
        // 构建文件地址
        String path = webEngineConfig.getPackageName().replace(".", SPACER);
        // Example: F:\code\Demo\src\main\java\com\demo\controller\ScriptDirController.java
        String filePath = concat(webEngineConfig.getRootPath(), SRC_MAIN_JAVA, path, SPACER
                , parentPackage.replace(".", SPACER), SPACER, classInfo.getClassName(), classSuffix);
        logger.info("文件地址:{}", filePath);
        processTemplate(classInfo, templateName, filePath);
    }

    /***
     * FreeMarker 模板固定方法
     * @param classInfo 类信息
     * @param templateName 模板名称
     * @param filePath 文件路径
     */
    @Override
    protected void processTemplate(ClassInfo classInfo, String templateName, String filePath) {
        defaultEngine.processTemplate(classInfo, templateName, filePath);
    }

    /**
     * 构建ftl模板所需要的参数。如果需要网模板添加参数请通过此方法实现
     *
     * @param classInfo
     * @return
     */
    @Override
    protected Map<String, Object> genTemplateParam(ClassInfo classInfo) {
        Map<String, Object> result = defaultEngine.genTemplateParam(classInfo);
        // 生成配置覆盖为自定义对象配置
        result.put("genConfig", webEngineConfig);
        return result;
    }



    @Override
    public void genFix() {
        defaultEngine.genFix();
    }

    @Override
    public void genController(ClassInfo classInfo) {
        defaultEngine.genController(classInfo);
    }

    @Override
    public void genService(ClassInfo classInfo) {
        defaultEngine.genService(classInfo);
    }

    @Override
    public void genRepositoryClass(ClassInfo classInfo) {
        defaultEngine.genRepositoryClass(classInfo);
    }

    @Override
    public void genRepositoryXml(ClassInfo classInfo) {
        // 构建文件地址
        String rootPath = getRootPath();
        // Example: C:\Users\Administrator\Desktop\Codes\KerwinBoots\src\main\resources\mapper\ScriptDirMapper.xml
        String filePath = rootPath + SRC_MAIN_RESOURCE + SPACER + MAPPER_PARENT_FOLDER + SPACER
                + classInfo.getClassName() + MAPPER_XML_SUFFIX;
        processTemplate(classInfo, concat(CODE_GENERATE_FILE_PREFIX, SPACER, BACK_FILE_PREFIX, SPACER, MAPPER_IMPL), filePath);
    }

    @Override
    public void genEntity(ClassInfo classInfo) {
        defaultEngine.genEntity(classInfo);
    }

    @Override
    public void genConfig() {
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

    private String getRootPath() {
        return concat(webEngineConfig.getRootPath(), SPACER, webEngineConfig.getProjectName());
    }

    @Override
    public void execute() {
        List<String> matters = webEngineConfig.getMatters();
        if (checkNotAccessMatters(matters)){
            return;
        }
        // 数组内容通过;号拼接合并
        String collect = String.join(";", matters);
        List<ClassInfo> classInfos = ClassInfoFactory.getClassInfoList(PropertiesConfig.getConfig().getDataBaseType(), webEngineConfig);
        // 根据不同的选项要有校验操作 根据枚举：GenMouduleEnum
        for (ClassInfo classInfo : classInfos) {
            if(collect.contains(GenMouduleEnum.CONTROLLER.getKey())){
                logger.info("=== 开始生成{}模块 ===", GenMouduleEnum.CONTROLLER.getKey());
                genController(classInfo);
            }
            if(collect.contains(GenMouduleEnum.ENTITY.getKey())){
                logger.info("=== 开始生成{}模块 ===", GenMouduleEnum.ENTITY.getKey());
                genEntity(classInfo);
            }
            if(collect.contains(GenMouduleEnum.MAPPER.getKey())){
                logger.info("=== 开始生成{}模块 ===", GenMouduleEnum.MAPPER.getKey());
                genRepositoryClass(classInfo);
            }
            if(collect.contains(GenMouduleEnum.SERVICE.getKey())){
                logger.info("=== 开始生成{}模块 ===", GenMouduleEnum.SERVICE.getKey());
                genService(classInfo);
            }
            if(collect.contains(GenMouduleEnum.MAPPERXML.getKey())){
                logger.info("=== 开始生成{}模块 ===", GenMouduleEnum.MAPPERXML.getKey());
                genRepositoryXml(classInfo);
            }
            if(collect.contains(GenMouduleEnum.CONFIG.getKey())){
                logger.info("=== 开始生成{}模块 ===", GenMouduleEnum.CONFIG.getKey());
                genConfig();
            }
            if(collect.contains(GenMouduleEnum.FIX.getKey())){
                logger.info("=== 开始生成{}模块 ===", GenMouduleEnum.FIX.getKey());
                genFix();
            }
        }
        logger.info(PropertiesConfig.getConfig().getProjectName() + " 构建完成.");
        // 执行自定义拦截接口 执行
        logger.info("=== 开始构建生成代码文件 ===");
        CustomEngineImpl.handleCustom();
        logger.info("=== 构建生成代码文件完成 ===");
    }

    /**
     * 检查当前请求是否存在对应生成模块参数，如果不存在，日志提示
     * @param matters
     * @return
     */
    private boolean checkNotAccessMatters(List<String> matters) {
        if(CollectionUtil.isEmpty(matters)){
            StringBuilder stringBuilder = new StringBuilder();
            for (GenMouduleEnum value : GenMouduleEnum.values()) {
                stringBuilder.append(value.getKey()).append(";");
            }
            logger.warn("当前未指定生成任何模块，未生成任何代码，请指定matters[]字段，可选配置为{}", stringBuilder.toString());
            return true;
        }
        return false;
    }
}
