package com.handcraft.timeTasks;

import com.forte.qqrobot.beans.cqcode.CQCode;
import com.forte.qqrobot.bot.BotManager;
import com.forte.qqrobot.bot.BotSender;
import com.forte.qqrobot.utils.CQCodeUtil;
import com.handcraft.features.pixiv.PixivMsg;
import com.handcraft.mapper.ImgInfoMapper;
import com.handcraft.pojo.ImgInfo;
import com.handcraft.util.ImgDownload;
import com.handcraft.util.StringUtil;
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
    ImgInfoMapper imgInfoMapper;
    @Resource
    StringUtil stringUtil;
    /**
     * 自定义送信器
     */
    @Resource
    private BotManager botManager;
    @Resource
    ImgDownload imgDownload;

    @Scheduled(cron = "0 0 11 * * ?")
    public void getDayImg() {
        BotSender sender = botManager.defaultBot().getSender();
        List<ImgInfo> imgInfos = pixivMsg.getDay();
        //预下载
        for (ImgInfo imgInfo : imgInfos) {
            try {
                imgDownload.download(imgInfo.getImageUrl(), "C:\\Users\\Administrator\\Desktop\\酷Q Pro\\data\\image\\", imgInfo.getUuid());
                imgInfoMapper.addImg(imgInfo);
            } catch (Exception ignored) {
            }
        }
    }

    @Scheduled(cron = "0 0 12 * * ?")
    public void sendDayImg() {
        BotSender sender = botManager.defaultBot().getSender();
        sender.SENDER.sendGroupMsg("361081715", "今日P站日榜");
        List<ImgInfo> imgInfos = imgInfoMapper.queryImgListByDate(stringUtil.getDate());
        for (ImgInfo imgInfo : imgInfos) {
            CQCode cqCode_image = cqCodeUtil.getCQCode_Image(imgInfo.getUuid() + imgInfo.getFormat());
            try {
                StringBuffer str = new StringBuffer();
                //写入图片CQ码
                str.append(cqCode_image.toString() + "\n");
                str.append("标题: " + imgInfo.getTitle() + "\n");
                str.append("P站ID: " + imgInfo.getId() + "\n");
                str.append("tag: " + imgInfo.getTags());
                sender.SENDER.sendGroupMsg("361081715", str.toString());
                sender.SENDER.sendGroupMsg("641057857", str.toString());

            } catch (Exception e) {
                continue;
            }
        }
    }

}
