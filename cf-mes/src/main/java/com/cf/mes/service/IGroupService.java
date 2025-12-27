package com.cf.mes.service;

import com.cf.mes.domain.Group;

import java.util.List;

/**
 * @Description 组别业务接口
 * @Author ccw
 * @Date 2021/5/11
 */
public interface IGroupService {
    /**
     * 查询组别
     *
     * @param groupId 组别ID
     * @return 组别
     */
    Group getGroupById(Long groupId);

    /**
     * 查询组别
     * @param groupCode
     * @return
     */
    Group getGroupByCode(String groupCode);


    /**
     * 查询组别列表
     *
     * @param group 组别
     * @return 组别集合
     */
    List<Group> getGroupList(Group group);

    /**
     * 新增组别
     *
     * @param group 组别
     * @return 结果
     */
    int insertGroup(Group group);

    /**
     * 修改组别
     *
     * @param group 组别
     * @return 结果
     */
    int updateGroup(Group group);

    /**
     * 删除组别
     *
     * @param groupId 组别ID
     * @return 结果
     */
    int deleteGroupById(Long groupId);

    /**
     * 批量删除组别
     *
     * @param groupIds 需要删除的数据ID
     * @return 结果
     */
    int deleteGroupByIds(Long[] groupIds);

    List<Group> getGroupByIdList(List<Long> groupIdList);
}
