package com.clz.web.sys_menu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.baomidou.mybatisplus.annotation.*;

@Data
@ApiModel("菜单信息实体")
@TableName("sys_menu")
public class SysMenu {

    @ApiModelProperty(
            value = "菜单ID，主键，自增",
            example = "1",
            required = true
    )
    @TableId(value = "menu_id", type = IdType.AUTO)
    private Long menuId;

    @ApiModelProperty(
            value = "父级菜单ID，根节点为0",
            example = "0",
            required = true
    )
    @TableField(value = "parent_id")
    private Long parentId;

    @ApiModelProperty(
            value = "菜单名称，用于左侧菜单栏显示",
            example = "系统管理",
            required = true
    )
    @TableField(value = "title")
    private String title;

    @ApiModelProperty(
            value = "权限编码，用于前端按钮权限和后端接口鉴权，如：sys,menu,list",
            example = "sys,menu,view",
            required = false
    )
    @TableField(value = "code")
    private String code;

    @ApiModelProperty(
            value = "路由名称（Vue中name，需唯一）",
            example = "SystemManagement",
            required = false
    )
    @TableField(value = "name")
    private String name;

    @ApiModelProperty(
            value = "路由路径（Vue中path，用于跳转）",
            example = "/system/menu",
            required = false
    )
    @TableField(value = "path")
    private String path;

    @ApiModelProperty(
            value = "前端组件路径（Vue文件路径）",
            example = "system/menu/index",
            required = false
    )
    @TableField(value = "url")
    private String url;

    @ApiModelProperty(
            value = "菜单类型：0-目录，1-菜单，2-按钮",
            example = "1",
            allowableValues = "0,1,2",
            required = true
    )
    @TableField(value = "type")
    private String type;

    @ApiModelProperty(
            value = "菜单图标（支持Element UI图标或自定义类）",
            example = "el-icon-setting",
            required = false
    )
    @TableField(value = "icon")
    private String icon;

    @ApiModelProperty(
            value = "上级菜单名称（非数据库字段，仅用于展示）",
            example = "根目录",
            required = false
    )
    @TableField(value = "parent_name")
    private String parentName;

    @ApiModelProperty(
            value = "排序号，数值越小越靠前",
            example = "1",
            required = false
    )
    @TableField(value = "order_num")
    private Long orderNum;

    @ApiModelProperty(
            value = "创建时间",
            example = "2024-01-01 10:00:00",
            required = false
    )
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(
            value = "更新时间",
            example = "2024-01-01 12:00:00",
            required = false
    )
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    // ================== 非数据库字段 ==================

    @ApiModelProperty(
            value = "树形结构中当前节点是否展开（仅前端使用）",
            example = "true",
            required = false
    )
    @TableField(exist = false)
    private Boolean open;

    @ApiModelProperty(
            value = "子菜单列表，用于构建树形结构",
            required = false
    )
    @TableField(exist = false)
    private List<SysMenu> children = new ArrayList<>();
}
