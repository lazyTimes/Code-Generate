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

import static com.generate.config.SystemConfig.TEMPLATE_BASE_PACKAGE;

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
     * TODO 重构点2： 支持自定义选择生成那个模板
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
            genConfig();
            genFix();
        }
        logger.info(PropertiesConfig.getConfig().getProjectName() + " 构建完成.");
        // 执行自定义拦截接口 执行
        logger.info("=== 开始构建生成代码文件 ===");
        CustomEngineImpl.handleCustom();
    }

    /**
     * 处理模板参数内容
     *
     * @param classInfo    类信息
     * @param templateName 模板名称
     * @param filePath     文件路径
     */
    protected abstract void processTemplate(ClassInfo classInfo, String templateName, String filePath);
}
