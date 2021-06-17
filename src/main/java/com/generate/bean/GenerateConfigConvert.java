package com.generate.bean;

import com.alibaba.fastjson.JSON;
import com.generate.model.WebEngineConfig;

/**
 * @author zhaoxudong
 * @version v1.0.0
 * @Package : com.generate.bean
 * @Description : web相关的代码生成器配置
 * @Create on : 2021/6/16 16:18
 **/
public class GenerateConfigConvert {

    /**
     * 将json转为对应实体对象
     * 这里转为properties所需配置
     * @param webEngineConfig
     * @return
     */
    public static ConfigurationInfo convertConfigInfo(String webEngineConfig) {
        return JSON.parseObject(webEngineConfig, ConfigurationInfo.class);
    }

    /**
     * 将json转为对应实体对象
     * 这里转为web所需配置
     * @param webEngineConfig
     * @return
     */
    public static WebEngineConfig convertWebEngineConfig(String webEngineConfig) {
        return JSON.parseObject(webEngineConfig, WebEngineConfig.class);
    }

    public static ConfigurationInfo convertConfigInfo(WebEngineConfig webEngineConfig) {
        String result = JSON.toJSONString(webEngineConfig);
        return convertConfigInfo(result);
    }
}
