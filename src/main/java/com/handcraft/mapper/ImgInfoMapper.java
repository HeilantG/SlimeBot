package com.handcraft.mapper;

import com.handcraft.pojo.ImgInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 图片信息操作层
 *
 * @author Heilant Gong
 */
@Mapper
@Repository
public interface ImgInfoMapper {

    /**
     * 查询所有
     *
     * @return 图片List
     */
    List<ImgInfo> queryImgList();

    /**
     * 根据日期查询
     *
     * @param date 日期
     * @return 图片List
     */
    List<ImgInfo> queryImgListByDate(String date);


    /**
     * @param imgInfo 图片信息
     * @return 单张图片信息
     */
    int addImg(ImgInfo imgInfo);
}
