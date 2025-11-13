package com.clz.web.goods.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clz.web.goods.entity.Goods;
import com.clz.web.goods.entity.GoodsParam;
import com.clz.web.goods.mapper.GoodsMapper;
import com.clz.web.goods.service.GoodsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {
    @Resource
    private GoodsMapper mapper;
    @Resource
    StringRedisTemplate stringRedisTemplate;
    @Override
    public Page<Goods> selectGoodsPage(GoodsParam param) {
        Map<String, Object> mapParam = new HashMap<>();
        if(StringUtils.isNotEmpty(param.getName())){
            mapParam.put("name", param.getName());
        }
        Long pageSize = param.getPageSize();
        Long lastId = (param.getCurrentPage() - 1) * param.getPageSize();
        mapParam.put("pageSize", pageSize);
        mapParam.put("lastId", lastId);
        List<Goods> goods = mapper.selectGoodsPage(mapParam);
        Long count = null;
        String goodsCount = stringRedisTemplate.opsForValue().get("goods_count");
        if (StringUtils.isEmpty(goodsCount)) {
            count = mapper.countGoods(mapParam);
            stringRedisTemplate.opsForValue().set("goods_count", String.valueOf(count));
        }else {
            count = Long.valueOf(goodsCount);
        }
        Page<Goods> goodsPage = new Page<>();
        goodsPage.setCurrent(param.getCurrentPage());
        goodsPage.setSize(param.getPageSize());
        goodsPage.setTotal(count);
        goodsPage.setRecords(goods);
        return goodsPage;
    }
}
