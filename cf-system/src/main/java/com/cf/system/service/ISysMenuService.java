package com.cf.system.service;

import com.cf.common.core.domain.entity.SysMenu;

import java.util.List;

/**
 * @Description 系统菜单服务接口
 * @Author ccw
 * @Date 2021/5/10
 */
public interface ISysMenuService {

    /**
     * 查询菜单列表
     *
     * @param menu
     * @return
     */
    List<SysMenu> getMenuList(SysMenu menu);

    /**
     * 根据id查询菜单
     *
     * @param menuId
     * @return
     */
    SysMenu getMenuById(long menuId);

    /**
     * 根据code查询菜单
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
     * 根据菜单id删除菜单
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
