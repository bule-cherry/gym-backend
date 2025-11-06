package com.clz.web.sys_role_menu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.clz.web.sys_role_menu.entity.RoleMenu;
import com.clz.web.sys_role_menu.entity.SaveMenuParam;

public interface RoleMenuService extends IService<RoleMenu> {
    // 更新角色对应的菜单
    public Boolean saveRoleMenu(SaveMenuParam param);
}
