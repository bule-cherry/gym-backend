package com.clz.web.sys_role.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.clz.utils.ResultUtils;
import com.clz.utils.ResultVo;
import com.clz.web.sys_role.entity.*;
import com.clz.web.sys_role.service.SysRoleService;
import com.clz.web.sys_role_menu.entity.SaveMenuParam;
import com.clz.web.sys_role_menu.service.RoleMenuService;
import com.clz.web.sys_role_menu.service.impl.RoleMenuServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Api("角色控制器")
@RestController
@RequestMapping("/api/role")
public class SysRoleController {

    @Autowired
    private SysRoleService service;

    @PostMapping
    @ApiOperation(value="新增角色")
    @PreAuthorize("hasAnyAuthority('sys:role:add')")
    public ResultVo save(@RequestBody SysRole sysRole) {
        sysRole.setCreateTime(new Date());
        boolean save = service.save(sysRole);
        if(save){
            return ResultUtils.success("新增成功!");
        }else{
            return ResultUtils.error("新增失败!");
        }
    }

    @DeleteMapping("/{roleId}")
    @ApiOperation(value="删除角色")
    public ResultVo delete(@PathVariable("roleId") Integer roleId) {
        boolean b = service.removeById(roleId);
        if(b){
            return ResultUtils.success("删除成功!");
        }else{
            return ResultUtils.error("删除失败!");
        }
    }

    @PutMapping
    @ApiOperation("修改角色")
    public ResultVo update(@RequestBody SysRole sysRole) {
        sysRole.setUpdateTime(new Date());
        boolean b = service.updateById(sysRole);
        if(b){
            return ResultUtils.success("修改成功!");
        }else{
            return ResultUtils.error("修改失败!");
        }
    }

    @GetMapping("/list")
    @ApiOperation("查询角色")
    public ResultVo list(RoleParam param) {
        IPage<SysRole> list = service.list(param);
        return ResultUtils.success("查询成功",list);
    }

    @GetMapping("/getSelect")
    @ApiOperation("查询下拉框可选项")
    public ResultVo getSelect() {
        List<SysRole> list = service.list();
        List<SelectType> selectTypes = new ArrayList<>();
        list.forEach(item ->{
            selectTypes.add(
                    new SelectType(item.getRoleId(), item.getRoleName()));
        });
        return ResultUtils.success("查询成功",selectTypes);
    }

    @GetMapping("/getMenuTree")
    public ResultVo getMenuTree(RoleAssignParam param) {
        return ResultUtils.success("查询成功",service.getMenuTree(param));
    }

    @Resource
    private RoleMenuService roleMenuService;

    @PostMapping("/saveRoleMenu")
    public ResultVo saveRoleMenu(@RequestBody SaveMenuParam param){
        roleMenuService.saveRoleMenu(param);
        return ResultUtils.success("分配成功");
    }


}
