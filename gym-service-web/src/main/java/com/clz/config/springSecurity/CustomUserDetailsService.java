package com.clz.config.springSecurity;

import com.clz.web.member.entity.Member;
import com.clz.web.member.service.MemberService;
import com.clz.web.sys_menu.entity.SysMenu;
import com.clz.web.sys_menu.service.SysMenuService;
import com.clz.web.sys_user.entity.SysUser;
import com.clz.web.sys_user.service.SysUserService;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Component("customizedUserDetailService")
public class CustomUserDetailsService implements UserDetailsService {

    @Resource
    MemberService memberService;
    @Resource
    SysUserService userService;
    @Resource
    SysMenuService menuService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        String[] strings = s.split(":", 2);
        String username = strings[0];
        String userType = strings[1];

        if(userType.equals("1")) { // 会员
            Member user = memberService.loadUser(username);
            if(user == null) {
                throw new UsernameNotFoundException("用户名或密码错误!");
            }
            List<SysMenu> list = menuService.getMenuByMemberId(user.getMemberId());
            List<String> collect = list.stream().map(SysMenu::getCode).filter(item -> StringUtils.isNotEmpty(item)).collect(Collectors.toList());
            String[] array = collect.toArray(new String[collect.size()]);
            //授权
            List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList(array);
            user.setAuthorities(authorityList);
            return user;
        } else if (userType.equals("2")) {
            SysUser user = userService.loadUser(username);
            if(user == null) {
                throw new UsernameNotFoundException("用户名或密码错误!");
            }
            List<SysMenu> list = menuService.getMenuByUserId(user.getUserId());
            List<String> collect = list.stream().map(SysMenu::getCode).filter(item -> StringUtils.isNotEmpty(item)).collect(Collectors.toList());
            String[] array = collect.toArray(new String[collect.size()]);
            //授权
            List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList(array);
            user.setAuthorities(authorityList);
            return user;
        }else{
            throw new UsernameNotFoundException("用户类型不存在!");
        }


    }
}
