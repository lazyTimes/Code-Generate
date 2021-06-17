package com.generate.controller;

import com.generate.bean.GenerateConfigConvert;
import com.generate.engine.AbstractEngine;
import com.generate.engine.impl.WebEngine;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zhaoxudong
 * @version v1.0.0
 * @Package : com.generate.controller
 * @Description : 接口生成对应的数据
 * @Create on : 2021/6/16 09:41
 **/
@Controller
@RequestMapping("/gen")
public class GenerateController implements GenerateInterface{

    @Override
    @RequestMapping("/generate")
    public Object generateSql(@RequestBody String param) {
        AbstractEngine engine = new WebEngine(GenerateConfigConvert.convertWebEngineConfig(param));
        engine.execute();
        return null;
    }
}
