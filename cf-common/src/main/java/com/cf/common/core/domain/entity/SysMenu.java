package com.cf.common.core.domain.entity;


import java.util.List;
import java.util.Objects;

import com.cf.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 菜单权限表 sys_menu
 *
 * @author WesChen
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysMenu extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private long menuId;
    private String menuCode;
    private String menuName;
    private long parentId;
    private int seqNo;
    private String path;
    private String status;
    private String menuType;
    private String hideFlag;
    private String icon;
    private String remark;
    private SysMenu parentEntity;

    /**
     * 子菜单
     */
    private List<SysMenu> children;

}
