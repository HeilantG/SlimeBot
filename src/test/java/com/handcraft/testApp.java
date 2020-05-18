package com.handcraft;

import com.alibaba.fastjson.JSON;
import com.handcraft.util.MsgCreate;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class testApp {
    @Test
    public void test() throws IOException {
        MsgCreate msgCreate = new MsgCreate();
        String str = "http://api.xmlm8.com/tk.php?t="+"2009年起源于北美洲的甲型H1N1流感流行强度为()";
        String s = msgCreate.okHttpGetMethod(str);
        String da = JSON.parseObject(s).get("da").toString();
        System.out.println(da);
    }

}
