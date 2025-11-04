package com.clz.web.material.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clz.utils.ResultUtils;
import com.clz.utils.ResultVo;
import com.clz.web.material.entity.ListParam;
import com.clz.web.material.entity.Material;
import com.clz.web.material.service.MaterialService;
import com.clz.web.sys_role.entity.RoleParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

@RestController
@RequestMapping("/api/material")
public class MaterialController {
    @Resource
    private MaterialService service;
    
    @PostMapping
    @ApiOperation(value="新增器材")
    public ResultVo save(@RequestBody Material material) {
        boolean save = service.save(material);
        if(save){
            return ResultUtils.success("新增成功!");
        }else{
            return ResultUtils.error("新增失败!");
        }
    }

    @PutMapping
    @ApiOperation("修改器材")
    public ResultVo update(@RequestBody Material material) {
        boolean b = service.updateById(material);
        if(b){
            return ResultUtils.success("修改成功!");
        }else{
            return ResultUtils.error("修改失败!");
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value="删除器材")
    public ResultVo delete(@PathVariable("id") Long id) {
        if(service.removeById(id)){
            return ResultUtils.success("删除成功!");
        }else{
            return ResultUtils.error("删除失败!");
        }
    }

    @GetMapping("/list")
    @ApiOperation("查询器材")
    public ResultVo list(ListParam param) {
        Page<Material> page = new Page<>(param.getCurrentPage(), param.getPageSize());
        LambdaQueryWrapper<Material> query = new LambdaQueryWrapper<Material>().like(StringUtils.isNotEmpty(param.getName()), Material::getName, param.getName());
        Page<Material> list = service.page(page, query);
        return ResultUtils.success("查询成功",list);
    }
}
