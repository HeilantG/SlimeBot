package com.handcraft.listener;

import com.forte.qqrobot.anno.Filter;
import com.forte.qqrobot.anno.template.OnGroup;
import com.forte.qqrobot.beans.cqcode.CQCode;
import com.forte.qqrobot.beans.messages.msgget.GroupMsg;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;
import com.handcraft.pojo.ImgInfo;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

/**
 * 这是测试用监听器
 *
 * @author HeilantG
 */
@Component
@OnGroup
@Log4j2
public class TestListener {


    CQCodeUtil cqCodeUtil = CQCodeUtil.build();

    @Filter(value = {"来图"})
    public void sexImg(GroupMsg msg, MsgSender sender) {

        StringBuffer cqCodeLocal = new StringBuffer("");
        ImgInfo seTu = new ImgInfo();
        seTu.setUuid("590ac7c6e1104865a92a445ebca3a8d0");
        seTu.setFormat(".png");
        log.warn("涩图>>>>>>>>" + seTu.toString());
        try {
            cqCodeLocal.append(cqCodeUtil.getCQCode_Image(System.getProperty("user.dir") + "\\image\\" + seTu.getUuid() + seTu.getFormat()).toString() + "\n");
            cqCodeLocal.append("标题:" + seTu.getTitle() + "\n");
            cqCodeLocal.append("P站ID:" + seTu.getId());
            log.warn("cqCode>>>>>>>>>" + cqCodeLocal.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sender.SENDER.sendGroupMsg(msg, cqCodeLocal.toString());
        }
    }

    @Filter(value = {"提醒"})
    public void remindClass(GroupMsg msg, MsgSender sender) {
        CQCode cqCode_image = cqCodeUtil.getCQCode_Image(System.getProperty("user.dir") + "\\image\\TimeTasks\\RemindClass.png");
        sender.SENDER.sendGroupMsg(msg, cqCode_image.toString());
    }


}
