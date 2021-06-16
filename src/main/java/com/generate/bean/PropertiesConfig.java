package com.generate.bean;

import com.generate.factory.PropertiesFactory;

import java.io.IOException;

/**
 * ******************************
 * author：      柯贤铭
 * createTime:   2020/1/9 17:11
 * description:  单例模式 -> 全局配置信息
 * version:      V1.0
 * ******************************
 */
public final class PropertiesConfig {

    // 配置信息
    private volatile static ConfigurationInfo CONFIGURATION_INFO = null;

    static {
        try {
            PropertiesFactory.loadProperties();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /***
     * 获取全局配置
     * 单例模式 双重锁校验
     */
    public static ConfigurationInfo getConfig() {
        return CONFIGURATION_INFO;
    }

    public static void setConfig(ConfigurationInfo CONFIGURATIONInfo) {
        PropertiesConfig.CONFIGURATION_INFO = CONFIGURATIONInfo;
    }

    private PropertiesConfig() {
        throw new AssertionError("can not newInstance");
    }
}
