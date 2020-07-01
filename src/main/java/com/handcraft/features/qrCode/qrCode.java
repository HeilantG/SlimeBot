package com.handcraft.features.qrCode;

import org.springframework.stereotype.Component;

/**
 * 创建QrCode
 * 接口来自：https://tool.oschina.net/action/qrcode/generate?data=
 *
 * @author Heilant Gong
 * 未实现
 */
@Component
public class qrCode {

    private String webStr = "https://tool.oschina.net/action/qrcode/generate?data=";

    public String getQrCode(String str) {
        webStr += str;
        return webStr;
    }

}
