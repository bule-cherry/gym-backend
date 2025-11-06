package com.clz.web.sys_menu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.clz.web.sys_menu.entity.SysMenu;

import java.util.List;

public interface SysMenuService extends IService<SysMenu> {
    public List<SysMenu> getParent();

    public List<SysMenu> getMenuByUserId(Long id);

    public List<SysMenu> getMenuByMemberId(Long id);

    public List<SysMenu> getMenuByRoleId(Long id);
}
