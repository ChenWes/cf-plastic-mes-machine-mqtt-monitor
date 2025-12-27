package com.cf.system.service.impl;

import com.cf.common.annotation.Lang;
import com.cf.common.constant.MessageCode;
import com.cf.common.core.domain.entity.SysMenu;
import com.cf.common.enums.LangInfoColumnName;
import com.cf.common.enums.LangInfoKeyFieldName;
import com.cf.common.enums.LangInfoTableName;
import com.cf.common.exception.CustomException;
import com.cf.common.utils.DateUtils;
import com.cf.common.utils.MessageUtils;
import com.cf.common.utils.StringUtils;
import com.cf.system.mapper.SysMenuMapper;
import com.cf.system.service.ISysMenuService;
import com.cf.system.service.ISysPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @Description 系统菜单业务实现
 * @Author ccw
 * @Date 2021/5/10
 */
@Service
@Lang(keyFieldName = LangInfoKeyFieldName.MENU_ID_KEY_FILE_NAME, tableName = LangInfoTableName.MENU_TABLE_NAME, columnNames = {LangInfoColumnName.MENU_NAME_COLUMN_NAME})
public class SysMenuServiceImpl implements ISysMenuService {


    @Autowired
    private SysMenuMapper menuMapper;

    @Autowired
    private ISysPermissionService permissionService;

    @Override
    @Transactional(readOnly = true)
    public List<SysMenu> getMenuList(SysMenu menu) {
        List<SysMenu> list = this.menuMapper.getMenuList(menu);
        list=this.processParentEntity(list);
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public SysMenu getMenuById(long menuId) {
        SysMenu menu = this.menuMapper.getMenuById(menuId);
        if (menu != null) {
            this.processParentEntity(Arrays.asList(menu));
        }
        return menu;
    }

    @Override
    @Transactional(readOnly = true)
    public SysMenu getMenuByCode(String menuCode) {
        SysMenu menu = this.menuMapper.getMenuByCode(menuCode);
        if (menu != null) {
            this.processParentEntity(Arrays.asList(menu));
        }
        return menu;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateMenu(SysMenu menu) {
        menu.setUpdateTime(DateUtils.getNowDate());
        //父菜单不能为自己
        if(menu.getMenuId()==menu.getParentId()){
            String msg = MessageUtils.message(MessageCode.MENU_PARENT_ID_ITSELF);
            throw new CustomException(msg);
        }
        SysMenu sysMenu=menuMapper.getMenuById(menu.getParentId());
        //父菜单不能为自己子菜单
        while (sysMenu!=null){
            if(sysMenu.getParentId()==menu.getMenuId()){
                String msg = MessageUtils.message(MessageCode.MENU_PARENT_ID_CHILD);
                throw new CustomException(msg);
            }else {
                sysMenu=menuMapper.getMenuById(sysMenu.getParentId());
            }
        }
        return this.menuMapper.updateMenu(menu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertMenu(SysMenu menu) {

        //check data
        if (StringUtils.isEmpty(menu.getMenuCode())) {
            String msg = MessageUtils.message(MessageCode.MENU_MENU_CODE_EMPTY);
            throw new CustomException(msg);
        }

        SysMenu sysMenu = this.menuMapper.getMenuByCode(menu.getMenuCode());
        if (sysMenu != null) {
            String msg = MessageUtils.message(MessageCode.MENU_MENU_CODE_EXISTS, sysMenu.getMenuCode());
            throw new CustomException(msg);

        }
        menu.setCreateTime(DateUtils.getNowDate());
        return  this.menuMapper.insertMenu(menu);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteMenuById(Long menuId) {

        this.permissionService.deletePermissionByMenuId(menuId);

        return this.menuMapper.deleteMenuById(menuId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteMenuByIds(Long[] menuIds) {
        for (Long menuId : menuIds) {
            this.permissionService.deletePermissionByMenuId(menuId);
        }
        return this.menuMapper.deleteMenuByIds(menuIds);
    }

    /***
     * 带出附件菜单
     * @param list
     * @return
     */
    private List<SysMenu> processParentEntity(List<SysMenu> list) {
        List<SysMenu> allData = this.menuMapper.getMenuList(null);
        list.forEach(m -> {
            if (m.getParentId() > 0) {
                Optional<SysMenu> opt = allData.stream().filter(p -> p.getMenuId() == m.getParentId()).findFirst();
                if (opt.isPresent()) {
                    m.setParentEntity(opt.get());
                }
            }

        });
        return list;
    }
}
