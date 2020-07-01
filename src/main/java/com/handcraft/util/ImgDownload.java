package com.handcraft.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * 图片下载工具类
 *
 * @author HeilantG
 * {@link this#download(String, String, String)} 图片下载主方法
 * {@link this#biliDownload(String, String, String)} 分享图片下载
 */
@Component
public class ImgDownload {
    @Resource
    StringUtil stringUtil;

    /**
     * 图片下载
     *
     * @param imgUrl 图片地址
     * @param path   本地路径
     * @param uuid   图片名
     * @return 图片信息
     * @author 群主今天发女装自拍了吗的ctrl c+V
     */
    public String download(String imgUrl, String path, String uuid) {
        if (path == null) {
            path = System.getProperty("user.dir") + "\\image\\";
        }
        String localUrl = "";
        String imgFormat = "";
        try {
            URL url = new URL(imgUrl);
            URLConnection connection = new URL(imgUrl).openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            InputStream inputStream = connection.getInputStream();
            //String b = DigestUtils.md5Hex(inputStream).toUpperCase();
            //打开网络输入流
            DataInputStream dis = new DataInputStream(url.openStream());
            //获取图片格式
            imgFormat = imgUrl.substring(StringUtils.ordinalIndexOf(imgUrl, ".", 3));
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
        return uuid + imgFormat;
    }


    /**
     * 分享图片下载
     * 等待重构
     *
     * @param imgUrl 图片地址
     * @param path   本地路径
     * @param uuid   图片名
     * @return 图片信息
     */
    public String biliDownload(String imgUrl, String path, String uuid) {
        if (path == null) {
            path = System.getProperty("user.dir") + "\\image\\";
        }
        String localUrl = "";
        String imgFormat = ".jpg";
        try {
            URL url = new URL("http://" + imgUrl);
            URLConnection connection = new URL("http://" + imgUrl).openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            InputStream inputStream = connection.getInputStream();
            //jcq图片文件本身就是md5码 但是方便存贮 我使用uuid
            //String b = DigestUtils.md5Hex(inputStream).toUpperCase();
            //打开网络输入流
            DataInputStream dis = new DataInputStream(url.openStream());
            //获取图片格式
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
        System.out.println(uuid + imgFormat);
        return uuid + imgFormat;
    }

}

