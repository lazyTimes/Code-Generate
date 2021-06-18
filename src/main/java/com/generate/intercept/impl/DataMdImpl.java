package com.generate.intercept.impl;

import com.generate.bean.ClassInfo;
import com.generate.bean.ConfigurationInfo;
import com.generate.config.SystemConfig;
import com.generate.intercept.CustomEngine;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.generate.util.FormatUtil.concat;

/**
 * ******************************
 * author：      柯贤铭
 * createTime:   2020/2/11 15:21
 * description:  表结构Impl
 * version:      V1.0
 * ******************************
 */
public class DataMdImpl implements CustomEngine {

    @Override
    public void handle(ConfigurationInfo config, List<ClassInfo> classInfos) {
        Map<String, Object> params = new HashMap<>(2);
        params.put("config", config);
        params.put("classInfos", classInfos);

        // 构建文件地址
        String filePath = concat(config.getProjectPath(), SRC_MAIN_RESOURCE, SystemConfig.SPACER, "DataSource.md");

        try {
            this.execute(params, concat(SystemConfig.CODE_GENERATE_FILE_PREFIX, SystemConfig.SPACER, SystemConfig.MARK_DOWN_FILE_PREFIX, SystemConfig.SPACER, SystemConfig.FreeMarkerFtlFileConfig.MARK_DOWN), filePath);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }

        logger.info("生成文件地址: {}", filePath);
    }

    private static final String SRC_MAIN_RESOURCE = concat(SystemConfig.SPACER, "src", SystemConfig.SPACER, "main", SystemConfig.SPACER, "resources" + SystemConfig.SPACER);

    private static Logger logger = LoggerFactory.getLogger(DataMdImpl.class);
}
