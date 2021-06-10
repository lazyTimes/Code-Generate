package com.mysql.factory;

import com.alibaba.fastjson.JSONObject;
import com.mysql.bean.ConfigurationInfo;
import com.mysql.bean.GlobleConfig;
import com.mysql.config.SystemConfig;
import com.mysql.enums.SystemPropertiesEnum;
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
     * 配置文件KEYS
     * @deprecated 改动，废弃该写法
     */
    private static final String[] KEYS = {"ip", "port", "driver", "dataBase", "encoding", "loginName", "passWord"
            , "include", "projectName", "packageName", "authorName", "rootPath", "customHandleInclude"};

    /***
     * 配置文件默认Values
     * @deprecated 改动，废弃该写法
     */
    private static final String[] VALUES = {"127.0.0.1", "3306", "com.mysql.jdbc.Driver", "db_file", "UTF-8", "root", ""
            , "*", "Demo", "com.demo", "Kerwin", "F:\\code", "*"};

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
        String filePath = System.getProperty("user.dir") + File.separator + SystemConfig.APPLICATION_PROPERTIES_FILE;
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
        String projectPath = configurationInfo.getRootPath() + File.separator + configurationInfo.getProjectName();
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

    /**
     * 读取配置文件或者读取默认的成员属性键值对配置
     * @deprecated 这种写法不利于维护配置，改为由枚举进行匹配和配置
     * @throws IOException
     */
    private static void loadBySystemConfig(Properties prop, JSONObject json) throws IOException {
        for (int i = 0; i < KEYS.length; i++) {
            json.put(KEYS[i], prop.getProperty(KEYS[i], VALUES[i]));
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
