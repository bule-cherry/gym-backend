package com.clz.web.course.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clz.utils.ResultUtils;
import com.clz.utils.ResultVo;
import com.clz.web.course.entity.Course;
import com.clz.web.course.entity.CourseList;
import com.clz.web.course.service.CourseService;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/course")
public class CourseController {
    @Resource
    private CourseService service;

    @PostMapping
    public ResultVo add(@RequestBody Course course) {
        if(service.save(course)) {
            return ResultUtils.success("添加成功");
        }else{
            return ResultUtils.error("添加失败");
        }
    }
    @PutMapping
    public ResultVo edit(@RequestBody Course course) {
        if(service.updateById(course)) {
            return ResultUtils.success("修改成功");
        }else{
            return ResultUtils.error("修改失败");
        }
    }
    @DeleteMapping("/{courseId}")
    public ResultVo delete(@PathVariable("courseId") Long courseId) {
        if(service.removeById(courseId)){
            return ResultUtils.success("删除成功");
        }else{
            return ResultUtils.error("删除失败");
        }
    }

    @GetMapping("/list")
    public ResultVo list(CourseList param) {
        Page<Course> page = new Page<>(param.getCurrentPage(), param.getPageSize());
        LambdaQueryWrapper<Course> query = new LambdaQueryWrapper<Course>()
                .like(StringUtils.isNotEmpty(param.getCourseName()),Course::getCourseName,param.getCourseName())
                .like(StringUtils.isNotEmpty(param.getTeacherName()),Course::getTeacherName,param.getTeacherName());
        Page<Course> list = service.page(page, query);
        return ResultUtils.success("查询成功",list);
    }
}
