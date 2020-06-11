package com.handcraft.service;

import com.handcraft.mapper.ClassInfoMapper;
import com.handcraft.pojo.ClassInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ClassInfoService {
    @Resource
    ClassInfoMapper classInfoMapper;

    public List<ClassInfo> queryAllClass() {
        return classInfoMapper.queryAllClass();
    }

    public int deleteClassByUuid(String classInfo) {
        return classInfoMapper.deleteClassByUuid(classInfo);
    }

    public int insertClassInfo(ClassInfo classInfo) {
        return classInfoMapper.insertClass(classInfo);
    }
}
