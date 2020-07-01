package com.handcraft.features.pixiv;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.handcraft.pojo.ImgInfo;
import com.handcraft.util.MsgCreate;
import com.handcraft.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * 涩图获取以及P站日图
 * 接口来源于:https://api.lolicon.app
 *
 * @author Heilant Gong
 * <p> 等待重写 代码过于复杂，已经到了无法修改的地步
 * {@link this#getSeTu(String, String, int)} 根据关键字 获取一张涩图
 * {@link this#getDay()} 获取当日日图
 */
@Component
public class PixivMsg {

    @Resource
    StringUtil stringUtil;
    @Resource
    MsgCreate msgCreate;
    @Resource
    ImgInfo imgInfo;

    /**
     * @param key     网站密钥
     * @param keyword 涩图关键字
     * @param r18     是否为R18
     * @return 图片基本信息
     */
    public ImgInfo getSeTu(String key, String keyword, int r18) {
        String url = "https://api.lolicon.app/setu/?apikey=" + key + "&r18=" + r18 + "&size1200=true&keyword=" + keyword;
        //处理信息
        // 第一次取值
        JSONObject obj = JSONObject.parseObject(msgCreate.okHttpGetMethod(url));
        // 如果没有找到则返回空值重新调用
        String code = obj.getString("code");
        if (code.equals("404")) {
            imgInfo.setId("0");
            return imgInfo;
        }
        String data = obj.getString("data");
        // 第二次取值
        String substring = data.substring(1, data.length() - 1);
        JSONObject jsonObject = JSONObject.parseObject(substring);
        String imgUrl = jsonObject.getString("url");
        imgUrl = imgUrl.replace("i.pixiv.cat", "www.pixivdl.net");
        imgInfo.setImageUrl(imgUrl);
        imgInfo.setUuid(stringUtil.getUUID());
        imgInfo.setId(jsonObject.getString("pid"));
        imgInfo.setTitle(jsonObject.getString("title"));
        imgInfo.setFormat(imgUrl.substring(StringUtils.ordinalIndexOf(imgUrl, ".", 3)));
        JSONArray tags = jsonObject.getJSONArray("tags");
        StringBuffer tagInfo = new StringBuffer();
        int i = 0;
        for (Object tag : tags) {
            tagInfo.append(tag.toString() + " ");
            i++;
            if (i == 3) {
                break;
            }
        }
        imgInfo.setTags(tagInfo.toString());
        imgInfo.setDate(Date.valueOf("2000-01-01"));
        return imgInfo;
    }


    /**
     * @return 日图List(十张图片
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
            img.setFormat(large.substring(StringUtils.ordinalIndexOf(large, ".", 3)));
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
