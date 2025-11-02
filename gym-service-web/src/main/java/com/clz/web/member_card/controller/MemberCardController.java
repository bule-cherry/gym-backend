package com.clz.web.member_card.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clz.utils.ResultUtils;
import com.clz.utils.ResultVo;
import com.clz.web.member_card.entity.ListCard;
import com.clz.web.member_card.entity.MemberCard;
import com.clz.web.member_card.service.MemberCardService;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("api/memberCard")
public class MemberCardController {
    @Resource
    private MemberCardService service;

    @PostMapping
    public ResultVo add(@RequestBody MemberCard memberCard) {
        if(service.save(memberCard)) {
            return ResultUtils.success("添加成功");
        }else{
            return ResultUtils.error("添加失败");
        }
    }
    @DeleteMapping("/{cardId}")
    public ResultVo delete(@PathVariable("cardId") Long cardId) {
        if(service.removeById(cardId)){
            return ResultUtils.success("删除成功");
        }else{
            return ResultUtils.error("删除失败");
        }
    }
    @PutMapping
    public ResultVo edit(@RequestBody MemberCard memberCard) {
        if(service.updateById(memberCard)) {
            return ResultUtils.success("修改成功");
        }else{
            return ResultUtils.error("修改失败");
        }
    }
    @GetMapping("/list")
    public ResultVo list(ListCard query) {
        IPage<MemberCard> page = new Page<>(query.getCurrentPage(), query.getPageSize());
        QueryWrapper<MemberCard> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(query.getTitle())) {
            queryWrapper.lambda().like(MemberCard::getTitle, query.getTitle());
        }
        IPage<MemberCard> list = service.page(page, queryWrapper);
        return ResultUtils.success("查询成功",list);
    }

}
