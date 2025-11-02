package com.clz.web.sys_menu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.clz.web.sys_menu.entity.SysMenu;

import java.util.List;

public interface SysMenuService extends IService<SysMenu> {
    public List<SysMenu> getParent();
}
