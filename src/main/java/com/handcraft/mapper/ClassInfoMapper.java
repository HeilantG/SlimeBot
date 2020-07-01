package com.handcraft.mapper;

import com.handcraft.pojo.ClassInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 课程信息操作层
 *
 * @author Heilant Gong
 */
@Mapper
@Repository
public interface ClassInfoMapper {
    /**
     * 查询所有课程信息
     *
     * @return 课程信息列表
     */
    public List<ClassInfo> queryAllClass();

    /**
     * 删除课程
     *
     * @param uuid 课程uuid
     * @return 删除标记
     */
    public int deleteClassByUuid(String uuid);

    /**
     * 增加课程
     *
     * @param classInfo 课程信息
     * @return 增加标记
     */
    public int insertClass(ClassInfo classInfo);
}
