package com.handcraft;

import com.handcraft.features.pixiv.PixivMsg;
import com.handcraft.pojo.ImgInfo;
import com.handcraft.util.StringUtil;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@SpringBootTest
public class testApp {

    @Test
    public void testTime() throws ParseException {
        SimpleDateFormat dfs = new SimpleDateFormat("HH:mm:ss");
        java.util.Date begin = dfs.parse("18:30:24");
        java.util.Date end = dfs.parse(dfs.format(new java.util.Date()));
        long between = (begin.getTime() - end.getTime()) / 1000;
        long day1 = between / (24 * 3600);
        long hour1 = between % (24 * 3600) / 3600;
        long minute1 = between % 3600 / 60;
        long second1 = between % 60 / 60;
        System.out.println("" + day1 + "天" + hour1 + "小时" + minute1 + "分" + second1 + "秒");
    }

    @Test
    public void testokHttp() throws IOException {
        int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1;
        System.out.println(day);

        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        System.out.println(df.format(new java.util.Date()));
    }

    @Test
    public void testDate() {
        StringUtil stringUtil = new StringUtil();
        System.out.println(Date.valueOf(stringUtil.getDate()));
    }

    @Test
    public void contextLoads() {
        PixivMsg pixivMsg = new PixivMsg();
        List<ImgInfo> day = pixivMsg.getDay();
        day.forEach(System.out::println);
    }
}
