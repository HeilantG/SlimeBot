package com.handcraft;

import com.forte.qqrobot.utils.CQCodeUtil;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class testApp {
    @Test
    public void dateFormat() throws IOException {
        CQCodeUtil cqCodeUtil = CQCodeUtil.build();

        String string = cqCodeUtil.getCQCode_Image("C:\\Users\\Administrator\\Desktop\\酷Q Pro\\data\\image\\1ef67ed8777540b28c85697d06c7ce53.jpg").toString();
        System.out.println(string);

    }

}
