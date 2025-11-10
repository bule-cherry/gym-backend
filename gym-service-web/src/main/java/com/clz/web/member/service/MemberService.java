package com.clz.web.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.clz.web.member.entity.JoinParam;
import com.clz.web.member.entity.Member;
import com.clz.web.member.entity.RechargeParam;
import com.clz.web.member_role.entity.MemberRole;

import java.text.ParseException;

public interface MemberService extends IService<Member> {

    public Boolean add( Member member);

    public Boolean delete(Long memberId);

    public Boolean edit(Member member);

    public MemberRole getRoleByMemberId(Long memberId);

    void joinApply(JoinParam joinParam) throws ParseException;

    void recharge(RechargeParam param);
    //根据用户名, 去查询用户
    public Member loadUser(String username);
}
