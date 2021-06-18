package com.generate.enums;

/**
 * @author zhaoxudong
 * @version v1.0.0
 * @Package : com.generate.enums
 * @Description : 系统支持生成模块enum
 * @Create on : 2021/6/17 15:12
 **/
public enum GenMouduleEnum {
    SERVICE("service", "service"),
    SERVICEIMPL("serviceimpl", "serviceimpl"),
    CONTROLLER("controller", "controller"),
    ENTITY("entity", "entity"),
    MAPPER("mapper", "mapper"),
    MAPPERXML("mapperxml", "mapperxml"),
    CONFIG("config", "config"),
    FIX("fix", "fix"),
    // 自定义模块
    CUSTOM("custom", "custom"),
    ;

    private final String key;
    private final String value;

    GenMouduleEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

}
