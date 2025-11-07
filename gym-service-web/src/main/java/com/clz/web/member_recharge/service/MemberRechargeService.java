package com.clz.web.member_recharge.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.clz.web.member_recharge.entity.MemberRecharge;
import com.clz.web.member_recharge.entity.RechargeParamList;

public interface MemberRechargeService extends IService<MemberRecharge> {
    public IPage<MemberRecharge> getRechargeList(RechargeParamList param);

    IPage<MemberRecharge> getRechargeByMember(RechargeParamList param);
}
