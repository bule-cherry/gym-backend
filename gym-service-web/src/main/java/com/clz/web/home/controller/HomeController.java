package com.clz.web.home.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.clz.utils.ResultUtils;
import com.clz.utils.ResultVo;
import com.clz.web.goods_order.service.GoodsOrderService;
import com.clz.web.home.entity.Echart;
import com.clz.web.home.entity.EchartItem;
import com.clz.web.home.entity.ResetPassword;
import com.clz.web.home.entity.TotalCount;
import com.clz.web.material.service.MaterialService;
import com.clz.web.member.entity.Member;
import com.clz.web.member.service.MemberService;
import com.clz.web.suggest.entity.Suggest;
import com.clz.web.suggest.service.SuggestService;
import com.clz.web.sys_user.entity.SysUser;
import com.clz.web.sys_user.service.SysUserService;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/resetPassword")
    public ResultVo resetPassword( @RequestBody ResetPassword resetPassword) {
        if("1".equals(resetPassword.getUserType())){//会员
            LambdaUpdateWrapper<Member> query = new LambdaUpdateWrapper<Member>()
                    .eq(Member::getMemberId, resetPassword.getUserId()).set(Member::getPassword, DigestUtils.md5DigestAsHex("123456".getBytes()));
            memberService.update(query);
            return ResultUtils.success("重置密码成功!");
        } else if ("2".equals(resetPassword.getUserType())) {
            LambdaUpdateWrapper<SysUser> query = new LambdaUpdateWrapper<SysUser>()
                    .eq(SysUser::getUserId, resetPassword.getUserId()).set(SysUser::getPassword, DigestUtils.md5DigestAsHex("123456".getBytes()));
            userService.update(query);
            return ResultUtils.success("重置密码成功!");
        }else{
            return ResultUtils.error("用户类型错误");
        }
    }
    @PostMapping("/updatePassword")
    public ResultVo updatePassword(@RequestBody ResetPassword resetPassword) {
        if("1".equals(resetPassword.getUserType())){//会员
            Member member = memberService.getById(resetPassword.getUserId());
            if(!member.getPassword().equals(DigestUtils.md5DigestAsHex(resetPassword.getOldPassword().getBytes()))){
                return ResultUtils.error("原密码不正确");
            }
            LambdaUpdateWrapper<Member> query = new LambdaUpdateWrapper<Member>()
                    .eq(Member::getMemberId, resetPassword.getUserId()).set(Member::getPassword, DigestUtils.md5DigestAsHex(resetPassword.getPassword().getBytes()));
            memberService.update(query);
            return ResultUtils.success("重置密码成功!");
        } else if ("2".equals(resetPassword.getUserType())) {
            SysUser user = userService.getById(resetPassword.getUserId());
            if(!user.getPassword().equals(DigestUtils.md5DigestAsHex(resetPassword.getOldPassword().getBytes()))){
                return ResultUtils.error("原密码不正确");
            }
            LambdaUpdateWrapper<SysUser> query = new LambdaUpdateWrapper<SysUser>()
                    .eq(SysUser::getUserId, resetPassword.getUserId()).set(SysUser::getPassword, DigestUtils.md5DigestAsHex(resetPassword.getPassword().getBytes()));
            userService.update(query);
            return ResultUtils.success("重置密码成功!");
        }else{
            return ResultUtils.error("用户类型错误");
        }
    }
}
