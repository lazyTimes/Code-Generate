package com.generate;

import com.generate.bean.PropertiesConfig;
import com.generate.engine.AbstractEngine;
import com.generate.engine.impl.DefaultEngine;
import com.generate.factory.PropertiesFactory;

/**
 * ******************************
 * author：      柯贤铭
 * createTime:   2020/1/9 16:58
 * description:  Code-Generate 代码生成器运行类
 * version:      V1.0
 * ******************************
 */
public class GenerateApp {

    /***
     * 执行 - 构建项目
     */
    public static void main(String[] args){
        AbstractEngine engine = new DefaultEngine(PropertiesConfig.getConfig());
        engine.execute();
    }
}
