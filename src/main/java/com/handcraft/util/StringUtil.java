package com.handcraft.util;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author HeilantG
 * uuid 工具类 组要用来转换格式
 */
@Component
public class StringUtil {
    public String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public String getDate() {
        Date dt = new Date();
        //最后的aa表示“上午”或“下午”    HH表示24小时制    如果换成hh表示12小时制
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(dt);
    }
}
