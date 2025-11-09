package com.clz.web.goods_order.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clz.utils.ResultUtils;
import com.clz.utils.ResultVo;
import com.clz.web.goods.entity.Goods;
import com.clz.web.goods.entity.GoodsParam;
import com.clz.web.goods.service.GoodsService;
import com.clz.web.goods_order.entity.GoodsOrder;
import com.clz.web.goods_order.entity.OrderItem;
import com.clz.web.goods_order.entity.OrderParam;
import com.clz.web.goods_order.service.GoodsOrderService;
import com.clz.web.sys_user.entity.SysUser;
import com.clz.web.sys_user.service.SysUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/order")
public class GoodsOrderController {
    @Resource
    SysUserService sysUserService;
    @Resource
    GoodsOrderService goodsOrderService;
    @Resource
    GoodsService goodsService;

    //下单
    @PostMapping("/down")
    public ResultVo down(@RequestBody OrderParam orderParam) {
        SysUser user = sysUserService.getById(orderParam.getUserId());
        List<OrderItem> orderList = orderParam.getOrderList();
        List<GoodsOrder> list = new ArrayList<GoodsOrder>();
        for (OrderItem item : orderList) {
            Goods good = goodsService.getById(item.getGoodsId());
            if (good == null) {
                continue;
            }
            GoodsOrder order = new GoodsOrder();
            BeanUtils.copyProperties(good,order);
            order.setNum(item.getNum());
            BigDecimal number = BigDecimal.valueOf(item.getNum());
            BigDecimal price = good.getPrice();
            BigDecimal total = number.multiply(price);
            BigDecimal totalPrice = total.setScale(2, BigDecimal.ROUND_HALF_UP);
            order.setTotalPrice(totalPrice);
            order.setControlUser(user.getNickName());
            list.add(order);
        }
        if(!list.isEmpty()){
            goodsOrderService.saveBatch(list);
        }
        return ResultUtils.success("下单成功");
    }

    @GetMapping("/list")
    public ResultVo list(GoodsParam param) {
        Page<GoodsOrder> page = new Page<>(param.getCurrentPage(), param.getPageSize());
        LambdaQueryWrapper<GoodsOrder> query = new LambdaQueryWrapper<GoodsOrder>().like(StringUtils.isNotEmpty(param.getName()),GoodsOrder::getName, param.getName());
        Page<GoodsOrder> list = goodsOrderService.page(page, query);
        return ResultUtils.success("查询成功",list);
    }
}
