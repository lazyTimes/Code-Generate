package com.generate.controller;

/**
 * @author zhaoxudong
 * @version v1.0.0
 * @Package : com.generate.controller
 * @Description : 定制化代码生成器接口规范
 * @Create on : 2021/6/15 11:26
 **/
public interface GenerateInterface {

    /**
     * 根据前端的传递结果，生成后端的传递结果
     * @param param
     * @return
     */
    Object generateSql(String param);

}
