package com.clz.web.sys_menu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.clz.web.sys_menu.entity.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysMenuMapper extends BaseMapper<SysMenu> {
    //根据员工id查询菜单
    List<SysMenu> getMenuByUserId(@Param("id") Long id);
    //根据会员id查询菜单
    List<SysMenu> getMenuByMemberId(@Param("id") Long id);
    //根据角色id查询菜单
    List<SysMenu> getMenuByRoleId(@Param("id") Long id);
}
