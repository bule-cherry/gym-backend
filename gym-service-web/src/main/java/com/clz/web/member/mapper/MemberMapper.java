package com.clz.web.member.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.clz.web.member.entity.Member;
import com.clz.web.member.entity.RechargeParam;
import org.apache.ibatis.annotations.Param;

public interface MemberMapper extends BaseMapper<Member> {
    public void addMoney(@Param("param") RechargeParam param);

    public void subMoney(@Param("param") RechargeParam param);
}
