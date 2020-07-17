package com.handcraft.features.qqAi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.handcraft.util.StringUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.TreeMap;

/**
 * 腾讯机器人闲聊
 *
 * @author Heilant Gong
 * <p>
 * {@link this#getSign(TreeMap)} 获取sing接口授权 详见 https://ai.qq.com/doc/auth.shtml
 * {@link this#getTalk(String, String)} 访问接口 获取聊天内容
 */
@Component
public class QQAiTalk {
    @Resource
    private StringUtil stringUtil;
    private String appId = "2154598615";
    private String appKey = "4UQUZsClEkoNqN2z";
    private String link = "https://api.ai.qq.com/fcgi-bin/nlp/nlp_textchat";

    /**
     * @param question 问题
     * @param session  聊天唯一表示
     * @return 返回Json字符串
     */
    public String getTalk(String question, String session) {
        TreeMap<String, String> singMap = new TreeMap<>();
        singMap.put("app_id", appId);
        singMap.put("time_stamp", Long.toString(System.currentTimeMillis() / 1000));
        singMap.put("nonce_str", stringUtil.getUUID());
        singMap.put("question", question);
        singMap.put("session", session);
        singMap.put("sign", getSign(singMap));
        JSONObject jsonObject = JSON.parseObject(JSONObject.toJSONString(singMap));
        try {
            return Jsoup.connect(link).data(singMap).ignoreContentType(true).get().text();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * @param singMap 需要加密的Map
     * @return 加密后的MD5
     */
    private String getSign(TreeMap<String, String> singMap) {
        StringBuffer md5 = new StringBuffer();
        singMap.forEach((key, value) -> {
            try {
                md5.append(key).append("=").append(URLEncoder.encode(value, "utf-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        });
        md5.append("app_key=").append(appKey);
        return DigestUtils.md5Hex(md5.toString().getBytes()).toUpperCase();
    }
}
