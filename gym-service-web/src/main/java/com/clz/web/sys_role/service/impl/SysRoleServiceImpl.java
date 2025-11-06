package com.clz.web.sys_role.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clz.web.sys_menu.entity.MakeMenuTree;
import com.clz.web.sys_menu.entity.SysMenu;
import com.clz.web.sys_menu.service.SysMenuService;
import com.clz.web.sys_role.entity.RoleAssignParam;
import com.clz.web.sys_role.entity.RoleParam;
import com.clz.web.sys_role.entity.RolePermissionVo;
import com.clz.web.sys_role.entity.SysRole;
import com.clz.web.sys_role.mapper.SysRoleMapper;
import com.clz.web.sys_role.service.SysRoleService;
import com.clz.web.sys_user.entity.SysUser;
import com.clz.web.sys_user.service.SysUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    @Resource
    private SysRoleMapper mapper;

    @Override
    public IPage<SysRole> list(RoleParam param) {
        Page<SysRole> page = new Page<>();
        page.setCurrent(param.getCurrentPage());
        page.setSize(param.getPageSize());
        QueryWrapper<SysRole> query = new QueryWrapper<>();
//        query.lambda().like(SysRole::getRoleName, param.getRoleName());
        query.like(param.getRoleName() != null, "role_name", param.getRoleName());
        return mapper.selectPage(page, query);
    }

    @Resource
    SysMenuService sysMenuService;
    @Resource
    SysUserService sysUserService;

    /**
     * 这个方法里,userId查询出来的是当前用户最多能分配的菜单,  例如:root用户, 可以分配所有的菜单
     * roleId查询出来的 List是角色选中的菜单, 也就是角色被分配的菜单,
     *  可能说, 当前角色还有其他菜单, 但是因为用户能显示的菜单有限, 不能显示出角色所有的功能
     *  数据来源: roleId 取决于你点击哪一条记录, userId取决于你登录的账号,存储在store里
     * @param param
     * @return */
    @Override
    public RolePermissionVo getMenuTree(RoleAssignParam param) {
        Long roleId = param.getRoleId();
        Long userId = param.getUserId();
        SysUser user = sysUserService.getById(userId);
        List<SysMenu> menuList = new ArrayList<>();
        if( user == null){
            return null;
        }
        if(StringUtils.isNotEmpty(user.getIsAdmin()) && user.getIsAdmin().equals("1")){
            menuList = sysMenuService.list();
        }else{
            menuList = sysMenuService.getMenuByUserId(userId);
        }
        // 构造树结构
        menuList = MakeMenuTree.makeTree(menuList,0L);

        // 查询角色原来的数据, 这个意义我还不理解
        List<SysMenu> menuByRoleId = sysMenuService.getMenuByRoleId(roleId);
        List<Long> ids = new ArrayList<>();
        Optional.ofNullable(menuByRoleId).orElse(new ArrayList<>())
                .stream().filter(Objects::nonNull)
                .forEach(item -> {
                    ids.add(item.getMenuId());
                });
        return new RolePermissionVo(menuList, ids.toArray());
    }
}
