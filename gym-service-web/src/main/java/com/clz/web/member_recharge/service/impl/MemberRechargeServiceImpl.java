package com.clz.web.member_recharge.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clz.web.member_apply.service.MemberApplyService;
import com.clz.web.member_recharge.entity.MemberRecharge;
import com.clz.web.member_recharge.mapper.MemberRechargeMapper;
import com.clz.web.member_recharge.service.MemberRechargeService;
import org.springframework.stereotype.Service;

@Service
public class MemberRechargeServiceImpl extends ServiceImpl<MemberRechargeMapper, MemberRecharge> implements MemberRechargeService {
}
