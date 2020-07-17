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
 * 今天的我来自 http://forte.love:15520/me
 * {@link this#getSweet()} 嘴甜
 * {@link this#getTianGou()} 舔狗
 * {@link this#getPoisonousChickenSoup()} 毒鸡汤
 * {@link this#getTodayMe(String, String)} 今天的我
 */
@Component
public class CreateApiMsg {
    @Resource
    MsgCreate msgCreate;

    private String aipUrl = "https://s.nmsl8.club/getloveword?type=";

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

    /**
     * 今天的我
     *
     * @param code 种子(建议qq号
     * @param name 姓名
     * @return 字符串
     */
    public String getTodayMe(String code, String name) {
        StringBuffer stringBuffer = new StringBuffer("http://forte.love:15520/me?");
        stringBuffer.append("code=").append(code);
        stringBuffer.append("&name=").append(name);
        return msgCreate.okHttpGetMethod(stringBuffer.toString());
    }

}
