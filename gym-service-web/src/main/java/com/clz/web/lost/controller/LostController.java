package com.clz.web.lost.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clz.utils.ResultUtils;
import com.clz.utils.ResultVo;
import com.clz.web.lost.entity.Lost;
import com.clz.web.lost.entity.LostParam;
import com.clz.web.lost.service.LostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("api/lost")
@Api("失物招领模块")
public class LostController {
    @Resource
    private LostService service;

    @PostMapping
    @ApiOperation("添加失物招领")
    public ResultVo add(@RequestBody Lost lost) {
        if(service.save(lost)) {
            return ResultUtils.success("添加成功");
        }else{
            return ResultUtils.error("添加失败");
        }
    }
    @PutMapping
    @ApiOperation("修改失物招领")
    public ResultVo edit(@RequestBody Lost lost) {
        if(service.updateById(lost)) {
            return ResultUtils.success("修改成功");
        }else{
            return ResultUtils.error("修改失败");
        }
    }
    @DeleteMapping("/{id}")
    @ApiOperation("删除失物招领")
    public ResultVo delete(@PathVariable("id") Long id) {
        if(service.removeById(id)){
            return ResultUtils.success("删除成功");
        }else{
            return ResultUtils.error("删除失败");
        }
    }

    @GetMapping("/list")
    @ApiOperation("分页查询失物招领")
    public ResultVo list(LostParam param) {
        Page<Lost> page = new Page<>(param.getCurrentPage(), param.getPageSize());
        LambdaQueryWrapper<Lost> query = new LambdaQueryWrapper<Lost>()
                .like(StringUtils.isNotEmpty(param.getLostName()),Lost::getLostName,param.getLostName());
        Page<Lost> list = service.page(page, query);
        return ResultUtils.success("查询成功",list);
    }
}
