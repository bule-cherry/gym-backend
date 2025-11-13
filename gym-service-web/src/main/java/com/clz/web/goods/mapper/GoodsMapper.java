package com.clz.web.goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.clz.web.goods.entity.Goods;

import java.util.List;
import java.util.Map;

public interface GoodsMapper extends BaseMapper<Goods> {
    List<Goods> selectGoodsPage(Map<String, Object> params);

    Long countGoods(Map<String, Object> params);
}