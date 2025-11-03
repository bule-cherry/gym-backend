package com.clz.web.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clz.web.member.entity.Member;
import com.clz.web.member.mapper.MemberMapper;
import com.clz.web.member.service.MemberService;
import com.clz.web.member_role.entity.MemberRole;
import com.clz.web.member_role.service.MemberRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {
    @Resource
    private MemberRoleService memberRoleService;

    @Override
    @Transactional
    public Boolean add(Member member) {
        LambdaQueryWrapper<Member> queryWrapper = new LambdaQueryWrapper<Member>().eq(Member::getUsername, member.getUsername());
        Member one = baseMapper.selectOne(queryWrapper);
        // 用户名重复
        if (one != null) {
            return false;
        }
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
}
