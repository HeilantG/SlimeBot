package com.handcraft.features.share;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * 解析qq分享
 *
 * @author Heilant Gong
 * 本质为操作字符串
 * 需要重构，更换为Mirai核心后，json格式出现改变
 * 争取解析所有分享
 */
@Component
public class ShareFormat {
    public HashMap<String, String> format(String shareJson) {
        // 截取json字符串 移除无用字段
        shareJson = shareJson.substring(0, shareJson.indexOf("收到"));
        // 转换为jsonObject
        JSONObject jsonObject = JSON.parseObject(shareJson);
        JSONObject detail_1 = jsonObject.getJSONObject("meta").getJSONObject("detail_1");
        String preview = detail_1.getString("preview");
        String qqdocurl = detail_1.getString("qqdocurl");
        qqdocurl = qqdocurl.substring(0, qqdocurl.indexOf("?"));
        String desc = detail_1.getString("desc");
        HashMap<String, String> returnMap = new HashMap<>();
        returnMap.put("preview", preview);
        returnMap.put("qqdocurl", qqdocurl);
        returnMap.put("desc", desc);
        return returnMap;
    }
}
