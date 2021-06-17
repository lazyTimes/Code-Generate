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
    CONTROLLER("controller", "controller"),
    ENTITY("entity", "entity"),
    MAPPER("mapper", "mapper"),
    MAPPERXML("mapperxml", "mapperxml"),
    CONFIG("config", "config"),
    FIX("fix", "fix"),
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
