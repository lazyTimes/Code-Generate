package com.generate.factory;

import cn.hutool.core.collection.CollectionUtil;
import com.generate.bean.ClassInfo;
import com.generate.bean.ConfigurationInfo;
import com.generate.bean.PropertiesConfig;
import com.generate.engine.AbstractEngine;
import com.generate.model.WebEngineConfig;
import com.generate.util.DataBaseUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.generate.config.SystemConfig.MATCH_ALL_MARK;

/**
 * ******************************
 * author：      柯贤铭
 * createTime:   2020/1/9 18:24
 * description:  ClassInfoFactory
 * version:      V1.0
 * ******************************
 */
public class ClassInfoFactory {

    private static final Logger logger = LoggerFactory.getLogger(ClassInfoFactory.class);

    /**
     * 用于properties静态配置读取使用，初次加载放入缓存
     *
     * @param databaseType
     * @param configurationInfo
     * @return
     */
    public static List<ClassInfo> getClassInfoList(String databaseType, ConfigurationInfo configurationInfo) {
        List<ClassInfo> result = new ArrayList<>();
        try {
            // 获取配置项
            List<String> tableNames = DataBaseUtil.getAllTableNames(databaseType);
            for (String tableName : tableNames) {
                // 仅加载 *; 配置项 或者 include包含项才进行处理
                if (MATCH_ALL_MARK.equals(configurationInfo.getInclude()) || configurationInfo.getIncludeMap().containsKey(tableName)) {
                    ClassInfo classInfo = DataBaseUtil.parseClassInfo(databaseType, tableName);
                    result.add(classInfo);
                }
            }
        } catch (Exception e) {
            logger.error("获取class信息列表失败，失败原因为：{}", e.getLocalizedMessage());
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 根据web请求参数生成指定的表
     * @param databaseType
     * @param webEngineConfig
     * @return
     */
    public static List<ClassInfo> getClassInfoList(String databaseType, WebEngineConfig webEngineConfig) {
        List<ClassInfo> result = new ArrayList<>();
        try {
            List<WebEngineConfig.WebGenerateParam> webGenerateParams = Objects.requireNonNull(webEngineConfig.getWebGenerateParams());
            // 获取配置项
            List<String> tableNames = DataBaseUtil.getAllTableNames(databaseType);
            if (MATCH_ALL_MARK.equals(webEngineConfig.getInclude())) {
                logger.warn("匹配到 *; 参数，默认为所有表生成代码");
                for (String tableName : tableNames) {
                    ClassInfo classInfo = DataBaseUtil.parseClassInfo(databaseType, tableName);
                    result.add(classInfo);
                }
                return result;
            }
            if(CollectionUtil.isEmpty(webGenerateParams)){
                return Collections.emptyList();
            }
            // 根据请求参数匹配对应的表
            for (WebEngineConfig.WebGenerateParam webGenerateParam : webGenerateParams) {
                for (String tableName : tableNames) {
                    if (tableName.equals(webGenerateParam.getTableName())) {
                        ClassInfo classInfo = DataBaseUtil.parseClassInfo(databaseType, tableName);
                        result.add(classInfo);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            logger.error("获取class信息列表失败，失败原因为：{}", e.getLocalizedMessage());
        }
        return result;
    }

}
