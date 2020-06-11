package com.handcraft.features.api;

import com.alibaba.fastjson.JSONObject;
import com.handcraft.util.MsgCreate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author HeilantG
 * 智慧发言 接口来自 https://s.nmsl8.club/
 */
@Component
public class CreateApiMsg {
    @Resource
    MsgCreate msgCreate;

    String aipUrl = "https://s.nmsl8.club/getloveword?type=";

    /**
     * 嘴甜
     */
    public String getSweet() {
        return format(msgCreate.okHttpGetMethod(aipUrl + "1"));
    }

    /**
     * 舔狗
     */
    public String getTianGou() {
        return format(msgCreate.okHttpGetMethod("http://api.yyhy.me/tg.php?type=api"));
    }

    /**
     * 毒鸡汤
     */
    public String getPoisonousChickenSoup() {
        return format(msgCreate.okHttpGetMethod(aipUrl + "4"));
    }

    private static String format(String Json) {
        // 第一次取值
        JSONObject obj = JSONObject.parseObject(Json);
        String content = obj.getString("content");
        content.replace("\\", "\\\\");
        // 第二次取值
        return content;
    }

}
