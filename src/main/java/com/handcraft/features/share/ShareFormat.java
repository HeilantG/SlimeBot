package com.handcraft.features.share;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ShareFormat {
    public List<String> format(String str) {
        List<String> infoList = new ArrayList<>();
        String judge = str;
        str = str.substring(str.indexOf("content=") + 8);
        str = str.substring(0, str.indexOf("title=") - 1);
        str = str.replace("&#44", "");
        str = str.replace("&#91", "");
        str = str.replace("&#93", "");
        str = str.replace(";", ",");
        str = str.replace("detail_1", "detail");
        JSONObject jsonObject = JSON.parseObject(str);
        //System.out.println(jsonObject.toJSONString());
        JSONObject detail = jsonObject.getJSONObject("detail");
        String qqdocurl = detail.getString("qqdocurl");
        String preview = detail.getString("preview");
        String share = "";

        try {
            share = qqdocurl.substring(0, qqdocurl.indexOf("utm_medium") - 1);
        } catch (Exception e) {
        }
        try {
            share = qqdocurl.substring(0, qqdocurl.indexOf("share") - 1);
        } catch (Exception e) {
        }
        if (judge.contains("detail_1")) {
            infoList.add(detail.getString("desc"));
        } else {
            infoList.add(detail.getString("title"));
        }
        infoList.add(share);
        infoList.add(preview);
        return infoList;
    }
}
