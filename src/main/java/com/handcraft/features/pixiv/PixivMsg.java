package com.handcraft.features.pixiv;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.handcraft.pojo.ImgInfo;
import com.handcraft.util.MsgCreate;
import com.handcraft.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Component
public class PixivMsg {

    @Autowired
    StringUtil stringUtil;

    // 获取涩图
    public static String getSeTu(String key, int r18) {
        String url = "https://api.lolicon.app/setu/?apikey=" + key + "&r18=" + r18 + "&size1200=true";
        //处理信息
        // 第一次取值
        JSONObject obj = JSONObject.parseObject(MsgCreate.httpGetMethod(url));
        String code = obj.getString("data");
        // 第二次取值
        String substring = code.substring(1, code.length() - 1);
        JSONObject jsonObject = JSONObject.parseObject(substring);
        return jsonObject.getString("url");
    }

    //日图
    public List<ImgInfo> getDay() {
        List<ImgInfo> imgInfos = new ArrayList<>();
        //获取信息
        String url = "https://api.pixivic.com/ranks?page=1&date=2020-04-23&mode=day&pageSize=10";
        String str = MsgCreate.httpGetMethod(url);
        JSONObject jsonObject = JSON.parseObject(str);
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        //处理信息
        for (Object o : jsonArray) {
            ImgInfo img = new ImgInfo();
            // UUID
            img.setUuid(stringUtil.getUUID());
            img.setDate(Date.valueOf(stringUtil.getDate()));
            JSONObject imgInfo = JSON.parseObject(o.toString());
            img.setId(imgInfo.getString("id"));
            img.setTitle(imgInfo.getString("title"));
            // imgUrl
            JSONArray imageUrls = imgInfo.getJSONArray("imageUrls");
            JSONObject imgObj = imageUrls.getJSONObject(0);
            String large = imgObj.getString("large");
            large = large.replace("i.pximg.net", "i.pixiv.cat");
            img.setImageUrl(large);
            StringBuffer tagInfo = new StringBuffer();
            // tag 获取
            JSONArray tags = imgInfo.getJSONArray("tags");
            for (Object tag : tags) {
                tagInfo.append(JSON.parseObject(tag.toString()).getString("translatedName") + " ");
            }
            img.setTags(tagInfo.toString());
            imgInfos.add(img);
        }
        for (ImgInfo imgInfo : imgInfos) {
        }
        return imgInfos;
    }

}
