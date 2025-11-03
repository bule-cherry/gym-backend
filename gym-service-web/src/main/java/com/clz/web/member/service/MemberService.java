package com.clz.web.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.clz.utils.ResultUtils;
import com.clz.utils.ResultVo;
import com.clz.web.member.entity.Member;
import com.clz.web.member_role.entity.MemberRole;
import org.springframework.web.bind.annotation.*;

public interface MemberService extends IService<Member> {

    public Boolean add( Member member);

    public Boolean delete(Long memberId);

    public Boolean edit(Member member);

    public MemberRole getRoleByMemberId(Long memberId);
}
