package com.handcraft;

import com.handcraft.util.MsgCreate;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class testApp {

    @Test
    public void all() {
        System.out.println(System.getProperty("user.dir"));
    }

    @Test
    public void formart() {
        MsgCreate msgCreate = new MsgCreate();
        StringBuffer stringBuffer = new StringBuffer("http://forte.love:15520/me?");
        stringBuffer.append("code=").append("123");
        stringBuffer.append("&name=").append("滑稽");
        System.out.println( msgCreate.okHttpGetMethod(stringBuffer.toString()));
    }

}
