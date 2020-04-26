package com.handcraft.util;

import com.handcraft.features.programmerCalendar.ProgrammerCalendar;

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
}
