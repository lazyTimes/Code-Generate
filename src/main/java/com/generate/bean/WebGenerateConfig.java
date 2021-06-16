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
public class WebGenerateConfig {

    public static ConfigurationInfo convertAndReturn(String webEngineConfig) {
        return JSON.parseObject(webEngineConfig, ConfigurationInfo.class);
    }

    public static ConfigurationInfo convertAndReturn(WebEngineConfig webEngineConfig) {
        String result = JSON.toJSONString(webEngineConfig);
        return convertAndReturn(result);
    }
}
