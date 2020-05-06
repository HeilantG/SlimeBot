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
 * 这是挥手的专用监听器
 * groupId = "361081715";
 */
@Component
public class ListenerForHuiShou {

    public CQCodeUtil cqCodeUtil = CQCodeUtil.build();
    @Resource
    MsgCreate msgCreate;
    @Resource
    ImgMapper imgMapper;
    @Resource
    StringUtil stringUtil;

    /**
     * 今日p站日榜单
     */

    @Listen(MsgGetTypes.groupMsg)
    @Filter(value = {"显示今日"}, group = "361081715", code = "1310147115")
    public void img(GroupMsg msg, MsgSender sender) {
        List<ImgInfo> imgInfos = imgMapper.queryImgListByDate(stringUtil.getDate());
        sender.SENDER.sendGroupMsg(msg, "今日(其实是前天)P站日榜前十选手:");
        for (ImgInfo imgInfo : imgInfos) {
            CQCode cqCode_image = cqCodeUtil.getCQCode_Image(imgInfo.getImageUrl());
            try {
                StringBuffer str = new StringBuffer();
                //写入图片CQ码
                str.append(cqCode_image.toString() + "\n");
                str.append("标题:" + imgInfo.getTitle() + "\n");
                str.append("P站ID:" + imgInfo.getId() + "\n");
                str.append("tag:" + imgInfo.getTags());
                sender.SENDER.sendGroupMsg(msg, str.toString());
            } catch (Exception e) {
                continue;
            }
        }
    }


}
