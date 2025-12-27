package com.cf.system.mapper;

import com.cf.system.domain.PlatformSystem;

import java.util.List;

/**
 * 平台系统Mapper接口
 * 
 * @author wilfmao
 * @date 2024-10-29
 */
public interface PlatformSystemMapper 
{
    /**
     * 查询平台系统
     * 
     * @param systemId 平台系统主键
     * @return 平台系统
     */
    public PlatformSystem getPlatformSystemBySystemId(Long systemId);

    /**
     * 查询平台系统列表
     * 
     * @param platformSystem 平台系统
     * @return 平台系统集合
     */
    public List<PlatformSystem> getPlatformSystemList(PlatformSystem platformSystem);

    /**
     * 根据ID列表，查询平台系统列表
     *
     * @param systemIds 需要查询的平台系统主键集合
     * @return 平台系统集合
     */
    public List<PlatformSystem> getPlatformSystemListBySystemIds(List<Long> systemIds);

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
     * 删除平台系统
     * 
     * @param systemId 平台系统主键
     * @return 结果
     */
    public int deletePlatformSystemBySystemId(Long systemId);

    /**
     * 批量删除平台系统
     * 
     * @param systemIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePlatformSystemBySystemIds(Long[] systemIds);
}
