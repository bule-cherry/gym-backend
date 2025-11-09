package com.clz.web.home.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.clz.utils.ResultUtils;
import com.clz.utils.ResultVo;
import com.clz.web.goods_order.service.GoodsOrderService;
import com.clz.web.home.entity.Echart;
import com.clz.web.home.entity.EchartItem;
import com.clz.web.home.entity.TotalCount;
import com.clz.web.material.service.MaterialService;
import com.clz.web.member.service.MemberService;
import com.clz.web.suggest.entity.Suggest;
import com.clz.web.suggest.service.SuggestService;
import com.clz.web.sys_user.service.SysUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("api/home")
public class HomeController {
    @Resource
    MemberService memberService;
    @Resource
    SysUserService userService;
    @Resource
    MaterialService materialService;
    @Resource
    GoodsOrderService orderService;

    @GetMapping("/getTotal")
    public ResultVo getTotal() {
        int memberCount = memberService.count();
        int userCount = userService.count();
        int materialCount = materialService.count();
        int orderCount = orderService.count();
        TotalCount totalCount = new TotalCount();
        totalCount.setMemberCount(memberCount);
        totalCount.setUserCount(userCount);
        totalCount.setMaterCount(materialCount);
        totalCount.setOrderCount(orderCount);
        return ResultUtils.success("查询成功", totalCount);
    }

    @Resource
    SuggestService suggestService;

    @GetMapping("/getSuggestList")
    public ResultVo getSuggestList() {
        LambdaQueryWrapper<Suggest> query = new LambdaQueryWrapper<Suggest>().orderByDesc(Suggest::getDateTime).last("limit 3");
        List<Suggest> list = suggestService.list(query);
        return ResultUtils.success("查询成功", list);
    }
    @Resource
    GoodsOrderService goodsOrderService;
    //热销商品
    @GetMapping("/getHotGoods")
    public ResultVo getHotGoods() {
        List<EchartItem> echartItems = goodsOrderService.hotGoods();
        Echart echart = new Echart();
        for (int i = 0; i < echartItems.size(); i++) {
            EchartItem item = echartItems.get(i);
            echart.getNames().add(item.getName());
            echart.getValues().add(item.getValue());
        }
        return ResultUtils.success("查询成功",echart);
    }

    //热销卡
    @GetMapping("/getHotCards")
    public ResultVo getHotCards(){
        List<EchartItem> echartItems = goodsOrderService.hotCards();
        return ResultUtils.success("查询成功",echartItems);
    }
    //热销课程
    @GetMapping("/getHotCourse")
    public ResultVo getHotCourse(){
        List<EchartItem> echartItems = goodsOrderService.hotCourse();
        return ResultUtils.success("查询成功",echartItems);
    }
}
