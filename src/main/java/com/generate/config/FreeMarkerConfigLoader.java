package com.generate.config;

import com.generate.engine.AbstractEngine;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

import java.util.Locale;

import static com.generate.config.SystemConfig.TEMPLATE_BASE_PACKAGE;

/**
 * @author zxd
 * @version v1.0.0
 * @Package : com.generate.config
 * @Description : freemarker配置加载器
 * @Create on : 2021/6/16 10:50
 **/
public class FreeMarkerConfigLoader {

    private static Configuration configuration;

    /***
     * 获取FreeMaker创建模板
     */
    public static Configuration loadFreeMarkerVersion2323() {
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
}
