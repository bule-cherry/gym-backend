package com.clz.web.goods_order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.clz.web.goods_order.entity.GoodsOrder;
import com.clz.web.home.entity.EchartItem;

import java.util.List;

public interface GoodsOrderMapper extends BaseMapper<GoodsOrder> {
    //热销商品
    List<EchartItem> hotGoods();
    //热销卡
    List<EchartItem> hotCards();
    //热销课程
    List<EchartItem> hotCourse();
}
