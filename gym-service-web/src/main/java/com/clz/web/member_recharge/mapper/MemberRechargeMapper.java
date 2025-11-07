package com.clz.web.member_recharge.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.clz.web.member_recharge.entity.MemberRecharge;
import org.apache.ibatis.annotations.Param;

public interface MemberRechargeMapper extends BaseMapper<MemberRecharge> {
    //得到
    public IPage<MemberRecharge> getRechargeList(IPage<MemberRecharge> page);

    //根据用户id查询
    IPage<MemberRecharge> getRechargeByMember(IPage<MemberRecharge> page, @Param("memberId") Long memberId);
}
