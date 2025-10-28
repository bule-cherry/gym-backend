package com.clz.web.sys_user_role.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户角色关联实体类
 */
@ApiModel(value = "SysUserRole", description = "用户与角色的关联关系")
@TableName("sys_user_role")  // 指定数据库表名
@Data
public class SysUserRole implements Serializable {
    @ApiModelProperty(value = "主键", example = "1", required = true)
    @TableId(value = "user_role_id", type = IdType.AUTO)  // 主键字段，自增
    private Integer userRoleId;

    @ApiModelProperty(value = "员工id", example = "1001")
    private Integer userId;

    @ApiModelProperty(value = "角色id", example = "2001")
    private Integer roleId;
}