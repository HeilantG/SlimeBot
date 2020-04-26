package com.handcraft.Mapper;

import com.handcraft.pojo.ImgInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ImgMapper {
    //查询所有

    List<ImgInfo> queryImgList();
    //根据日期查询

    List<ImgInfo> queryImgListByDate(String date);

    //添加图片

    int addImg(ImgInfo imgInfo);
}
