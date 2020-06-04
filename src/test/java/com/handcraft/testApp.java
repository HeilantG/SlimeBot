package com.handcraft;

import com.handcraft.util.ImgDownload;
import com.handcraft.util.StringUtil;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class testApp {
    @Resource
    ImgDownload imgDownload;
    @Resource
    StringUtil stringUtil;

    @Test
    public void all() {
        String msgStr = "[CQ:at,qq=1183216650]";
        System.out.println(msgStr.substring(msgStr.indexOf("qq=") + 3,msgStr.length()-1));
    }


}
