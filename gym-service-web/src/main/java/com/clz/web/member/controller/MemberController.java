package com.clz.web.member.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clz.utils.ResultUtils;
import com.clz.utils.ResultVo;
import com.clz.web.member.entity.JoinParam;
import com.clz.web.member.entity.Member;
import com.clz.web.member.entity.PageParam;
import com.clz.web.member.entity.RechargeParam;
import com.clz.web.member.service.MemberService;

import com.clz.web.member_card.entity.MemberCard;
import com.clz.web.member_card.service.MemberCardService;
import com.clz.web.member_recharge.entity.MemberRecharge;
import com.clz.web.member_recharge.entity.RechargeParamList;
import com.clz.web.member_recharge.service.MemberRechargeService;
import com.clz.web.member_role.entity.MemberRole;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api/member")
public class MemberController {
    @Resource
    private MemberService service;

    @PostMapping
    public ResultVo add(@RequestBody Member member) {
        if(service.add(member)) {
            return ResultUtils.success("添加成功");
        }else{
            return ResultUtils.error("会员卡号被占用!");
        }
    }
    @DeleteMapping("/{memberId}")
    public ResultVo delete(@PathVariable("memberId") Long memberId) {
        if(service.delete(memberId)){
            return ResultUtils.success("删除成功");
        }else{
            return ResultUtils.error("删除失败");
        }
    }
    @PutMapping
    public ResultVo edit(@RequestBody Member member) {
        if(service.edit(member)) {
            return ResultUtils.success("修改成功");
        }else{
            return ResultUtils.error("会员卡号被占用!");
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
    @GetMapping("getRoleByMemberId")
    public ResultVo getRoleByMemberId(Long memberId) {
        MemberRole memberRole = service.getRoleByMemberId(memberId);
        if(memberRole != null){
            return ResultUtils.success("查询成功",memberRole);
        }else{
            return ResultUtils.success("查询失败");
        }
    }

    @Resource
    private MemberCardService memberCardService;
    @Resource
    private MemberService memberService;

    @GetMapping("/getCardList")
    public ResultVo getCardList() {
        List<MemberCard> list = memberCardService.list();
        return ResultUtils.success("查询成功",list);
    }

    @PostMapping("/joinApply")
    public ResultVo joinApply(@RequestBody JoinParam param) throws ParseException {
        memberService.joinApply(param);
        return ResultUtils.success("办卡成功");
    }
    @PostMapping("recharge")
    public ResultVo recharge(@RequestBody RechargeParam param) {
        memberService.recharge(param);
        return ResultUtils.success("充值成功");
    }

    @Resource
    MemberRechargeService memberRechargeService;

    @GetMapping("/getMyRecharge")
    public ResultVo getMyRecharge(RechargeParamList param) {
        String type = param.getUserType();
        if("1".equals(type)){//会员
            IPage<MemberRecharge> list = memberRechargeService.getRechargeByMember(param);
            return ResultUtils.success("查询成功",list);
        }else if("2".equals(type)){//员工
            IPage<MemberRecharge> list = memberRechargeService.getRechargeList(param);
            return ResultUtils.success("查询成功",list);
        }else{
            return ResultUtils.error("用户类型不存在!");
        }
    }
}
