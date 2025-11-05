package com.clz.web.suggest.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clz.utils.ResultUtils;
import com.clz.utils.ResultVo;
import com.clz.web.suggest.entity.Suggest;
import com.clz.web.suggest.entity.SuggestParam;
import com.clz.web.suggest.service.SuggestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

@RestController
@RequestMapping("api/suggest")
@Api("反馈模块")
public class SuggestController {
    @Resource
    private SuggestService service;

    @PostMapping
    @ApiOperation("添加反馈")
    public ResultVo add(@RequestBody Suggest suggest) {
        suggest.setDateTime(new Date());
        if(service.save(suggest)) {
            return ResultUtils.success("添加成功");
        }else{
            return ResultUtils.error("添加失败");
        }
    }
    @PutMapping
    @ApiOperation("修改反馈")
    public ResultVo edit(@RequestBody Suggest suggest) {
        if(service.updateById(suggest)) {
            return ResultUtils.success("修改成功");
        }else{
            return ResultUtils.error("修改失败");
        }
    }
    @DeleteMapping("/{id}")
    @ApiOperation("删除反馈")
    public ResultVo delete(@PathVariable("id") Long id) {
        if(service.removeById(id)){
            return ResultUtils.success("删除成功");
        }else{
            return ResultUtils.error("删除失败");
        }
    }

    @GetMapping("/list")
    @ApiOperation("分页查询反馈")
    public ResultVo list(SuggestParam param) {
        Page<Suggest> page = new Page<>(param.getCurrentPage(), param.getPageSize());
        LambdaQueryWrapper<Suggest> query = new LambdaQueryWrapper<Suggest>()
                .like(StringUtils.isNotEmpty(param.getTitle()),Suggest::getTitle,param.getTitle());
        Page<Suggest> list = service.page(page, query);
        return ResultUtils.success("查询成功",list);
    }
}
