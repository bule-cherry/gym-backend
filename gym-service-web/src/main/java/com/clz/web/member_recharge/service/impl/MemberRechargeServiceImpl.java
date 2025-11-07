package com.clz.web.member_recharge.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clz.web.member_apply.service.MemberApplyService;
import com.clz.web.member_recharge.entity.MemberRecharge;
import com.clz.web.member_recharge.entity.RechargeParamList;
import com.clz.web.member_recharge.mapper.MemberRechargeMapper;
import com.clz.web.member_recharge.service.MemberRechargeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MemberRechargeServiceImpl extends ServiceImpl<MemberRechargeMapper, MemberRecharge> implements MemberRechargeService {
    @Resource
    private MemberRechargeMapper mapper;

    @Override
    public IPage<MemberRecharge> getRechargeList(RechargeParamList param) {
        Page<MemberRecharge> page = new Page<>(param.getCurrentPage(), param.getPageSize());
        return mapper.getRechargeList(page);
    }

    @Override
    public IPage<MemberRecharge> getRechargeByMember(RechargeParamList param) {
        Page<MemberRecharge> page = new Page<>(param.getCurrentPage(), param.getPageSize());
        return mapper.getRechargeByMember(page,param.getMemberId());
    }
}
