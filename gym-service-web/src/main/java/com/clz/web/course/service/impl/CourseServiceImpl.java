package com.clz.web.course.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clz.web.course.entity.Course;
import com.clz.web.course.mapper.CourseMapper;
import com.clz.web.course.service.CourseService;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {
}
