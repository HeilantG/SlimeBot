package com.handcraft.listener;

import com.forte.qqrobot.utils.CQCodeUtil;
import com.handcraft.mapper.ImgInfoMapper;
import com.handcraft.util.MsgCreate;
import com.handcraft.util.StringUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * pixiv监听
 *
 * @author HeilantG
 * 暂时无用
 */
@Component
public class PixivListener {

    public CQCodeUtil cqCodeUtil = CQCodeUtil.build();
    @Resource
    MsgCreate msgCreate;
    @Resource
    ImgInfoMapper imgInfoMapper;
    @Resource
    StringUtil stringUtil;


}
