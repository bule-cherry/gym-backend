package com.clz.web.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clz.web.member.entity.JoinParam;
import com.clz.web.member.entity.Member;
import com.clz.web.member.entity.RechargeParam;
import com.clz.web.member.mapper.MemberMapper;
import com.clz.web.member.service.MemberService;
import com.clz.web.member_apply.entity.MemberApply;
import com.clz.web.member_apply.mapper.MemberApplyMapper;
import com.clz.web.member_apply.service.MemberApplyService;
import com.clz.web.member_card.entity.MemberCard;
import com.clz.web.member_card.mapper.MemberCardMapper;
import com.clz.web.member_card.service.MemberCardService;
import com.clz.web.member_recharge.entity.MemberRecharge;
import com.clz.web.member_recharge.service.MemberRechargeService;
import com.clz.web.member_role.entity.MemberRole;
import com.clz.web.member_role.service.MemberRoleService;
import com.clz.web.sys_user.entity.SysUser;
import com.clz.web.sys_user.service.SysUserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {
    @Resource
    private MemberRoleService memberRoleService;
    @Resource
    private MemberMapper memberMapper;

    @Override
    @Transactional
    public Boolean add(Member member) {
        LambdaQueryWrapper<Member> queryWrapper = new LambdaQueryWrapper<Member>().eq(Member::getUsername, member.getUsername());
        Member one = baseMapper.selectOne(queryWrapper);
        // 用户名重复
        if (one != null) {
            return false;
        }
        //给密码进行一个md5加密
        member.setPassword(DigestUtils.md5Hex(member.getPassword()));
        int i = baseMapper.insert(member);
        if (i > 0) {
            memberRoleService.save(new MemberRole(null,member.getMemberId(),member.getRoleId()));
        }
        return true;
    }

    @Override
    @Transactional
    public Boolean delete(Long memberId) {
        baseMapper.deleteById(memberId);
        LambdaQueryWrapper<MemberRole> queryWrapper = new LambdaQueryWrapper<MemberRole>().eq(MemberRole::getMemberId, memberId);
        memberRoleService.remove(queryWrapper);
        return true;
    }

    @Override
    @Transactional
    public Boolean edit(Member member) {
        LambdaQueryWrapper<Member> queryWrapper = new LambdaQueryWrapper<Member>().eq(Member::getUsername, member.getUsername());
        Member one = baseMapper.selectOne(queryWrapper);
        // 根据用户名查询出来的账号和传入不一致
        if (one != null && !one.getMemberId().equals(member.getMemberId())) {
            return false;
        }
        //给密码进行一个md5加密
        member.setPassword(DigestUtils.md5Hex(member.getPassword()));
        baseMapper.updateById(member);
        LambdaUpdateWrapper<MemberRole> query = new LambdaUpdateWrapper<MemberRole>()
                .eq(MemberRole::getMemberId, member.getMemberId())
                .set(MemberRole::getRoleId, member.getRoleId());
        memberRoleService.update(query);
        return true;
    }

    @Override
    public MemberRole getRoleByMemberId(Long memberId) {
        LambdaQueryWrapper<MemberRole> query = new LambdaQueryWrapper<MemberRole>().eq(MemberRole::getMemberId, memberId);
        return memberRoleService.getOne(query);
    }

    @Resource
    MemberApplyService memberApplyService;
    @Resource
    MemberCardService memberCardService;
    @Resource
    MemberRechargeService memberRechargeService;

    @Override
    @Transactional
    public void joinApply(JoinParam param) throws ParseException {
        MemberCard card = memberCardService.getById(param.getCardId());
        Member member = baseMapper.selectById(param.getMemberId());
        member.setCardType(card.getTitle());
        member.setCardDay(card.getCardDay());
        member.setPrice(card.getPrice());
        //设置会员的日期
        String endTime = member.getEndTime();
        Calendar calendar = Calendar.getInstance();
        Date date = null;
        if(StringUtils.isNotEmpty(endTime)) {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(endTime);
        }else{
            date = new Date();
        }
        calendar.setTime(date);
        calendar.add(Calendar.DATE, card.getCardDay());
        member.setEndTime(new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()));
        // 更新会员
        baseMapper.updateById(member);
        //把更新记录插入办卡表
        MemberApply memberApply = new MemberApply(
                null,member.getMemberId(),card.getCardType(),card.getCardDay(),
                card.getPrice(),new Date(),"创建人暂无");
        memberApplyService.save(memberApply);
    }

    @Resource
    SysUserService sysUserService;

    @Override
    @Transactional
    public void recharge(RechargeParam param) {
        MemberRecharge memberRecharge = new MemberRecharge(null,param.getMemberId(),param.getMoney(), new Date(), null);
        boolean save = memberRechargeService.save(memberRecharge);
        if(save){
            memberMapper.addMoney(param);
        }

    }
}
