package com.clz.web.sys_menu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.clz.utils.ResultUtils;
import com.clz.utils.ResultVo;
import com.clz.web.sys_menu.entity.MakeMenuTree;
import com.clz.web.sys_menu.entity.SysMenu;
import com.clz.web.sys_menu.service.SysMenuService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/menu")
public class SysMenuController {
    @Resource
    private SysMenuService service;

    @PostMapping
    public ResultVo add(@RequestBody SysMenu menu) {
        menu.setCreateTime(new Date());
        if(service.save(menu)) {
            return ResultUtils.success("添加成功");
        }else{
            return ResultUtils.error("添加失败");
        }
    }
    @DeleteMapping("/{menuId}")
    public ResultVo delete(@PathVariable("menuId") Long menuId) {
        if(service.removeById(menuId)){
            return ResultUtils.success("删除成功");
        }else{
            return ResultUtils.error("删除失败");
        }
    }
    @PutMapping
    public ResultVo edit(@RequestBody SysMenu menu) {
        menu.setUpdateTime(new Date());
        if(service.updateById(menu)) {
            return ResultUtils.success("修改成功");
        }else{
            return ResultUtils.error("修改失败");
        }
    }
    @GetMapping("/list")
    public ResultVo list() {
        QueryWrapper<SysMenu> query = new QueryWrapper<SysMenu>();
        query.lambda().orderByDesc(SysMenu::getOrderNum);
        List<SysMenu> list = service.list(query);
        List<SysMenu> menus = MakeMenuTree.makeTree(list, 0L);
        return ResultUtils.success("查询成功",menus);
    }

    @GetMapping("/parent")
    public ResultVo parent() {
        List<SysMenu> parent = service.getParent();
        return ResultUtils.success("查询成功",parent);
    }


}
