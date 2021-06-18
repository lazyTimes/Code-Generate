package com.generate.intercept.impl;

import com.generate.bean.ClassInfo;
import com.generate.bean.ConfigurationInfo;
import com.generate.config.SystemConfig;
import com.generate.util.IOTools;
import com.generate.intercept.CustomEngine;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ******************************
 * author：      柯贤铭
 * createTime:   2020/3/21 17:19
 * description:  基于LayUi的增删改查模板
 * version:      V1.0
 * ******************************
 */
public class LayUiHtmlImpl implements CustomEngine {
    @Override
    public void handle(ConfigurationInfo config, List<ClassInfo> classInfos) {
        // 复制公有文件
        copyCommonFiles(config);

        // 参数
        Map<String, Object> params = new HashMap<>();
        params.put("config"    , config);
        params.put("classInfos", classInfos);

        // 处理需要复制的config.js init.json
        String configJs    = config.getProjectPath() + STATIC + SystemConfig.SPACER + "api"  + SystemConfig.SPACER + "config.js";
        String initJson    = config.getProjectPath() + STATIC + SystemConfig.SPACER + "api"  + SystemConfig.SPACER + "init.json";
        String welcomeHtml = config.getProjectPath() + STATIC + SystemConfig.SPACER + "page" + SystemConfig.SPACER + "welcome.html";
        String indexHtml   = config.getProjectPath() + STATIC + SystemConfig.SPACER + "index.html";

        try {
            this.execute(params, "code-generator/layui-html/api/config.ftl"       , configJs);
            this.execute(params, "code-generator/layui-html/api/init.ftl"         , initJson);
            this.execute(params, "code-generator/layui-html/page-file/welcome.ftl", welcomeHtml);
            this.execute(params, "code-generator/layui-html/index.ftl"            , indexHtml);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }

        // 针对所有数据表处理 table, add, edit 界面
        for (ClassInfo info : classInfos) {

            // 类参数
            params.put("classInfo", info);

            String table = config.getProjectPath() + STATIC + SystemConfig.SPACER + "page" + SystemConfig.SPACER + info.getClassName() + "-table.html";
            String add   = config.getProjectPath() + STATIC + SystemConfig.SPACER + "page" + SystemConfig.SPACER + info.getClassName() + "-add.html";
            String edit  = config.getProjectPath() + STATIC + SystemConfig.SPACER + "page" + SystemConfig.SPACER + info.getClassName() + "-edit.html";
            String see   = config.getProjectPath() + STATIC + SystemConfig.SPACER + "page" + SystemConfig.SPACER + info.getClassName() + "-see.html";
            try {
                this.execute(params, "code-generator/layui-html/page-file/table.ftl" , table);
                this.execute(params, "code-generator/layui-html/page-file/add.ftl"   , add);
                this.execute(params, "code-generator/layui-html/page-file/edit.ftl"  , edit);
                this.execute(params, "code-generator/layui-html/page-file/see.ftl"   , see);
            } catch (IOException | TemplateException e) {
                e.printStackTrace();
            }
        }

        logger.info("生成文件地址：{}/code-generator/layui-html/page-file", config.getProjectPath());
    }

    private void copyCommonFiles (ConfigurationInfo config) {
        try {
            // 复制 css, images, js, lib
            IOTools.loadRecourseFromJarByFolder("/templates/code-generator/layui-html/css",
                    config.getProjectPath() + STATIC, this.getClass(),
                    "/templates/code-generator/layui-html/css");

            IOTools.loadRecourseFromJarByFolder("/templates/code-generator/layui-html/images",
                    config.getProjectPath() + STATIC, this.getClass(),
                    "/templates/code-generator/layui-html/images");

            IOTools.loadRecourseFromJarByFolder("/templates/code-generator/layui-html/js",
                    config.getProjectPath() + STATIC, this.getClass(),
                    "/templates/code-generator/layui-html/js");

            IOTools.loadRecourseFromJarByFolder("/templates/code-generator/layui-html/lib",
                    config.getProjectPath() + STATIC, this.getClass(),
                    "/templates/code-generator/layui-html/lib");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static final String SRC_MAIN_RESOURCE = SystemConfig.SPACER + "src" + SystemConfig.SPACER + "main" + SystemConfig.SPACER + "resources" + SystemConfig.SPACER;

    private static final String STATIC = SRC_MAIN_RESOURCE + SystemConfig.SPACER + "static" + SystemConfig.SPACER;

    private static Logger logger = LoggerFactory.getLogger(LayUiHtmlImpl.class);
}
