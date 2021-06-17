package com.generate.factory;

import cn.hutool.core.collection.CollectionUtil;
import com.generate.bean.ClassInfo;
import com.generate.bean.ConfigurationInfo;
import com.generate.bean.PropertiesConfig;
import com.generate.model.WebEngineConfig;
import com.generate.util.DataBaseUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * ******************************
 * author：      柯贤铭
 * createTime:   2020/1/9 18:24
 * description:  ClassInfoFactory
 * version:      V1.0
 * ******************************
 */
public class ClassInfoFactory {

    private volatile static List<ClassInfo> CLASS_INFO_LIST = new ArrayList<>();

    public static List<ClassInfo> getClassInfoList(String databaseType) {
        if (CollectionUtil.isEmpty(CLASS_INFO_LIST)) {
            synchronized (ClassInfoFactory.class) {
                if (CollectionUtil.isEmpty(CLASS_INFO_LIST)) {
                    try {
                        // 获取配置项
                        ConfigurationInfo config = PropertiesConfig.getConfig();

                        List<String> tableNames = DataBaseUtil.getAllTableNames(databaseType);
                        for (String tableName : tableNames) {
                            // 仅加载 *; 配置项 或者 include包含项才进行处理
                            if("*;".equals(config.getInclude()) || config.getIncludeMap().containsKey(tableName)) {
                                ClassInfo classInfo = DataBaseUtil.parseClassInfo(databaseType, tableName);
                                CLASS_INFO_LIST.add(classInfo);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return CLASS_INFO_LIST;
    }
}
