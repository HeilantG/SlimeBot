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
        System.out.println(System.getProperty("user.dir"));
    }

}
