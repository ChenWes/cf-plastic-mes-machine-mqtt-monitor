package com.cf.common.core.domain.entity;

import com.cf.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * @Description 系统权限类
 * @Author ccw
 * @Date 2021/5/10
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysPermission extends BaseEntity {

    private long permissionId;
    private long menuId;
    private String permissionCode;
    private String permissionName;
    private String status;

}
