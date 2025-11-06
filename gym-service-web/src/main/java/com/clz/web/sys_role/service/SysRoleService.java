package com.clz.web.sys_role.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.clz.web.sys_role.entity.RoleAssignParam;
import com.clz.web.sys_role.entity.RoleParam;
import com.clz.web.sys_role.entity.RolePermissionVo;
import com.clz.web.sys_role.entity.SysRole;
import org.springframework.stereotype.Service;

public interface SysRoleService extends IService<SysRole> {
    IPage<SysRole> list(RoleParam param);

    //查询权限树数据回显
    RolePermissionVo getMenuTree(RoleAssignParam param);
}
