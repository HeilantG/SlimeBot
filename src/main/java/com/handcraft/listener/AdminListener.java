package com.handcraft.listener;

import com.forte.qqrobot.anno.Filter;
import com.forte.qqrobot.anno.Listen;
import com.forte.qqrobot.beans.cqcode.CQCode;
import com.forte.qqrobot.beans.messages.msgget.PrivateMsg;
import com.forte.qqrobot.beans.messages.types.MsgGetTypes;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;
import com.handcraft.features.pixiv.PixivMsg;
import com.handcraft.features.share.ShareFormat;
import com.handcraft.mapper.ImgInfoMapper;
import com.handcraft.pojo.ImgInfo;
import com.handcraft.util.ImgDownload;
import com.handcraft.util.MsgCreate;
import com.handcraft.util.StringUtil;
import com.simplerobot.modules.delay.DelaySender;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 自用私聊测试
 *
 * @author HeilantG
 * {@link this#share(PrivateMsg, MsgSender)} 分享解析
 * {@link this#getImg(PrivateMsg, MsgSender)} 手动获取当日日图
 * {@link this#selectImg(PrivateMsg, MsgSender)} 显示当日日图
 * {@link this#testYs(PrivateMsg, MsgSender)} 延时消息
 */
@Listen(MsgGetTypes.privateMsg)
@Component
public class AdminListener {
    public CQCodeUtil cqCodeUtil = CQCodeUtil.build();
    @Resource
    ImgInfoMapper imgInfoMapper;
    @Resource
    PixivMsg pixivMsg;
    @Resource
    StringUtil stringUtil;
    @Resource
    MsgCreate msgCreate;
    @Resource
    ImgDownload imgDownload;
    @Resource
    ShareFormat shareFormat;

    @Filter(code = {"1310147115"}, value = {".*CQ:rich.*"})
    public void share(PrivateMsg msg, MsgSender sender) {
        List<String> format = shareFormat.format(msg.getMsg());
        StringBuffer sb = new StringBuffer();
        sb.append("title:" + format.get(0) + "\n");
        sb.append("url:" + format.get(1));
        sender.SENDER.sendPrivateMsg(msg, sb.toString());
    }


    @Filter(code = {"1310147115"}, value = {"获取今日"})
    public void getImg(PrivateMsg msg, MsgSender sender) {
        List<ImgInfo> day = pixivMsg.getDay();
        sender.SENDER.sendPrivateMsg(msg, "获取完毕,已保存如数据库,预下载开始");
        for (ImgInfo imgInfo : day) {
            try {
                imgDownload.download(imgInfo.getImageUrl(), null, imgInfo.getUuid());
                imgInfoMapper.addImg(imgInfo);
            } catch (Exception ignored) {

            }
        }
        sender.SENDER.sendPrivateMsg(msg, "图片缓存完毕");
    }


    @Filter(code = {"1310147115"}, value = {"显示今日"})
    public void selectImg(PrivateMsg msg, MsgSender sender) {
        List<ImgInfo> imgInfos = imgInfoMapper.queryImgListByDate(stringUtil.getDate());
        for (ImgInfo imgInfo : imgInfos) {
            CQCode cqCode_image = cqCodeUtil.getCQCode_Image(imgInfo.getUuid() + imgInfo.getFormat());
            try {
                sender.SENDER.sendPrivateMsg(msg, cqCode_image.toString());
            } catch (Exception e) {
                continue;
            }
        }
    }

    //延时消息

    @Filter(code = {"1310147115"}, value = {"延时"})
    public void testYs(PrivateMsg msg, MsgSender sender) {
        DelaySender delaySender = new DelaySender(sender.SENDER, 2);
//        DelaySender delaySender = new DelaySender(sender.SENDER, 2000, TimeUnit.MILLISECONDS); // 使用毫秒
        // 2秒后复读一次
        delaySender.sendPrivateMsg(msg.getQQCode(), "2秒后： " + msg.getMsg());
        // 10秒后再复读一次
        // 重新设置时间, 注意！此处设置的时间格式默认为毫秒，所以需要添加时间格式，或者使用10*1000
        delaySender.setDelayTime(10, TimeUnit.SECONDS);
        // 延时发送
        delaySender.sendPrivateMsg(msg.getQQCode(), "10秒后： " + msg.getMsg());
        // 用原本的sender先直接发送一个消息
        sender.SENDER.sendPrivateMsg(msg.getQQCode(), "直接发送： " + msg.getMsg());
    }


}
