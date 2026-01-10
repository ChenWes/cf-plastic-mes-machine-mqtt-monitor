package com.cf.mes.service.impl;

import com.cf.common.utils.DateUtils;
import com.cf.common.utils.SecurityUtils;
import com.cf.mes.domain.MouldTemperatureSettings;
import com.cf.mes.mapper.MouldTemperatureSettingsMapper;
import com.cf.mes.service.IMouldTemperatureSettingsService;
import com.cf.system.service.ISysDicService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 模具温度设定Service业务层处理
 *
 * @author wilfmao
 * @date 2026-01-02
 */
@Service
public class MouldTemperatureSettingsServiceImpl implements IMouldTemperatureSettingsService {
    @Autowired
    private MouldTemperatureSettingsMapper mouldTemperatureSettingsMapper;

    @Autowired
    private ISysDicService sysDicService;

    /**
     * 查询模具温度设定
     *
     * @param settingsId 模具温度设定主键
     * @return 模具温度设定
     */
    @Override
    public MouldTemperatureSettings getMouldTemperatureSettingsBySettingsId(Long settingsId) {
        return mouldTemperatureSettingsMapper.getMouldTemperatureSettingsBySettingsId(settingsId);
    }

    /**
     * 查询模具温度设定列表
     *
     * @param mouldTemperatureSettings 模具温度设定
     * @return 模具温度设定
     */
    @Override
    public List<MouldTemperatureSettings> getMouldTemperatureSettingsList(MouldTemperatureSettings mouldTemperatureSettings) {
        return mouldTemperatureSettingsMapper.getMouldTemperatureSettingsList(mouldTemperatureSettings);
    }

    /**
     * 根据ID列表，查询模具温度设定列表
     *
     * @param settingsIds 需要查询的模具温度设定主键集合
     * @return 模具温度设定集合
     */
    @Override
    public List<MouldTemperatureSettings> getMouldTemperatureSettingsListBySettingsIds(List<Long> settingsIds) {
        if (CollectionUtils.isEmpty(settingsIds)) {
            return new ArrayList<>();
        }
        settingsIds = settingsIds.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
        if (settingsIds.isEmpty()) {
            return new ArrayList<>();
        }
        return mouldTemperatureSettingsMapper.getMouldTemperatureSettingsListBySettingsIds(settingsIds);
    }

    /**
     * 新增模具温度设定
     *
     * @param mouldTemperatureSettings 模具温度设定
     * @return 结果
     */
    @Override
    public int insertMouldTemperatureSettings(MouldTemperatureSettings mouldTemperatureSettings) {
        mouldTemperatureSettings.setCreateBy(SecurityUtils.getUserCode());
        mouldTemperatureSettings.setCreateTime(DateUtils.getNowDate());
        return mouldTemperatureSettingsMapper.insertMouldTemperatureSettings(mouldTemperatureSettings);
    }

    /**
     * 修改模具温度设定
     *
     * @param mouldTemperatureSettings 模具温度设定
     * @return 结果
     */
    @Override
    public int updateMouldTemperatureSettings(MouldTemperatureSettings mouldTemperatureSettings) {
        mouldTemperatureSettings.setUpdateBy(SecurityUtils.getUserCode());
        mouldTemperatureSettings.setUpdateTime(DateUtils.getNowDate());
        return mouldTemperatureSettingsMapper.updateMouldTemperatureSettings(mouldTemperatureSettings);
    }

    /**
     * 批量删除模具温度设定
     *
     * @param settingsIds 需要删除的模具温度设定主键
     * @return 结果
     */
    @Override
    public int deleteMouldTemperatureSettingsBySettingsIds(Long[] settingsIds) {
        return mouldTemperatureSettingsMapper.deleteMouldTemperatureSettingsBySettingsIds(settingsIds);
    }

    /**
     * 删除模具温度设定信息
     *
     * @param settingsId 模具温度设定主键
     * @return 结果
     */
    @Override
    public int deleteMouldTemperatureSettingsBySettingsId(Long settingsId) {
        return mouldTemperatureSettingsMapper.deleteMouldTemperatureSettingsBySettingsId(settingsId);
    }

    @Override
    public MouldTemperatureSettings getMouldTemperatureSettings(String mouldCode, String partType, String position) {
        MouldTemperatureSettings query = new MouldTemperatureSettings();
        query.setMouldCode(mouldCode);
        query.setPartType(partType);
        query.setPosition(position);
        List<MouldTemperatureSettings> settingsList = mouldTemperatureSettingsMapper.getMouldTemperatureSettingsList(query);
        if (CollectionUtils.isEmpty(settingsList)) {
            return null;
        }
        return settingsList.get(0);
    }

    @Override
    public List<MouldTemperatureSettings> getMouldTemperatureSettings(String mouldCode) {
        MouldTemperatureSettings query = new MouldTemperatureSettings();
        query.setMouldCode(mouldCode);
        return mouldTemperatureSettingsMapper.getMouldTemperatureSettingsList(query);
    }
}
