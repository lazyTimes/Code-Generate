package com.generate.engine.impl;

import com.generate.bean.PropertiesConfig;
import com.generate.engine.AbstractEngine;
import com.generate.factory.ClassInfoFactory;
import com.generate.intercept.CustomEngine;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

import static com.generate.config.SystemConfig.MATCH_ALL_MARK;

/**
 * ******************************
 * author：      柯贤铭
 * createTime:   2020/1/17 9:18
 * description:  CustomEngineImpl 基于自定义拦截接口的方法调用
 * 基于org.reflections进行全量文件接口扫描
 * version:      V1.0
 * ******************************
 */
public final class CustomEngineImpl {

    private static final Logger logger = LoggerFactory.getLogger(CustomEngineImpl.class);

    /***
     * 扫描全包获取 实现CustomEngine接口的类
     */
    private static Set<Class<? extends CustomEngine>> toDos() {
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage(""))
                .filterInputsBy(input -> {
                    assert input != null;
                    return input.endsWith(".class");
                }));

        return reflections.getSubTypesOf(CustomEngine.class);
    }

    public static void handleCustom() {
        Set<Class<? extends CustomEngine>> classes = toDos();
        for (Class<? extends CustomEngine> aClass : classes) {

            // 基于配置项检测是否需要启用自定义实现类
            if (MATCH_ALL_MARK.equals(PropertiesConfig.getConfig().getCustomHandleInclude()) ||
                    PropertiesConfig.getConfig().getCustomHandleIncludeMap().containsKey(aClass.getSimpleName())) {
                try {
                    // 基于反射构建对象 - 调用handle方法
                    CustomEngine engine = aClass.newInstance();
                    engine.handle(PropertiesConfig.getConfig(), ClassInfoFactory.getClassInfoList(PropertiesConfig.getConfig().getDataBaseType(), PropertiesConfig.getConfig()));
                } catch (InstantiationException | IllegalAccessException e) {
                    logger.error("构建对象失败，失败原因为:{}", e.getLocalizedMessage());
                }
            }
        }
    }
}
