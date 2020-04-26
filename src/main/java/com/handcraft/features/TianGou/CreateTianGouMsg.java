package com.handcraft.features.TianGou;

import com.alibaba.fastjson.JSONObject;
import com.handcraft.util.MsgCreate;

/**
 * @author HeilantG
 * 舔狗发言 接口来自 http://api.yyhy.me/tg.php?type=api
 */

public class CreateTianGouMsg {

    public static String get() {
        return format(MsgCreate.httpGetMethod("http://api.yyhy.me/tg.php?type=api"));
    }

    private static String format(String Json) {
        // 第一次取值
        JSONObject obj = JSONObject.parseObject(Json);
        String date = obj.getString("date");
        date.replace("\\", "\\\\");
        String content = obj.getString("content");
        content.replace("\\", "\\\\");
        content = content.substring(0, content.length() - 1);
        // 第二次取值
        return date + "\n" + content;
    }

}
