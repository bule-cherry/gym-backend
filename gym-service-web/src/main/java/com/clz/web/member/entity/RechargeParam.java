package com.clz.web.member.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RechargeParam {
    private Long userId;
    private Long memberId;
    private BigDecimal money;
}