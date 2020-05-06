package com.handcraft.util;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * @author HeilantG
 * uuid 工具类 组要用来转换格式
 */
@Component
public class StringUtil {
    /**
     * 获取uuid
     */
    public String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 获取当前日期 并转变为String
     */
    public String getDate() {
        Date dt = new Date();
        //最后的aa表示“上午”或“下午”    HH表示24小时制    如果换成hh表示12小时制
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(dt);
    }

    /**
     * 根据给定Date 格式化为String日期
     */
    public String formatDate(Date date) {
        //最后的aa表示“上午”或“下午”    HH表示24小时制    如果换成hh表示12小时制
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    /**
     * 获取当前日期的前后某一天的日期
     */
    public Date getDesignatedDate(int i) {
        //最后的aa表示“上午”或“下午”    HH表示24小时制    如果换成hh表示12小时制

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, i);
        return calendar.getTime();
    }
}
