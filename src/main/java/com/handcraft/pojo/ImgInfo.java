package com.handcraft.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Date;

/**
 * 图片信息
 *
 * @author Heilant Gong
 * {@link this#uuid} 唯一标识
 * {@link this#id} P站ID
 * {@link this#title} 标题
 * {@link this#imageUrl} 图片链接
 * {@link this#tags} tags 标签
 * {@link this#date} 上传日期
 * {@link this#format} 图片格式
 */
@Component
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ImgInfo {
    String uuid;
    String id;
    String title;
    String imageUrl;
    String tags;
    Date date;
    String format;
}
