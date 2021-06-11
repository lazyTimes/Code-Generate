package com.generate.factory;

import com.alibaba.fastjson.JSONObject;
import com.generate.bean.ConfigurationInfo;
import com.generate.bean.GlobleConfig;
import com.generate.config.SystemConfig;
import com.generate.enums.SystemPropertiesEnum;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * ******************************
 * author：      柯贤铭
 * createTime:   2020/1/9 17:05
 * description:  配置文件解析器
 * version:      V1.0
 * ******************************
 */
public class PropertiesFactory {

    /***
     * 加载全局配置
     * @throws IOException 默认抛出IO异常
     */
    public static void loadProperties() throws IOException {
        loadSystemConfig();
    }

    /**
     * 读取系统配置或者读取枚举常量
     *
     * @throws IOException
     */
    private static void loadSystemConfig() throws IOException {
        String filePath = System.getProperty("user.dir") + SystemConfig.SPACER + SystemConfig.APPLICATION_PROPERTIES_FILE;
        InputStream inStream;
        if (new File(filePath).exists()) {
            inStream = new FileInputStream(filePath);
        } else {
            inStream = PropertiesFactory.class.getClassLoader().getResourceAsStream(SystemConfig.APPLICATION_PROPERTIES_FILE);
        }
        Properties prop = new Properties();
        prop.load(inStream);
        // FastJson 构造对象
        JSONObject json = new JSONObject();
        loadBySystemEnum(prop, json);
        ConfigurationInfo configurationInfo = json.toJavaObject(ConfigurationInfo.class);
        configurationInfo.setIncludeMap(parseInclude(configurationInfo.getInclude()));
        configurationInfo.setCustomHandleIncludeMap(parseInclude(configurationInfo.getCustomHandleInclude()));
        // 解析项目目录地址
        String projectPath = configurationInfo.getRootPath() + SystemConfig.SPACER + configurationInfo.getProjectName();
        configurationInfo.setProjectPath(projectPath);
        // 共有环境变量设置参数
        GlobleConfig.setGlobleConfig(configurationInfo);
        logger.info("Properties load Successful, Msg is: " + json);
    }

    /**
     * 读取配置文件
     * @param prop
     * @param json
     */
    private static void loadBySystemEnum(Properties prop, JSONObject json) {
        for (SystemPropertiesEnum propEntry : SystemPropertiesEnum.values()) {
            json.put(propEntry.getKey(), prop.getProperty(propEntry.getKey(), propEntry.getValue()));
        }
    }

    /***
     * 解析需要构造的表Map方法
     */
    private static Map<String, String> parseInclude(String include) {
        Map<String, String> result = new HashMap<>();
        if (StringUtils.isBlank(include)) {
            return result;
        }
        String[] strings = include.split(";");
        for (String key : strings) {
            result.put(key, key);
        }
        return result;
    }

    private static Logger logger = LoggerFactory.getLogger(PropertiesFactory.class);
}
