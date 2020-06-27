package com.handcraft.features.qrCode;

import org.springframework.stereotype.Component;

/**
 * @author Heilant Gong
 * 创建QrCode
 */
@Component
public class qrCode {

    private String webStr = "https://tool.oschina.net/action/qrcode/generate?data=";

    public String getQrCode(String str) {
        webStr += str;
        return webStr;
    }

}
