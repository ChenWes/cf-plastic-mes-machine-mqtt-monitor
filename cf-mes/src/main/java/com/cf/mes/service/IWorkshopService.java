package com.cf.mes.service;

import com.cf.mes.domain.Workshop;

import java.util.List;

/**
 * 车间信息Service接口
 *
 * @author luorog
 */
public interface IWorkshopService {
    /**
     * 查询车间信息
     *
     * @param workshopId 车间信息ID
     * @return 车间信息
     */
    Workshop getWorkshopById(Long workshopId);

    Workshop getSlimWorkshopById(Long workshopId);

    /**
     * 查询车间信息
     *
     * @param workshopCode
     * @return
     */
    Workshop getWorkshopByCode(String workshopCode);

    /**
     * 查询车间信息列表
     *
     * @param workshop 车间信息
     * @return 车间信息集合
     */
    List<Workshop> getWorkshopList(Workshop workshop);

    List<Workshop> getSlimWorkshopList(Workshop workshop);

    List<Workshop> getWorkshopListByFactoryId(Long factoryId);

    /**
     * 根据工厂ids查询车间信息列表
     *
     * @param factoryIds 工厂ids
     * @return 车间信息集合
     */
    List<Workshop> getWorkshopListByFactoryIds(Long[] factoryIds);

    /**
     * 新增车间信息
     *
     * @param workshop 车间信息
     * @return 结果
     */
    int insertWorkshop(Workshop workshop);

    /**
     * 修改车间信息
     *
     * @param workshop 车间信息
     * @return 结果
     */
    int updateWorkshop(Workshop workshop);

    /**
     * 批量删除车间信息
     *
     * @param workshopIds 需要删除的车间信息ID
     * @return 结果
     */
    int deleteWorkshopByIds(Long[] workshopIds);

    /**
     * 删除车间信息信息
     *
     * @param workshopId 车间信息ID
     * @return 结果
     */
    int deleteWorkshopById(Long workshopId);

    List<Workshop> getWorkshopListByIds(List<Long> workshopIds);

    List<Workshop> getWorkshopWithFactoryListByIds(List<Long> workshopIds);
}
