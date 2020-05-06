package com.handcraft;

import com.handcraft.util.MsgCreate;
import com.handcraft.util.StringUtil;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
public class testApp {
    @Test
    public void dateFormat() {
     /*   Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(sdf.format(dt));*/
        StringUtil stringUtil = new StringUtil();
        MsgCreate msgCreate = new MsgCreate();
        System.out.println(msgCreate.getProgrammerCalendar(1));
    }

}
