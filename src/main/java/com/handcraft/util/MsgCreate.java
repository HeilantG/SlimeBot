package com.handcraft.util;

import com.handcraft.features.programmerCalendar.ProgrammerCalendar;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//信息文本拼接工具

public class MsgCreate {
    //每日消息
    public static String getDayMsg() {
        // 最终返回的文字
        StringBuilder str = new StringBuilder();
        // 确认今天是周几
        int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1;
        //欢迎头
        str.append(dayHello(day));
        //老黄历
        str.append(ProgrammerCalendar.getCalendar());
        //日图
        //str.append("[CQ:image,file=" + day + ".jpg]");
        return str.toString();
    }

    //老黄历
    public static String getProgrammerCalendar(int... key) {
        return ProgrammerCalendar.getCalendar(key);
    }

    private static String dayHello(int day) {
        StringBuilder str = new StringBuilder();
        String[] arr = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        str.append("各位早上好,今天是");
        str.append(arr[day] + "\n");
        if (day > 5) {
            str.append("周末啦,出去散散心吧吧,又或者来一趟沙之家?\n");
        }
        return str.toString();
    }


    public static String unicodeToString(String str) {

        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
        Matcher matcher = pattern.matcher(str);
        char ch;
        while (matcher.find()) {
            //group 6728
            String group = matcher.group(2);
            //ch:'木' 26408
            ch = (char) Integer.parseInt(group, 16);
            //group1 \u6728
            String group1 = matcher.group(1);
            str = str.replace(group1, ch + "");
        }
        return str;
    }

    public static String httpGetMethod(String url) {
        try {
            CloseableHttpClient httpClient = HttpClients.createSystem();
            // 创建get方法

            HttpGet hg = new HttpGet(url);
/*
            // 浏览器表示
            hg.addHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.7.6)");
            // 传输的类型
            hg.addHeader("Content-Type", "application/x-www-form-urlencoded");*/
            // 超时断开(起码不会卡连接池
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(5000).setConnectionRequestTimeout(5000)
                    .setSocketTimeout(5000).build();
            hg.setConfig(requestConfig);
            //执行请求
            CloseableHttpResponse resp = httpClient.execute(hg);
            //获取请求结果的html 实体
            HttpEntity entity = resp.getEntity();
            // 使用EntityUtils toString方法将entity转换为String ，编码为gbk
            String entitString = EntityUtils.toString(entity, "utf-8");
            return entitString;

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
