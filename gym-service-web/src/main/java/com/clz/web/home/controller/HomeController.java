package com.clz.web.home.controller;


import com.alibaba.fastjson.JSON;
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
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
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
    @Resource
    StringRedisTemplate stringRedisTemplate;
    //热销商品
    @GetMapping("/getHotGoods")
    public ResultVo getHotGoods() {
        //1. 从redis中查询数据
        String hotGoods = stringRedisTemplate.opsForValue().get("hotGoods");
        //2. 判断是否存在
        if (StringUtils.isNotEmpty(hotGoods)) {
            // 使用 parseObject 明确指定目标类型
            Echart echart = JSON.parseObject(hotGoods, Echart.class);
            // 3.存在直接返回
            return ResultUtils.success("查询成功", echart);
        }
        //4. 不存在查询
        List<EchartItem> echartItems = goodsOrderService.hotGoods();
        Echart echart = new Echart();
        for (int i = 0; i < echartItems.size(); i++) {
            EchartItem item = echartItems.get(i);
            echart.getNames().add(item.getName());
            echart.getValues().add(item.getValue());
        }
        String jsonString = JSON.toJSONString(echart);
        //5.不存在返回错误
        if (StringUtils.isEmpty(jsonString)) {
            return  ResultUtils.error("数据不存在");
        }
        //6. 存在写入redis
        stringRedisTemplate.opsForValue().set("hotGoods", jsonString, Duration.ofMinutes(30));
        //7. 返回
        return ResultUtils.success("查询成功",echart);
        /*List<EchartItem> echartItems = goodsOrderService.hotGoods();
        Echart echart = new Echart();
        for (int i = 0; i < echartItems.size(); i++) {
            EchartItem item = echartItems.get(i);
            echart.getNames().add(item.getName());
            echart.getValues().add(item.getValue());
        }
        return ResultUtils.success("查询成功",echart);*/
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

    @Resource
    PasswordEncoder passwordEncoder;

    @PostMapping("/resetPassword")
    public ResultVo resetPassword( @RequestBody ResetPassword resetPassword) {
        if("1".equals(resetPassword.getUserType())){//会员
            LambdaUpdateWrapper<Member> query = new LambdaUpdateWrapper<Member>()
                    .eq(Member::getMemberId, resetPassword.getUserId()).set(Member::getPassword, passwordEncoder.encode("123456"));
            memberService.update(query);
            return ResultUtils.success("重置密码成功!");
        } else if ("2".equals(resetPassword.getUserType())) {
            LambdaUpdateWrapper<SysUser> query = new LambdaUpdateWrapper<SysUser>()
                    .eq(SysUser::getUserId, resetPassword.getUserId()).set(SysUser::getPassword, passwordEncoder.encode("123456"));
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
            if(!passwordEncoder.matches(resetPassword.getOldPassword(),member.getPassword())){
                return ResultUtils.error("原密码不正确");
            }
            LambdaUpdateWrapper<Member> query = new LambdaUpdateWrapper<Member>()
                    .eq(Member::getMemberId, resetPassword.getUserId()).set(Member::getPassword, passwordEncoder.encode(resetPassword.getPassword()));
            memberService.update(query);
            return ResultUtils.success("重置密码成功!");
        } else if ("2".equals(resetPassword.getUserType())) {
            SysUser user = userService.getById(resetPassword.getUserId());
            if(!passwordEncoder.matches(resetPassword.getOldPassword(),user.getPassword())){
                return ResultUtils.error("原密码不正确");
            }
            LambdaUpdateWrapper<SysUser> query = new LambdaUpdateWrapper<SysUser>()
                    .eq(SysUser::getUserId, resetPassword.getUserId()).set(SysUser::getPassword, passwordEncoder.encode(resetPassword.getPassword()));
            userService.update(query);
            return ResultUtils.success("重置密码成功!");
        }else{
            return ResultUtils.error("用户类型错误");
        }
    }

    @PostMapping("/loginOut")
    public ResultVo loginOut(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return ResultUtils.success("退出登录成功");
    }
}
