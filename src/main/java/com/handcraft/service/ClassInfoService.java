package com.handcraft.service;

import com.handcraft.mapper.ClassMapper;
import com.handcraft.pojo.ClassInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ClassInfoService {
    @Resource
    ClassMapper classMapper;

    public List<ClassInfo> queryAllClass() {
        return classMapper.queryAllClass();
    }

    public int deleteClassByUuid(String classInfo) {
        return classMapper.deleteClassByUuid(classInfo);
    }

    public int insertClassInfo(ClassInfo classInfo) {
        return classMapper.insertClass(classInfo);
    }
}
