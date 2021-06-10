package com.generate.util;

import com.generate.bean.ConfigurationInfo;
import com.generate.bean.GlobleConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * ******************************
 * author：      柯贤铭
 * createTime:   2020/1/9 17:05
 * description:  DBUtil DB连接
 *               单例模式
 * version:      V1.0
 * ******************************
 */
public class DBUtil {

    private volatile static Connection conn = null;

    private static Logger logger = LoggerFactory.getLogger(DBUtil.class);

    static {
        try {
            Class.forName(GlobleConfig.getGlobleConfig().getDriver());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * TODO： 重构点： 支持多数据源
     * @return
     * @throws Exception
     */
    private static Connection produceConnection() throws Exception {
        ConfigurationInfo config = GlobleConfig.getGlobleConfig();
        String url = String.format("jdbc:%s://%s:%s/%s?characterEncoding=%s", config.getDataBaseType(), config.getIp(), config.getPort(), config.getDataBase(), config.getEncoding());
        return DriverManager.getConnection(url, config.getLoginName(), config.getPassWord());
    }

    /***
     * 获取数据库连接,双重校验保证线程安全
     */
    public static Connection getConnection(){
        try {
            if (null == conn) {
                synchronized (DBUtil.class) {
                    if (null == conn) {
                        long start = System.currentTimeMillis();
                        conn = produceConnection();
                        logger.info("Connection is ready. consume time is: " + (System.currentTimeMillis() - start) + "ms");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}
