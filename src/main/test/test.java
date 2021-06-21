import com.alibaba.fastjson.JSON;
import com.generate.model.WebEngineConfig;

/**
 * @author zhaoxudong
 * @version v1.0.0
 * @Package : PACKAGE_NAME
 * @Description :
 * @Create on : 2021/6/16 17:24
 **/
public class test {

    public static void main(String[] args) {
        System.out.println(JSON.toJSONString(new WebEngineConfig()));
    }
}
