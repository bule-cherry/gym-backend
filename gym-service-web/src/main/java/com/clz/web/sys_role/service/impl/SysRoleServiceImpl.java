package com.clz.web.sys_role.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clz.web.sys_role.entity.RoleParam;
import com.clz.web.sys_role.entity.SysRole;
import com.clz.web.sys_role.mapper.SysRoleMapper;
import com.clz.web.sys_role.service.SysRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
}
