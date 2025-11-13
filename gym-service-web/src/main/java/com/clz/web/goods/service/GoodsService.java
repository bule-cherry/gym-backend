package com.clz.web.goods.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.clz.web.goods.entity.Goods;
import com.clz.web.goods.entity.GoodsParam;


public interface GoodsService extends IService<Goods> {
    Page<Goods> selectGoodsPage(GoodsParam param);
}
