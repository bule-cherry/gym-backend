package com.clz.web.sys_user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.clz.web.sys_user.entity.PageParam;
import com.clz.web.sys_user.entity.SysUser;

public interface SysUserService extends IService<SysUser> {
    /**
     * 分页查询
     * @param param
     * @return
     */
    IPage<SysUser> list(PageParam param);
}
