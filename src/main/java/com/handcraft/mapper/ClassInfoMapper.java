package com.handcraft.mapper;

import com.handcraft.pojo.ClassInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ClassInfoMapper {
    public List<ClassInfo> queryAllClass();

    public int deleteClassByUuid(String uuid);

    public int insertClass(ClassInfo classInfo);
}
