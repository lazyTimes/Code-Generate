package com.generate.controller;

import com.alibaba.fastjson.JSON;
import com.generate.bean.GenerateConfigConvert;
import com.generate.engine.AbstractEngine;
import com.generate.engine.impl.WebEngine;
import com.generate.model.AjaxResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zxd
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
    @ResponseBody
    public Object generateSql(@RequestBody String param) {
        try {
            AbstractEngine engine = new WebEngine(GenerateConfigConvert.convertWebEngineConfig(param));
            engine.execute();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return AjaxResult.success("代码生成器生成代码执行成功", param);
    }
}
