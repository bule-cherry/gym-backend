package com.clz.web.sys_user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * 系统用户实体类
 */
@ApiModel(value = "SysUser", description = "系统用户信息")
@Data
@TableName("sys_user")
public class SysUser implements UserDetails {

    @ApiModelProperty(value = "员工id", example = "1", required = true)
    @TableId(type = IdType.AUTO)
    private Long userId;

    //表明roleId字段不属于sys_user表，需要排除
    @TableField(exist = false)
    private Long roleId;

    @ApiModelProperty(value = "账户(员工编号)", example = "emp001")
    private String username;

    @ApiModelProperty(value = "密码", example = "e10adc3949ba59abbe56e057f20f883e")
    private String password;

    @ApiModelProperty(value = "电话", example = "13800138000")
    private String phone;

    @ApiModelProperty(value = "邮箱", example = "zhangsan@example.com")
    private String email;

    @ApiModelProperty(value = "性别 0:男 1:女", example = "0")
    private String sex;

    @ApiModelProperty(value = "工资", example = "5000.00")
    private BigDecimal salary;

    @ApiModelProperty(value = "类型(1:员工 2:教练)", example = "1")
    private String userType;

    @ApiModelProperty(value = "状态(0:停用 1:启用)", example = "1")
    private String status;

    @ApiModelProperty(value = "是否是管理员(1:是 0:否)", example = "0")
    private String isAdmin;

    @ApiModelProperty(value = "账户是否过期(1:未过期 0:已过期)", example = "1")
    private boolean isAccountNonExpired;

    @ApiModelProperty(value = "账户是否被锁定(1:未锁定 0：已锁定)", example = "1")
    private boolean isAccountNonLocked;

    @ApiModelProperty(value = "密码是否过期(1:未过期 0:已过期)", example = "1")
    private boolean isCredentialsNonExpired;

    @ApiModelProperty(value = "账户是否可用(1 可用 0不可用)", example = "1")
    private boolean isEnabled;

    @ApiModelProperty(value = "姓名", example = "张三")
    private String nickName;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;
    //用户权限字段的集合
    //表明authorities字段不属于sys_user表，需要排除
    @TableField(exist = false)
    Collection<? extends GrantedAuthority> authorities;
}
