package java.com.mysql.util;

import com.alibaba.fastjson.JSON;
import com.generate.bean.PropertiesConfig;
import com.generate.engine.impl.WebEngine;
import com.generate.factory.ClassInfoFactory;
import com.generate.factory.PropertiesFactory;
import com.generate.model.WebEngineConfig;
import com.generate.util.DataBaseUtil;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by Kerwin Cotter on 2020/1/9.
 *
 * StepOne: 加载配置文件
 */
public class UtilTest {

    @Before
    public void before() throws IOException {
//        PropertiesFactory.loadProperties();
    }

    @Test
    public void loadProperties() throws IOException {
        System.out.println(JSON.toJSONString(new WebEngineConfig()));
//        PropertiesFactory.loadProperties();
    }

    @Test
    public void getConnection() {
        System.out.println(DataBaseUtil.getConnection());
    }

    @Test
    public void getClassInfoList() {
        System.out.println(ClassInfoFactory.getClassInfoList(PropertiesConfig.getConfig().getDataBaseType()));
    }
}