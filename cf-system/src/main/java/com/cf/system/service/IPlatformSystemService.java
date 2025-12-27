package com.cf.system.service;

import com.cf.system.domain.PlatformSystem;

import java.util.List;

/**
 * 平台系统Service接口
 * 
 * @author wilfmao
 * @date 2024-10-29
 */
public interface IPlatformSystemService 
{
    /**
     * 查询平台系统
     * 
     * @param systemId 平台系统主键
     * @return 平台系统
     */
    public PlatformSystem getPlatformSystemBySystemId(Long systemId);

    /**
     * 根据ID列表，查询平台系统列表
     *
     * @param systemIds 需要查询的平台系统主键集合
     * @return 平台系统集合
     */
    public List<PlatformSystem> getPlatformSystemListBySystemIds(List<Long> systemIds);

    /**
     * 查询平台系统列表
     * 
     * @param platformSystem 平台系统
     * @return 平台系统集合
     */
    public List<PlatformSystem> getPlatformSystemList(PlatformSystem platformSystem);

    /**
     * 新增平台系统
     * 
     * @param platformSystem 平台系统
     * @return 结果
     */
    public int insertPlatformSystem(PlatformSystem platformSystem);

    /**
     * 修改平台系统
     * 
     * @param platformSystem 平台系统
     * @return 结果
     */
    public int updatePlatformSystem(PlatformSystem platformSystem);

    /**
     * 批量删除平台系统
     * 
     * @param systemIds 需要删除的平台系统主键集合
     * @return 结果
     */
    public int deletePlatformSystemBySystemIds(Long[] systemIds);

    /**
     * 删除平台系统信息
     * 
     * @param systemId 平台系统主键
     * @return 结果
     */
    public int deletePlatformSystemBySystemId(Long systemId);
}
