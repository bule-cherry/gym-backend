package com.clz.web.sys_role_menu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clz.web.sys_role_menu.entity.RoleMenu;
import com.clz.web.sys_role_menu.entity.SaveMenuParam;
import com.clz.web.sys_role_menu.mapper.RoleMenuMapper;
import com.clz.web.sys_role_menu.service.RoleMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {

    @Override
    @Transactional
    public Boolean saveRoleMenu(SaveMenuParam param) {
        LambdaQueryWrapper<RoleMenu> query = new LambdaQueryWrapper<RoleMenu>().eq(RoleMenu::getRoleId, param.getRoleId());
        baseMapper.delete(query);
        baseMapper.saveRoleMenu(param.getRoleId(),param.getList());
        return null;
    }
}
