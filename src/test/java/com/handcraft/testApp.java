package com.handcraft;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class testApp {

    @Test
    public void all() {
        System.out.println(System.getProperty("user.dir"));
    }

    @Test
    public void formart(){
        String shareJson = "{\"app\":\"com.tencent.miniapp_01\",\"desc\":\"哔哩哔哩\",\"view\":\"view_8C8E89B49BE609866298ADDFF2DBABA4\",\"ver\":\"1.0.0.19\",\"prompt\":\"[QQ小程序]哔哩哔哩\",\"meta\":{\"detail_1\":{\"appid\":\"1109937557\",\"desc\":\"纯素减肥套餐！射射兄弟，已从160斤减到100公斤。【致命高雅14】\",\"host\":{\"nick\":\"雪が落ちていた\",\"uin\":1875278408},\"icon\":\"http:\\/\\/miniapp.gtimg.cn\\/public\\/appicon\\/432b76be3a548fc128acaa6c1ec90131_200.jpg\",\"preview\":\"pubminishare-30161.picsz.qpic.cn\\/9b4d4d98-c377-4b68-a17c-10d6723ac9b4\",\"qqdocurl\":\"https:\\/\\/b23.tv\\/LumREJ?share_medium=android&share_source=qq&bbid=54423052-CA6C-4676-99D2-84301A66387759245infoc&ts=1594739589049\",\"scene\":1036,\"shareTemplateData\":{},\"shareTemplateId\":\"8C8E89B49BE609866298ADDFF2DBABA4\",\"title\":\"哔哩哔哩\",\"url\":\"m.q.qq.com\\/a\\/s\\/1c26a6dc0b16082abc44ad7d324d6010\"}},\"config\":{\"autoSize\":0,\"ctime\":1594739600,\"forward\":1,\"height\":0,\"token\":\"a4b8b1f2b45fea0d4175c28ddcff4a57\",\"type\":\"normal\",\"width\":0}}收到[[QQ小程序]哔哩哔哩]消息，请升级TIM版本查看\n";
        shareJson = shareJson.substring(0, shareJson.indexOf("收到"));
        System.out.println("shareJson=====>" + shareJson);
        JSONObject jsonObject = JSON.parseObject(shareJson);
        System.out.println("jsonOBJ====>" + jsonObject);
    }

}
