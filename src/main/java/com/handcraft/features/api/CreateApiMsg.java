package com.handcraft.features.api;

import com.alibaba.fastjson.JSONObject;
import com.handcraft.util.MsgCreate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 智慧发言
 *
 * @author HeilantG
 * <p>
 * 这是甜/舔/毒消息创建类
 * 嘴甜和毒鸡汤消息来自 https://s.nmsl8.club/
 * 舔狗消息来自 http://api.yyhy.me/tg.php?type=api
 * {@link this#getSweet()} 嘴甜
 * {@link this#getTianGou()} 舔狗
 * {@link this#getPoisonousChickenSoup()} 毒鸡汤
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
