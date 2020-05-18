package com.handcraft.listener;

import com.forte.qqrobot.anno.Filter;
import com.forte.qqrobot.anno.Listen;
import com.forte.qqrobot.beans.cqcode.CQCode;
import com.forte.qqrobot.beans.messages.msgget.GroupMsg;
import com.forte.qqrobot.beans.messages.types.MsgGetTypes;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;
import com.handcraft.mapper.ImgMapper;
import com.handcraft.pojo.ImgInfo;
import com.handcraft.util.MsgCreate;
import com.handcraft.util.StringUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author HeilantG
 * groupId = "190375193"
 */
@Component
public class ListenerForTest {
    public CQCodeUtil cqCodeUtil = CQCodeUtil.build();
    @Resource
    MsgCreate msgCreate;
    @Resource
    ImgMapper imgMapper;
    @Resource
    StringUtil stringUtil;

    @Listen(MsgGetTypes.groupMsg)
    @Filter(value = {"显示今日"}, group = "190375193")
    public void img(GroupMsg msg, MsgSender sender) {
        sender.SENDER.sendGroupMsg(msg, "今日(其实是三天前)P站日榜前十选手:");
        List<ImgInfo> imgInfos = imgMapper.queryImgListByDate(stringUtil.getDate());
        for (ImgInfo imgInfo : imgInfos) {
            CQCode cqCode_image = cqCodeUtil.getCQCode_Image(imgInfo.getUuid() + imgInfo.getFormat());
            System.out.println(cqCode_image);
            try {
                StringBuffer str = new StringBuffer();
                //写入图片CQ码
                str.append(cqCode_image.toString() + "\n");
                str.append("标题: " + imgInfo.getTitle() + "\n");
                str.append("P站ID: " + imgInfo.getId() + "\n");
                str.append("tag: " + imgInfo.getTags());
                sender.SENDER.sendGroupMsg(msg, str.toString());
            } catch (Exception ignored) {
            }
        }
    }

    @Listen(MsgGetTypes.groupMsg)
    @Filter(group = {"190375193"}, value = {"hello.*"})
    public void today(GroupMsg groupMsg, MsgSender sender) {
        String msg = msgCreate.getDayMsg();
    }
}
