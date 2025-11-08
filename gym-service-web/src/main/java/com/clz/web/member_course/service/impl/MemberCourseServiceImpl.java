package com.clz.web.member_course.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clz.web.course.entity.Course;
import com.clz.web.course.service.CourseService;
import com.clz.web.member.entity.RechargeParam;
import com.clz.web.member.mapper.MemberMapper;
import com.clz.web.member.service.MemberService;
import com.clz.web.member_course.entity.MemberCourse;
import com.clz.web.member_course.mapper.MemberCourseMapper;
import com.clz.web.member_course.service.MemberCourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
@Service
public class MemberCourseServiceImpl extends ServiceImpl<MemberCourseMapper, MemberCourse> implements MemberCourseService {
    @Resource
    CourseService courseService;
    @Resource
    MemberMapper memberMapper;

    @Override
    @Transactional
    public void joinCourse(MemberCourse memberCourse) {
        Course course = courseService.getById(memberCourse.getCourseId());
        BeanUtils.copyProperties(course, memberCourse);
        // 添加报名表
        int insert = baseMapper.insert(memberCourse);
        if(insert > 0){ // 添加成功, 给会员扣钱
            RechargeParam param = new RechargeParam();
            param.setMemberId(memberCourse.getMemberId());
            param.setMoney(memberCourse.getCoursePrice());
            memberMapper.subMoney(param);
        }


    }
}
