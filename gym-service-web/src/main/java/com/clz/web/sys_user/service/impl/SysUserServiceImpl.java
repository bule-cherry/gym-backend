package com.clz.web.sys_user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clz.web.sys_user.entity.PageParam;
import com.clz.web.sys_user.entity.SysUser;
import com.clz.web.sys_user.mapper.SysUserMapper;
import com.clz.web.sys_user.service.SysUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Resource
    private SysUserMapper mapper;

    /**
     * 分页查询
     * @param param
     * @return
     */
    @Override
    public IPage<SysUser> list(PageParam param) {
        IPage<SysUser> page = new Page<>(param.getCurrentPage(), param.getPageSize());
        QueryWrapper<SysUser> query = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(param.getNickName())) {
            query.lambda().like(SysUser::getNickName, param.getNickName());
        }
        if(StringUtils.isNotEmpty(param.getPhone())){
            query.lambda().like(SysUser::getPhone,param.getPhone());
        }
        return mapper.selectPage(page, query);
    }

    @Override
    public SysUser loadUser(String username) {
        LambdaQueryWrapper<SysUser> query = new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, username);
        return mapper.selectOne(query);
    }
}
