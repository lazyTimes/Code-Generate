package com.generate.engine;

import com.generate.bean.ClassInfo;
import com.generate.bean.PropertiesConfig;
import com.generate.config.FreeMarkerConfigLoader;
import com.generate.engine.impl.CustomEngineImpl;
import com.generate.engine.impl.DefaultEngine;
import com.generate.factory.ClassInfoFactory;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.generate.config.SystemConfig.*;
import static com.generate.config.SystemConfig.SPACER;
import static com.generate.util.FormatUtil.concat;

/**
 * ******************************
 * author：      柯贤铭
 * createTime:   2020/1/10 9:20
 * description:  AbstractEngine 抽象
 * version:      V1.0
 * ******************************
 */
public abstract class AbstractEngine implements GeneralEngine {

    private static final Logger logger = LoggerFactory.getLogger(AbstractEngine.class);

    /***
     * 获取FreeMaker创建模板
     */
    public static Configuration getFreeMakerConfiguration() {
        return FreeMarkerConfigLoader.loadFreeMarkerVersion2323();
    }


    /**
     * 默认执行方法
     */
    @Override
    public void execute() {
        // 默认获取properties中的配置
        List<ClassInfo> classInfos = ClassInfoFactory.getClassInfoList(PropertiesConfig.getConfig().getDataBaseType(), PropertiesConfig.getConfig());
        for (ClassInfo classInfo : classInfos) {
            genController(classInfo);
            genEntity(classInfo);
            genRepositoryClass(classInfo);
            genService(classInfo);
            genRepositoryXml(classInfo);
            genConfig(classInfo);
            genFix(classInfo);
        }
        // 执行自定义拦截接口 执行
        logger.info("=== 开始构建自定义组件内容 ===");
        CustomEngineImpl.handleCustom();
        logger.info("=== 构建自定义组件完成 ===");
        logger.info(PropertiesConfig.getConfig().getProjectName() + " 构建完成.");
        logger.info("代码构建完成");
    }




    /**
     * 统一模板处理方法
     *
     * @param classInfo    类信息
     * @param templateName 模板名称
     * @param filePath     文件路径
     */
    protected abstract void processTemplate(ClassInfo classInfo, String templateName, String filePath);

    /**
     * 处理模板参数的方法
     * @param classInfo
     * @return
     */
    protected abstract Map<String, Object> genTemplateParam(ClassInfo classInfo);
}
