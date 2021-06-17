package com.generate.engine.impl;

import com.generate.bean.ClassInfo;
import com.generate.bean.GenerateConfigConvert;
import com.generate.bean.PropertiesConfig;
import com.generate.config.FreeMarkerConfigLoader;
import com.generate.engine.AbstractEngine;
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
     * @param classInfo
     * @param templateName
     * @param filePath
     */
    private void processTemplate(ClassInfo classInfo, String templateName, String filePath) {
        try {
            File file = new File(filePath);
            file.getParentFile().mkdirs();
            Writer writer = new FileWriter(new File(filePath));
            // TODO 获取生成的模板文件名称
            Template template = getTemplate(templateName);
            Map<String, Object> params = new HashMap<>(16);
            params.put("classInfo", classInfo);
            params.put("authorName", webEngineConfig.getAuthorName());
            params.put("packageName", webEngineConfig.getPackageName());
            params.put("projectName", webEngineConfig.getProjectName());
            params.put("genConfig", webEngineConfig);
            template.process(params, writer);
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
        defaultEngine.genRepositoryXml(classInfo);
    }

    @Override
    public void genEntity(ClassInfo classInfo) {
        defaultEngine.genEntity(classInfo);
    }

    @Override
    public void genConfig() {
        defaultEngine.genConfig();
    }

    @Override
    public void execute() {
        List<ClassInfo> classInfos = ClassInfoFactory.getClassInfoList(PropertiesConfig.getConfig().getDataBaseType());
        for (ClassInfo classInfo : classInfos) {
            genController(classInfo);
            genEntity(classInfo);
            genRepositoryClass(classInfo);
            genService(classInfo);
            genRepositoryXml(classInfo);
            genConfig();
            genFix();
        }
        logger.info(PropertiesConfig.getConfig().getProjectName() + " 构建完成.");
        // 执行自定义拦截接口 执行
        logger.info("=== 开始构建生成代码文件 ===");
        CustomEngineImpl.handleCustom();
    }
}
