package com.handcraft.timeTasks;

import com.forte.qqrobot.beans.cqcode.CQCode;
import com.forte.qqrobot.bot.BotManager;
import com.forte.qqrobot.bot.BotSender;
import com.forte.qqrobot.utils.CQCodeUtil;
import com.handcraft.features.pixiv.PixivMsg;
import com.handcraft.pojo.ImgInfo;
import com.handcraft.util.MsgCreate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

// 每早定时获取P站日图

@Component
@EnableScheduling
public class GetPixivDayImg {
    CQCodeUtil cqCodeUtil = CQCodeUtil.build();
    @Resource
    PixivMsg pixivMsg;
    @Resource
    MsgCreate msgCreate;
    /**
     * 自定义送信器
     */
    @Resource
    private BotManager botManager;

    @Scheduled(cron = "0 0 8 * * ?")
    public void dayImg() {
        List<ImgInfo> day = pixivMsg.getDay();
        //存入数据库
        for (int i = 0; i < day.size(); i++) {
            System.out.println(day.get(i));
        }
        //预下载
        for (ImgInfo imgInfo : day) {
            BotSender sender = botManager.defaultBot().getSender();
            CQCode cqCode_image = cqCodeUtil.getCQCode_Image(imgInfo.getImageUrl());
            sender.SENDER.sendGroupMsg("190375193", cqCode_image.toString() + imgInfo.getId());
        }
    }

}
