package com.handcraft.features.baiduyun;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 百度云支链还原
 *
 * @author Heilant Gong
 * 使用了Jsoup
 * 接口来源于http://pan.naifei.cc
 */
@Component
public class YunGet {
    String linkUrl = "http://pan.naifei.cc/?";

    public Map<String, String> get(String link, String sharePwd) {
        Map<String, String> returnMap = new HashMap<>();
        //处理地址
        StringBuilder linkSplice = new StringBuilder(linkUrl);
        linkSplice.append("share=").append(link);
        linkSplice.append("&pwd=").append(sharePwd);
        Document doc = null;
        try {
            //获取网页html
            doc = Jsoup.connect(linkSplice.toString()).get();
            Elements td = doc.select("td");
            // 文件名
            returnMap.put("name", td.get(0).text());
            // 文件大小
            returnMap.put("size", td.get(1).text());
            Elements a = doc.select("a");
            returnMap.put("link", a.get(0).attr("href"));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return returnMap;
    }
}
