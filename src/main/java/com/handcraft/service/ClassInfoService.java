package com.handcraft.service;

import com.handcraft.mapper.ClassInfoMapper;
import com.handcraft.pojo.ClassInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 课程信息Service层
 *
 * @author Heilant Gong
 */
@Service
public class ClassInfoService {
    @Resource
    ClassInfoMapper classInfoMapper;

    /**
     * 查询所有课程信息
     *
     * @return 课程信息列表
     */
    public List<ClassInfo> queryAllClass() {
        return classInfoMapper.queryAllClass();
    }

    /**
     * 删除课程
     *
     * @param uuid 课程uuid
     * @return 删除标记
     */
    public int deleteClassByUuid(String uuid) {
        return classInfoMapper.deleteClassByUuid(uuid);
    }

    /**
     * 增加课程
     *
     * @param classInfo 课程信息
     * @return 增加标记
     */
    public int insertClassInfo(ClassInfo classInfo) {
        return classInfoMapper.insertClass(classInfo);
    }
}
