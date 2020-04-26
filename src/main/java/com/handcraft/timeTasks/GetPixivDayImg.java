package com.handcraft.timeTasks;

import com.forte.qqrobot.anno.timetask.CronTask;
import com.forte.qqrobot.beans.cqcode.CQCode;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.timetask.TimeJob;
import com.forte.qqrobot.utils.CQCodeUtil;
import com.handcraft.Mapper.ImgMapper;
import com.handcraft.features.pixiv.PixivMsg;
import com.handcraft.pojo.ImgInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

// 每早定时获取P站日图

@Component
@CronTask("0 0 6 ? * * *")
public class GetPixivDayImg implements TimeJob {
    CQCodeUtil cqCodeUtil = CQCodeUtil.build();
    @Autowired
    PixivMsg pixivMsg;
    @Autowired
    ImgMapper imgMapper;

    @Override
    public void execute(MsgSender msgSender, CQCodeUtil cqCodeUtil) {
        List<ImgInfo> day = pixivMsg.getDay();
        //存入数据库
        for (int i = 0; i < day.size(); i++) {
            System.out.println(day.get(i));
        }
        //预下载
        for (ImgInfo imgInfo : day) {
            CQCode cqCode_image = cqCodeUtil.getCQCode_Image(imgInfo.getImageUrl());
            msgSender.SENDER.sendGroupMsg("190375193", cqCode_image.toString() + imgInfo.getId());
        }
    }
}
