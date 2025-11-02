package com.clz.web.sys_menu.entity;

import com.fasterxml.jackson.databind.util.BeanUtil;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MakeMenuTree {
    public static List<SysMenu> makeTree(List<SysMenu> menus, Long pid) {
        List<SysMenu> list = new ArrayList<SysMenu>();
        Optional.ofNullable(menus).orElse(new ArrayList<>())
                .stream().filter(item -> item != null && item.getParentId().equals(pid))
                .forEach(item -> {
                    SysMenu menu = new SysMenu();
                    BeanUtils.copyProperties(item, menu);
                    List<SysMenu> children = makeTree(menus, menu.getMenuId());
                    menu.setChildren(children);
                    list.add(menu);
                });
        return list;
    }
    public static List<RouterVO> makeRouter(List<SysMenu> menus, Long pid) {
        //构建存放路由数据的容器
        ArrayList<RouterVO> list = new ArrayList<>();
        // 设置路由 name, path, 设置children
        Optional.ofNullable(menus).orElse(new ArrayList<>())
                .stream().filter(item -> item != null && item.getParentId().equals(pid))
                .forEach(item -> {
                    RouterVO router = new RouterVO();
                    router.setName(item.getName());
                    router.setPath(item.getPath());
                    //设置children,递归调用自己
                    List<RouterVO> children = makeRouter(menus, item.getMenuId());
                    router.setChildren(children);
                    //如果menu的上级是0, 那么component = Layout , 否则等于menu.url
                    if(item.getParentId().equals(0L)){
                        router.setComponent("Layout");
                        //如果一级菜单是 菜单类型，单独处理
                        if(item.getType().equals("1")){
                            router.setRedirect(item.getPath());
                            //菜单需要设置children
                            List<RouterVO> listChild = new ArrayList<>();
                            RouterVO child  = new RouterVO();
                            child.setName(item.getName());
                            child.setPath(item.getPath());
                            child.setComponent(item.getUrl());
                            child.setMeta(child.new Meta(
                                    item.getTitle(),
                                    item.getIcon(),
                                    item.getCode().split(",")
                            ));
                            listChild.add(child);
                            router.setChildren(listChild);
                            router.setPath(item.getPath() +"parent");
                            router.setName(item.getName()+"parent");
                        }
                    }else{
                        router.setComponent(item.getUrl());
                    }
                    router.setMeta(router.new Meta(item.getTitle(), item.getIcon(),
                            item.getCode().split(",")));
                    list.add(router);
                });
            return list;
    }
}
