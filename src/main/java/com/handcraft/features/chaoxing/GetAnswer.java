package com.handcraft.features.chaoxing;

import com.alibaba.fastjson.JSON;
import com.handcraft.util.MsgCreate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 超星查题
 *
 * @author Heilant Gong
 * <p>
 * 目前接口为 http://api.xmlm8.com/tk.php
 */
@Component
public class GetAnswer {
    @Resource
    MsgCreate msgCreate;

    public String get(String question) {
        String str = " http://jbks.s759n.cn/chati.php?w=" + question;
        String s = JSON.toJSONString(question);
        return JSON.parseObject(msgCreate.okHttpGetMethod(str)).get("data").toString();
    }
}
