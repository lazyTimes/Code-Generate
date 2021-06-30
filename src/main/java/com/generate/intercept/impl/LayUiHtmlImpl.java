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

import static com.generate.config.SystemConfig.*;
import static com.generate.util.FormatUtil.concat;

/**
 * ******************************
 * author：      柯贤铭
 * createTime:   2020/3/21 17:19
 * description:  基于LayUi的增删改查模板
 * version:      V1.0
 * ******************************
 */
public class LayUiHtmlImpl implements CustomEngine {


    private static Logger logger = LoggerFactory.getLogger(LayUiHtmlImpl.class);

    @Override
    public void handle(ConfigurationInfo config, List<ClassInfo> classInfos) {
        // 复制公有文件
        copyCommonFiles(config);

        // 参数
        Map<String, Object> params = new HashMap<>();
        params.put("config", config);
        params.put("classInfos", classInfos);

        // 处理需要复制的config.js init.json
        String configJs = concat(config.getProjectPath(), SystemConfig.STATIC, SystemConfig.SPACER, "api", SystemConfig.SPACER, "config.js");
        String initJson = concat(config.getProjectPath(), SystemConfig.STATIC, SystemConfig.SPACER, "api", SystemConfig.SPACER, "init.json");
        String welcomeHtml = concat(config.getProjectPath(), SystemConfig.STATIC, SystemConfig.SPACER, "page", SystemConfig.SPACER, "welcome.html");
        String indexHtml = concat(config.getProjectPath(), SystemConfig.STATIC, SystemConfig.SPACER, "index.html");

        try {
            this.execute(params, concat(CODE_GENERATE_FILE_PREFIX, SPACER, LAYUI_HTML_FILE_PREFIX, "/api/config.ftl"), configJs);
            this.execute(params, concat(CODE_GENERATE_FILE_PREFIX, SPACER, LAYUI_HTML_FILE_PREFIX, "/api/init.ftl"), initJson);
            this.execute(params, concat(CODE_GENERATE_FILE_PREFIX, SPACER, LAYUI_HTML_FILE_PREFIX, "/page-file/welcome.ftl"), welcomeHtml);
            this.execute(params, concat(CODE_GENERATE_FILE_PREFIX, SPACER, LAYUI_HTML_FILE_PREFIX, "/index.ftl"), indexHtml);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }

        // 针对所有数据表处理 table, add, edit 界面
        for (ClassInfo info : classInfos) {

            // 类参数
            params.put("classInfo", info);

            String table = concat(config.getProjectPath(), SystemConfig.STATIC, SystemConfig.SPACER, "page", SystemConfig.SPACER, info.getClassName(), "-table.html");
            String add = concat(config.getProjectPath(), SystemConfig.STATIC, SystemConfig.SPACER, "page", SystemConfig.SPACER, info.getClassName(), "-add.html");
            String edit = concat(config.getProjectPath(), SystemConfig.STATIC, SystemConfig.SPACER, "page", SystemConfig.SPACER, info.getClassName(), "-edit.html");
            String see = concat(config.getProjectPath(), SystemConfig.STATIC, SystemConfig.SPACER, "page", SystemConfig.SPACER, info.getClassName(), "-see.html");
            try {
                this.execute(params, concat(CODE_GENERATE_FILE_PREFIX, SPACER, LAYUI_HTML_FILE_PREFIX, "/page-file/table.ftl"), table);
                this.execute(params, concat(CODE_GENERATE_FILE_PREFIX, SPACER, LAYUI_HTML_FILE_PREFIX, "/page-file/add.ftl"), add);
                this.execute(params, concat(CODE_GENERATE_FILE_PREFIX, SPACER, LAYUI_HTML_FILE_PREFIX, "/page-file/edit.ftl"), edit);
                this.execute(params, concat(CODE_GENERATE_FILE_PREFIX, SPACER, LAYUI_HTML_FILE_PREFIX, "/page-file/see.ftl"), see);
            } catch (IOException | TemplateException e) {
                e.printStackTrace();
            }
        }

        logger.info("生成文件地址：{}/{}/{}/page-file", config.getProjectPath(), CODE_GENERATE_FILE_PREFIX, LAYUI_HTML_FILE_PREFIX);
    }

    private void copyCommonFiles(ConfigurationInfo config) {
        try {
            // 复制 css, images, js, lib
            String css = concat(TEMPLATE_BASE_PACKAGE, CODE_GENERATE_FILE_PREFIX, SPACER, LAYUI_HTML_FILE_PREFIX, "/css");
            IOTools.loadRecourseFromJarByFolder(
                    css,
                    concat(config.getProjectPath(), SystemConfig.STATIC),
                    this.getClass(),
                    css);

            String images = concat(TEMPLATE_BASE_PACKAGE, CODE_GENERATE_FILE_PREFIX, SPACER, LAYUI_HTML_FILE_PREFIX, "/images");
            IOTools.loadRecourseFromJarByFolder(
                    images,
                    concat(config.getProjectPath(), SystemConfig.STATIC),
                    this.getClass(),
                    images);

            String js = concat(TEMPLATE_BASE_PACKAGE, CODE_GENERATE_FILE_PREFIX, SPACER, LAYUI_HTML_FILE_PREFIX, "/js");
            IOTools.loadRecourseFromJarByFolder(
                    js,
                    concat(config.getProjectPath(), SystemConfig.STATIC),
                    this.getClass(),
                    js);

            String lib = concat(TEMPLATE_BASE_PACKAGE, CODE_GENERATE_FILE_PREFIX, SPACER, LAYUI_HTML_FILE_PREFIX, "/lib");
            IOTools.loadRecourseFromJarByFolder(
                    lib,
                    concat(config.getProjectPath(), SystemConfig.STATIC),
                    this.getClass(),
                    lib);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
