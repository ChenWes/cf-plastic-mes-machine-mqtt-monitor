package com.cf.mes.mapper;

import java.util.List;

import com.cf.mes.domain.Factory;

/**
 * 工厂信息Mapper接口
 *
 * @author luorog
 */
public interface FactoryMapper {
    /**
     * 查询工厂信息
     *
     * @param factoryId 工厂信息ID
     * @return 工厂信息
     */
    Factory getFactoryById(Long factoryId);

    /**
     * 查询工厂信息
     *
     * @param factoryCode
     * @return
     */
    Factory getFactoryByCode(String factoryCode);


    /**
     * 查询工厂信息列表
     *
     * @param factory 工厂信息
     * @return 工厂信息集合
     */
    List<Factory> getFactoryList(Factory factory);

    /**
     * 新增工厂信息
     *
     * @param factory 工厂信息
     * @return 结果
     */
    int insertFactory(Factory factory);

    /**
     * 修改工厂信息
     *
     * @param factory 工厂信息
     * @return 结果
     */
    int updateFactory(Factory factory);

    /**
     * 删除工厂信息
     *
     * @param factoryId 工厂信息ID
     * @return 结果
     */
    int deleteFactoryById(Long factoryId);

    /**
     * 批量删除工厂信息
     *
     * @param factoryIds 需要删除的数据ID
     * @return 结果
     */
    int deleteFactoryByIds(Long[] factoryIds);

    List<Factory> getFactoryListByIds(List<Long> factoryIds);
}
