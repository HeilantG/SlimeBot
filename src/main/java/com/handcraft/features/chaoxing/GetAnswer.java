package com.handcraft.features.chaoxing;

import com.alibaba.fastjson.JSON;
import com.handcraft.util.MsgCreate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class GetAnswer {
    @Resource
    MsgCreate msgCreate;

    /*
     *超星查题
     *目前接口为 http://api.xmlm8.com/tk.php
     */

    public String get(String question) {
        String str = "http://api.xmlm8.com/tk.php?t=" + question;
        return JSON.parseObject(msgCreate.okHttpGetMethod(str)).get("da").toString();
    }
}
