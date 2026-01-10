package com.cf.mes.service;

import com.cf.mes.domain.MouldTemperatureSettings;

import java.util.List;

/**
 * 模具温度设定Service接口
 *
 * @author wilfmao
 * @date 2026-01-02
 */
public interface IMouldTemperatureSettingsService {
    /**
     * 查询模具温度设定
     *
     * @param settingsId 模具温度设定主键
     * @return 模具温度设定
     */
    public MouldTemperatureSettings getMouldTemperatureSettingsBySettingsId(Long settingsId);

    /**
     * 根据ID列表，查询模具温度设定列表
     *
     * @param settingsIds 需要查询的模具温度设定主键集合
     * @return 模具温度设定集合
     */
    public List<MouldTemperatureSettings> getMouldTemperatureSettingsListBySettingsIds(List<Long> settingsIds);

    /**
     * 查询模具温度设定列表
     *
     * @param mouldTemperatureSettings 模具温度设定
     * @return 模具温度设定集合
     */
    public List<MouldTemperatureSettings> getMouldTemperatureSettingsList(MouldTemperatureSettings mouldTemperatureSettings);

    /**
     * 新增模具温度设定
     *
     * @param mouldTemperatureSettings 模具温度设定
     * @return 结果
     */
    public int insertMouldTemperatureSettings(MouldTemperatureSettings mouldTemperatureSettings);

    /**
     * 修改模具温度设定
     *
     * @param mouldTemperatureSettings 模具温度设定
     * @return 结果
     */
    public int updateMouldTemperatureSettings(MouldTemperatureSettings mouldTemperatureSettings);

    /**
     * 批量删除模具温度设定
     *
     * @param settingsIds 需要删除的模具温度设定主键集合
     * @return 结果
     */
    public int deleteMouldTemperatureSettingsBySettingsIds(Long[] settingsIds);

    /**
     * 删除模具温度设定信息
     *
     * @param settingsId 模具温度设定主键
     * @return 结果
     */
    public int deleteMouldTemperatureSettingsBySettingsId(Long settingsId);

    MouldTemperatureSettings getMouldTemperatureSettings(String mouldCode, String partType, String position);

    List<MouldTemperatureSettings> getMouldTemperatureSettings(String mouldCode);

}
