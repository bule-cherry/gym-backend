package com.clz.web.sys_user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.clz.utils.ResultUtils;
import com.clz.utils.ResultVo;
import com.clz.web.sys_user.entity.PageParam;
import com.clz.web.sys_user.entity.SysUser;
import com.clz.web.sys_user.service.SysUserService;
import com.clz.web.sys_user_role.entity.SysUserRole;
import com.clz.web.sys_user_role.service.SysUserRoleService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@RestController
@RequestMapping("/api/user")
public class SysUserController {
    @Resource
    private SysUserService userService;

    @PostMapping()
    public ResultVo save(@RequestBody SysUser user) {
        //判断用户名是否存在
        QueryWrapper<SysUser> query = new QueryWrapper<>();
        query.lambda().eq(SysUser::getUsername, user.getUsername());
        SysUser one = userService.getOne(query);
        //如果存在,添加失败,提示"账户已经被占用"
        if(one != null) {
            return ResultUtils.error("账户已经被占用");
        }
        //密码加密,补充IsAdmin=0,CreateTime
        if(StringUtils.isNotEmpty(user.getPassword())) {
            user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        }
        user.setIsAdmin("0");
        user.setCreateTime(LocalDateTime.now());
        //存入数据库
        boolean save = userService.save(user);
        if(save) {
            return ResultUtils.success("保存成功");
        }else{
            return ResultUtils.error("保存失败");
        }
    }

    @DeleteMapping()
    public ResultVo delete(@RequestParam Integer id) {
        boolean remove = userService.removeById(id);
        if(remove) {
            return ResultUtils.success("删除用户成功");
        }else{
            return ResultUtils.error("删除用户失败");
        }
    }

    @PutMapping()
    public ResultVo update(@RequestBody SysUser user) {
        //判断用户名是否存在
        QueryWrapper<SysUser> query = new QueryWrapper<>();
        query.lambda().eq(SysUser::getUsername, user.getUsername());
        SysUser one = userService.getOne(query);
        //如果用户不存在或者用户名和id不一致
        if(one == null || !Objects.equals(one.getUserId(), user.getUserId())) {
            return ResultUtils.error("用户不存在或者用户名和id不一致");
        }
        if(StringUtils.isNotEmpty(user.getPassword())) {
            user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        }
        user.setUpdateTime(LocalDateTime.now());
        boolean update = userService.updateById(user);
        if(update) {
            return ResultUtils.success("编辑用户成功");
        }else{
            return ResultUtils.error("编辑用户失败");
        }
    }

    @GetMapping("list")
    public ResultVo list(PageParam pageParam) {
        IPage<SysUser> list = userService.list(pageParam);
        //密码不显示
        list.getRecords().stream().forEach(item ->{
            item.setPassword("");
        });
        return ResultUtils.success("查询成功",list);
    }

    @Resource
    SysUserRoleService userRoleService;

    @ApiOperation("根据用户id查角色")
    @GetMapping("role")
    public ResultVo getRole(@RequestParam Integer id) {
        QueryWrapper<SysUserRole> query = new QueryWrapper<>();
        query.lambda().eq(SysUserRole::getUserId, id);
        SysUserRole one = userRoleService.getOne(query);
        return ResultUtils.success("查询成功",one);
    }

}
