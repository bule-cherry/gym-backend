package com.clz.web.sys_menu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clz.web.sys_menu.entity.MakeMenuTree;
import com.clz.web.sys_menu.entity.SysMenu;
import com.clz.web.sys_menu.mapper.SysMenuMapper;
import com.clz.web.sys_menu.service.SysMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {
    @Resource SysMenuMapper mapper;

    @Override
    public List<SysMenu> getParent() {
        List<String> strings = Arrays.asList("1","0");
        QueryWrapper<SysMenu> query = new QueryWrapper<>();
        query.lambda().in(SysMenu::getType, strings).orderByDesc(SysMenu::getOrderNum);
        List<SysMenu> menus = mapper.selectList(query);
        //组装顶级菜单（默认）
        SysMenu menu = new SysMenu();
        menu.setMenuId(0L);
        menu.setParentId(-1L);
        menu.setTitle("顶级菜单");
        menus.add(menu);
        //组装树数据
        return MakeMenuTree.makeTree(menus,-1L);
    }

    @Override
    public List<SysMenu> getMenuByUserId(Long id) {
        return baseMapper.getMenuByUserId(id);
    }

    @Override
    public List<SysMenu> getMenuByMemberId(Long id) {
        return baseMapper.getMenuByMemberId(id);
    }

    @Override
    public List<SysMenu> getMenuByRoleId(Long id) {
        return baseMapper.getMenuByRoleId(id);
    }
}
