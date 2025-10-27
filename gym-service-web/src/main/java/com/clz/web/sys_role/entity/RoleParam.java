package com.clz.web.sys_role.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("角色查询参数")
public class RoleParam {
    @ApiModelProperty("当前页码")
    private Long currentPage;//当前页码
    @ApiModelProperty("页面数据容量")
    private Long pageSize;//页面数据容量
    @ApiModelProperty("角色名称")
    private String roleName;//角色名称
}
