package com.cf.system.service.impl;

import com.cf.common.annotation.Lang;
import com.cf.common.enums.LangInfoColumnName;
import com.cf.common.enums.LangInfoKeyFieldName;
import com.cf.common.enums.LangInfoTableName;
import com.cf.common.utils.DateUtils;
import com.cf.common.utils.SecurityUtils;
import com.cf.system.domain.PlatformSystem;
import com.cf.system.mapper.PlatformSystemMapper;
import com.cf.system.service.IPlatformSystemService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 平台系统Service业务层处理
 *
 * @author wilfmao
 * @date 2024-10-29
 */
@Service
@Lang(keyFieldName = LangInfoKeyFieldName.PLATFORM_SYSTEM_KEY_FIELD,
        tableName = LangInfoTableName.PLATFORM_SYSTEM_TABLE,
        columnNames = {
                LangInfoColumnName.PLATFORM_SYSTEM_TITLE_COLUMN_NAME,
                LangInfoColumnName.PLATFORM_SYSTEM_TAG_COLUMN_NAME,
                LangInfoColumnName.PLATFORM_SYSTEM_REMARK_COLUMN_NAME})
public class PlatformSystemServiceImpl implements IPlatformSystemService {
    @Autowired
    private PlatformSystemMapper platformSystemMapper;

    /**
     * 查询平台系统
     *
     * @param systemId 平台系统主键
     * @return 平台系统
     */
    @Override
    public PlatformSystem getPlatformSystemBySystemId(Long systemId) {
        return platformSystemMapper.getPlatformSystemBySystemId(systemId);
    }

    /**
     * 查询平台系统列表
     *
     * @param platformSystem 平台系统
     * @return 平台系统
     */
    @Override
    public List<PlatformSystem> getPlatformSystemList(PlatformSystem platformSystem) {
        return platformSystemMapper.getPlatformSystemList(platformSystem);
    }

    /**
     * 根据ID列表，查询平台系统列表
     *
     * @param systemIds 需要查询的平台系统主键集合
     * @return 平台系统集合
     */
    @Override
    public List<PlatformSystem> getPlatformSystemListBySystemIds(List<Long> systemIds) {
        if (CollectionUtils.isEmpty(systemIds)) {
            return new ArrayList<>();
        }
        systemIds = systemIds.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
        if (systemIds.isEmpty()) {
            return new ArrayList<>();
        }
        return platformSystemMapper.getPlatformSystemListBySystemIds(systemIds);
    }

    /**
     * 新增平台系统
     *
     * @param platformSystem 平台系统
     * @return 结果
     */
    @Override
    public int insertPlatformSystem(PlatformSystem platformSystem) {
        platformSystem.setCreateBy(SecurityUtils.getUserCode());
        platformSystem.setCreateTime(DateUtils.getNowDate());
        return platformSystemMapper.insertPlatformSystem(platformSystem);
    }

    /**
     * 修改平台系统
     *
     * @param platformSystem 平台系统
     * @return 结果
     */
    @Override
    public int updatePlatformSystem(PlatformSystem platformSystem) {
        platformSystem.setUpdateBy(SecurityUtils.getUserCode());
        platformSystem.setUpdateTime(DateUtils.getNowDate());
        return platformSystemMapper.updatePlatformSystem(platformSystem);
    }

    /**
     * 批量删除平台系统
     *
     * @param systemIds 需要删除的平台系统主键
     * @return 结果
     */
    @Override
    public int deletePlatformSystemBySystemIds(Long[] systemIds) {
        return platformSystemMapper.deletePlatformSystemBySystemIds(systemIds);
    }

    /**
     * 删除平台系统信息
     *
     * @param systemId 平台系统主键
     * @return 结果
     */
    @Override
    public int deletePlatformSystemBySystemId(Long systemId) {
        return platformSystemMapper.deletePlatformSystemBySystemId(systemId);
    }
}
