package com.handcraft.features.share;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ShareFormat {
    public List<String> format(String str){
        str = str.substring(str.indexOf("content=") + 8, str.indexOf("title=")-1);
        str=str.replace("&#44","");
        str=str.replace("&#91","");
        str=str.replace("&#93","");
        str=str.replace(";", ",");
        JSONObject jsonObject = JSON.parseObject(str);
        System.out.println(jsonObject.toJSONString());
        JSONObject detail_1 = jsonObject.getJSONObject("detail_1");
        String qqdocurl = detail_1.getString("qqdocurl");
        String preview = detail_1.getString("preview");
        String share = qqdocurl.substring(0, qqdocurl.indexOf("share") - 1);
        List<String> infoList = new ArrayList<>();
        infoList.add(detail_1.getString("desc"));
        infoList.add(share);
        infoList.add(preview);
        return infoList;
    }
}
