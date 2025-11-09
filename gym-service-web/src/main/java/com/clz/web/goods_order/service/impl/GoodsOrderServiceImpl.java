package com.clz.web.goods_order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clz.web.goods_order.entity.GoodsOrder;
import com.clz.web.goods_order.mapper.GoodsOrderMapper;
import com.clz.web.goods_order.service.GoodsOrderService;
import com.clz.web.home.entity.EchartItem;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class GoodsOrderServiceImpl extends ServiceImpl<GoodsOrderMapper, GoodsOrder> implements GoodsOrderService {
    @Override
    public List<EchartItem> hotGoods() {
        return baseMapper.hotGoods();
    }

    @Override
    public List<EchartItem> hotCards() {
        return baseMapper.hotCards();
    }

    @Override
    public List<EchartItem> hotCourse() {
        return baseMapper.hotCourse();
    }
}
