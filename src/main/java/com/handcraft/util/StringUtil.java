package com.handcraft.util;

import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * 字符串工具类
 *
 * @author HeilantG
 */
@Component
public class StringUtil {

    /**
     * 获取一个去掉-的uuid
     *
     * @return uuid
     */
    public String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }


    /**
     * 获取当前日期 并转变为String
     *
     * @return 当前日期的String
     */
    public String getDate() {
        Date dt = new Date();
        //最后的aa表示“上午”或“下午”    HH表示24小时制    如果换成hh表示12小时制
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(dt);
    }


    /**
     * 根据给定Date 格式化为String
     *
     * @param date 所需格式化的Date
     * @return 格式化后的date
     */
    public String formatDate(Date date) {
        //最后的aa表示“上午”或“下午”    HH表示24小时制    如果换成hh表示12小时制
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }


    /**
     * 获取当前日期的前后某一天的日期
     *
     * @param i 前一天或后一天
     * @return 日期
     */
    public Date getDesignatedDate(int i) {
        //最后的aa表示“上午”或“下午”    HH表示24小时制    如果换成hh表示12小时制

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, i);
        return calendar.getTime();
    }

    public String backMD5(String inStr) {

        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];

        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();

        for (int i = 0; i < md5Bytes.length; i++) {

            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }

        return hexValue.toString();

    }
}
