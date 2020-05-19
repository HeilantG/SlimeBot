package com.handcraft.features.iptk;


import com.handcraft.util.MsgCreate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Heilant Gong
 * 这是闲聊bot
 * 接口来自茉莉机器人
 * http://www.itpk.cn/
 */
@Component
public class IptkBotTalk {
    @Resource
    MsgCreate msgCreate;
    public String getTalk(String question){
        String key = "ffcc4d1f15c2d2b97ee01d22faa35c27";
        String secret = "4ekpnqe0kcux";
        String url = "http://i.itpk.cn/api.php?question="+question+"&api_key="+key+"&api_secret="+ secret;
        return msgCreate.okHttpGetMethod(url);
    }
}
