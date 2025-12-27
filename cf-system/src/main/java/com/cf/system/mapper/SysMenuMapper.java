package com.cf.system.mapper;

import com.cf.common.annotation.Lang;
import com.cf.common.core.domain.entity.SysMenu;
import com.cf.common.enums.LangInfoColumnName;
import com.cf.common.enums.LangInfoKeyFieldName;
import com.cf.common.enums.LangInfoTableName;

import java.util.List;

/**
 * @Description 系统菜单数据接口
 * @Author ccw
 * @Date 2021/5/10
 */
public interface SysMenuMapper {

    /**
     * 获取菜单列表
     *
     * @param menu
     * @return
     */
    @Lang(keyFieldName = LangInfoKeyFieldName.MENU_ID_KEY_FILE_NAME,tableName = LangInfoTableName.MENU_TABLE_NAME, columnNames = {LangInfoColumnName.MENU_NAME_COLUMN_NAME})
    List<SysMenu> getMenuList(SysMenu menu);

    /**
     * 根据id获取菜单
     *
     * @param menuId
     * @return
     */
    SysMenu getMenuById(long menuId);

    /**
     * 根据code获取菜单
     *
     * @param menuCode
     * @return
     */
    SysMenu getMenuByCode(String menuCode);

    /**
     * 更新菜单
     *
     * @param menu
     * @return
     */
    int updateMenu(SysMenu menu);

    /**
     * 插入菜单
     *
     * @param menu
     * @return
     */
    int insertMenu(SysMenu menu);

    /**
     * 单个删除菜单
     *
     * @param menuId
     * @return
     */
    int deleteMenuById(Long menuId);

    /**
     * 批量删除菜单
     *
     * @param menuIds
     * @return
     */
    int deleteMenuByIds(Long[] menuIds);


}
