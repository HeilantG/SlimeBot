package com.handcraft.features.pixiv;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @author HeilantG
 * 涩图工具类 接口来自 https://api.lolicon.app/
 * r18 : 0为非 R18，1为 R18，2为混合
 */

public class CreateSetuMsg {

    public static String get(String key, int r18) {
        return format(httpGetMethod(key, r18));
    }

    private static String format(String Json) {
        // 第一次取值
        JSONObject obj = JSONObject.parseObject(Json);
        String code = obj.getString("data");
        // 第二次取值
        String substring = code.substring(1, code.length() - 1);
        JSONObject jsonObject = JSONObject.parseObject(substring);
        return jsonObject.getString("url");
    }


    private static String httpGetMethod(String key, int r18) {
        try {
            CloseableHttpClient httpClient = HttpClients.createSystem();
            // 创建get方法

            HttpGet hg = new HttpGet("https://api.lolicon.app/setu/?apikey=" + key + "&r18=" + r18 + "&size1200=true");

            // 浏览器表示
            hg.addHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.7.6)");
            // 传输的类型
            hg.addHeader("Content-Type", "application/x-www-form-urlencoded");
            // 超时断开(起码不会卡连接池
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(5000).setConnectionRequestTimeout(1000)
                    .setSocketTimeout(5000).build();
            hg.setConfig(requestConfig);
            //执行请求
            CloseableHttpResponse resp = httpClient.execute(hg);
            //获取请求结果的html 实体
            HttpEntity entity = resp.getEntity();
            // 使用EntityUtils toString方法将entity转换为String ，编码为gbk
            String entitString = EntityUtils.toString(entity, "utf-8");
            return entitString;

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
