package com.handcraft.controller;

import com.alibaba.fastjson.JSON;
import com.handcraft.pojo.ClassInfo;
import com.handcraft.service.ClassInfoService;
import com.handcraft.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author HeilantG
 * 课表信息
 */
@Controller
public class ClassInfoController {
    @Resource
    StringUtil stringUtil;

    @Resource
    ClassInfoService classInfoService;

    @RequestMapping(value = "/select/ClassInfo")
    @ResponseBody
    public String select() {
        return JSON.toJSONString(classInfoService.queryAllClass());
    }

    @RequestMapping(value = "/delete/ClassInfo")
    @ResponseBody
    public String delete(String uuid) {
        int i = classInfoService.deleteClassByUuid(uuid);
        if (i == 1) {
            return "success";
        }
        return "error";
    }

    @RequestMapping(value = "/insert/ClassInfo")
    public String insert(ClassInfo classInfo) {
        classInfo.setUuid(stringUtil.getUUID());
        // System.out.println(classInfo.toString());
        classInfoService.insertClassInfo(classInfo);
        return "redirect:/page/class_table.html";
    }
}
