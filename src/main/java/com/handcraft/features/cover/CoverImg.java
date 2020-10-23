package com.handcraft.features.cover;

import com.handcraft.util.ImgDownload;
import com.handcraft.util.StringUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 获取芦苇的长相
 */
@Component
public class CoverImg {

    private String url = "http://cover.acfunwiki.org/cover.php";
    @Resource
    ImgDownload imgDownload;
    @Resource
    StringUtil stringUtil;


    public String getImg() {
        return imgDownload.download(url, null, stringUtil.getUUID(),".jpg");
    }
}
