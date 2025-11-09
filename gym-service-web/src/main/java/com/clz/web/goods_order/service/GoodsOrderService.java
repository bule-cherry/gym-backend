package com.clz.web.goods_order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.clz.web.goods_order.entity.GoodsOrder;
import com.clz.web.home.entity.EchartItem;

import java.util.List;

public interface GoodsOrderService extends IService<GoodsOrder> {
    //热销商品
    List<EchartItem> hotGoods();
    //热销卡
    List<EchartItem> hotCards();
    //热销课程
    List<EchartItem> hotCourse();
}
