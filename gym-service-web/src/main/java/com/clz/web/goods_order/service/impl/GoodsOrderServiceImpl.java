package com.clz.web.goods_order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clz.web.goods_order.entity.GoodsOrder;
import com.clz.web.goods_order.mapper.GoodsOrderMapper;
import com.clz.web.goods_order.service.GoodsOrderService;
import org.springframework.stereotype.Service;

@Service
public class GoodsOrderServiceImpl extends ServiceImpl<GoodsOrderMapper, GoodsOrder> implements GoodsOrderService {
}
