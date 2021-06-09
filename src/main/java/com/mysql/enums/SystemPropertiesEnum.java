package com.mysql.enums;

/**
 * @author zhaoxudong
 * @version v1.0.0
 * @Package : com.mysql.enums
 * @Description : 系统配置枚举常量。如果没有从对应配置读取到常量值，使用枚举默认值作为替代
 * @Create on : 2021/6/8 15:14
 **/
public enum SystemPropertiesEnum {
    DRIVER("driver", "com.mysql.jdbc.Driver"),
    IP("ip", "root"),
    // 默认数据库类型为mysql
    DATABASE_NAME("dataBaseType", "mysql"),
    PORT("port", "3306"),
    ENCODING("encoding", "UTF-8"),
    LOGIN_NAME("loginName", "root"),
    PASSWORD("password", "SinoPay2020_"),
    DATABASE("dataBase", ""),
    INCLUDE_TABLES("include", "*;"),
    PROJECT_NAME("projectName", "Demo"),
    PACKAGE_NAME("packageName", "com.demo"),
    ROOT_PATH("rootPath", "C:\\code"),
    CUSTOM_HANDLE_INCLUDE("customHandleInclude", "*;"),
    AUTHOR("authorName", "none");

    SystemPropertiesEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    private final String key;
    private final String value;

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
