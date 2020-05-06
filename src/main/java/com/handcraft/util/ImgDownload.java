package com.handcraft.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * 图片下载
 *
 * @author HeilantG
 */
@Component
public class ImgDownload {
    @Resource
    StringUtil stringUtil;

    /**
     * @return
     * @author 群主今天发女装自拍了吗的ctrl c+V
     */
    public String download(String imgUrl, String path) {
        String localUrl = "";
        String uuid = stringUtil.getUUID();
        try {
            URL url = new URL(imgUrl);
            URLConnection connection = new URL(imgUrl).openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            InputStream inputStream = connection.getInputStream();
            //jcq图片文件本身就是md5码 但是方便存贮 我使用uuid
            //String b = DigestUtils.md5Hex(inputStream).toUpperCase();
            //打开网络输入流
            DataInputStream dis = new DataInputStream(url.openStream());
            //获取图片格式
            String imgFormat = imgUrl.substring(StringUtils.ordinalIndexOf(imgUrl, ".", 3));
            //建立一个新的文件
            localUrl = path + uuid + imgFormat;
            FileOutputStream fos = new FileOutputStream(new File(localUrl));
            byte[] buffer1 = new byte[1024];
            int length;
            //开始填充数据
            while ((length = dis.read(buffer1)) > 0) {
                fos.write(buffer1, 0, length);
            }
            dis.close();
            fos.close();
        } catch (Exception e) {
        }
        return uuid;
    }

    public void getImg() throws IOException {
        List<String> urlList = new ArrayList<>();
        urlList.add("https://www.pixivdl.net/img-original/img/2020/05/02/00/00/08/81213018_p0.jpg");
        urlList.add("https://www.pixivdl.net/img-original/img/2020/05/02/00/00/13/81213060_p0.png");
        urlList.add("https://www.pixivdl.net/img-original/img/2020/05/02/00/00/11/81213040_p0.png");

        for (String s : urlList) {
            System.out.println(s.substring(StringUtils.ordinalIndexOf(s, ".", 3) + 1));
        }
        int size = 0;
        for (String url : urlList) {
            size++;
            System.out.println(size + ":" + url);
            InputStream uc;
            uc = new URL(url).openConnection().getInputStream();
            FileOutputStream out = new FileOutputStream("C:\\Users\\HeilantG\\Desktop\\酷Q Pro\\data\\image\\" + size + "."
                    + url.substring(StringUtils.ordinalIndexOf(url, ".", 3) + 1));
            int record;
            while ((record = uc.read()) != -1) {
                out.write(record);
            }
            uc.close();
        }
    }

}

