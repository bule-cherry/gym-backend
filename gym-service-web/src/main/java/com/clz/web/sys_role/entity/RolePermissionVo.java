package com.clz.web.sys_role.entity;

import com.clz.web.sys_menu.entity.SysMenu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolePermissionVo {

    private List<SysMenu> listMenu = new ArrayList<SysMenu>();

    private Object[] checkList;
}
