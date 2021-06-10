package com.generate.engine;

import com.generate.bean.ClassInfo;
import com.generate.bean.GlobleConfig;
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

    private static Configuration configuration;

    /***
     * 获取FreeMaker创建模板
     */
    public static Configuration getFreeMakerConfiguration() {
        if (configuration == null) {
            configuration = new Configuration(Configuration.VERSION_2_3_23);
            try {
                configuration.setTemplateLoader(new ClassTemplateLoader(AbstractEngine.class, TEMPLATE_BASE_PACKAGE));
                configuration.setNumberFormat("#");
                configuration.setClassicCompatible(true);
                configuration.setDefaultEncoding("UTF-8");
                configuration.setLocale(Locale.CHINA);
                configuration.setAPIBuiltinEnabled(true);
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
            configuration.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
        }
        return configuration;
    }

    /**
     * TODO 重构点2： 支持自定义选择生成那个模板
     */
    @Override
    public void execute() {
        List<ClassInfo> classInfos = ClassInfoFactory.getClassInfoList();
        for (ClassInfo classInfo : classInfos) {
            genController(classInfo);
            genEntity(classInfo);
            genRepositoryClass(classInfo);
            genService(classInfo);
            genRepositoryXml(classInfo);
            genConfig();
            genFix();
        }
        logger.info(GlobleConfig.getGlobleConfig().getProjectName() + " 构建完成.");
        // 执行自定义拦截接口 执行
        logger.info("=== 开始构建生成代码文件 ===");
        CustomEngineImpl.handleCustom();
    }

    /***
     * 初始化工程
     * 加载配置文件, 加载数据库连接, 加载数据表字段信息, 加载FreeMaker模板
     * 获得执行器
     */
    public static AbstractEngine init() {
        return new DefaultEngine();
    }

}
