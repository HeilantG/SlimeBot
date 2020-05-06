package com.handcraft.features.pixiv;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.handcraft.pojo.ImgInfo;
import com.handcraft.util.MsgCreate;
import com.handcraft.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Component
public class PixivMsg {

    @Autowired
    StringUtil stringUtil;

    @Resource
    MsgCreate msgCreate;

    // 获取涩图
    public String getSeTu(String key, int r18) {
        String url = "https://api.lolicon.app/setu/?apikey=" + key + "&r18=" + r18 + "&size1200=true";
        //处理信息
        // 第一次取值
        JSONObject obj = JSONObject.parseObject(msgCreate.okHttpGetMethod(url));
        String code = obj.getString("data");
        // 第二次取值
        String substring = code.substring(1, code.length() - 1);
        JSONObject jsonObject = JSONObject.parseObject(substring);
        String imgUrl = jsonObject.getString("url");
        imgUrl = imgUrl.replace("i.pixiv.cat", "www.pixivdl.net");
        return imgUrl;
    }

    /**
     * 获取日图
     */
    public List<ImgInfo> getDay() {
        List<ImgInfo> imgInfos = new ArrayList<>();
        //获取三天前日期 拼接进字符串
        String date = stringUtil.formatDate(stringUtil.getDesignatedDate(-3));
        String url = "https://api.pixivic.com/ranks?page=1&date=" + date + "&mode=day&pageSize=10";
        String str = msgCreate.okHttpGetMethod(url);
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
            String large = imgObj.getString("original");
            large = large.replace("i.pximg.net", "www.pixivdl.net");
            img.setImageUrl(large);
            StringBuffer tagInfo = new StringBuffer();
            // tag 获取
            JSONArray tags = imgInfo.getJSONArray("tags");
            int i = 0;
            for (Object tag : tags) {
                tagInfo.append(JSON.parseObject(tag.toString()).getString("translatedName") + " ");
                i++;
                if (i == 3) {
                    break;
                }
            }
            img.setTags(tagInfo.toString());
            imgInfos.add(img);
        }
        for (ImgInfo imgInfo : imgInfos) {
        }
        return imgInfos;
    }

}
