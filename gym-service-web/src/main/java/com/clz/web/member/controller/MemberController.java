package com.clz.web.member.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clz.utils.ResultUtils;
import com.clz.utils.ResultVo;
import com.clz.web.member.entity.Member;
import com.clz.web.member.entity.PageParam;
import com.clz.web.member.service.MemberService;
import com.clz.web.member_card.entity.ListCard;
import com.clz.web.member_card.entity.MemberCard;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/member")
public class MemberController {
    @Resource
    private MemberService service;

    @PostMapping
    public ResultVo add(@RequestBody Member member) {
        if(service.save(member)) {
            return ResultUtils.success("添加成功");
        }else{
            return ResultUtils.error("添加失败");
        }
    }
    @DeleteMapping("/{memberId}")
    public ResultVo delete(@PathVariable("memberId") Long memberId) {
        if(service.removeById(memberId)){
            return ResultUtils.success("删除成功");
        }else{
            return ResultUtils.error("删除失败");
        }
    }
    @PutMapping
    public ResultVo edit(@RequestBody Member member) {
        if(service.updateById(member)) {
            return ResultUtils.success("修改成功");
        }else{
            return ResultUtils.error("修改失败");
        }
    }
    @GetMapping("/list")
    public ResultVo list(PageParam pageParam) {
        Page<Member> page = new Page<>(pageParam.getCurrentPage(), pageParam.getPageSize());
        LambdaQueryWrapper<Member> query = new LambdaQueryWrapper<Member>()
                .like(StringUtils.isNotEmpty(pageParam.getName()),Member::getName,pageParam.getName())
                .like(StringUtils.isNotEmpty(pageParam.getPhone()),Member::getPhone,pageParam.getPhone())
                .like(StringUtils.isNotEmpty(pageParam.getUsername()),Member::getUsername,pageParam.getUsername());
        Page<Member> list = service.page(page, query);
        return ResultUtils.success("查询成功",list);
    }
}
