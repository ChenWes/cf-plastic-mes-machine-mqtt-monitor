package com.cf.mes.service.impl;

import com.cf.common.annotation.Lang;
import com.cf.common.constant.MessageCode;
import com.cf.common.core.token.ITokenService;
import com.cf.common.enums.LangInfoColumnName;
import com.cf.common.enums.LangInfoKeyFieldName;
import com.cf.common.enums.LangInfoTableName;
import com.cf.common.exception.CustomException;
import com.cf.common.utils.DateUtils;
import com.cf.common.utils.MessageUtils;
import com.cf.common.utils.StringUtils;
import com.cf.mes.domain.Group;
import com.cf.mes.mapper.GroupMapper;
import com.cf.mes.service.IDeptService;
import com.cf.mes.service.IGroupService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Description 组别业务实现
 * @Author ccw
 * @Date 2021/5/11
 */
@Service
@Lang(keyFieldName = LangInfoKeyFieldName.GROUP_ID_KEY_FILE_NAME, tableName = LangInfoTableName.GROUP_TABLE_NAME, columnNames = {LangInfoColumnName.GROUP_NAME_COLUMN_NAME})
public class GroupServiceImpl implements IGroupService {
    @Autowired
    private GroupMapper groupMapper;

    @Autowired
    private IDeptService deptService;

    @Autowired
    private ITokenService tokenService;

    /**
     * 查询组别
     *
     * @param groupId 组别ID
     * @return 组别
     */
    @Override
    @Transactional(readOnly = true)
    public Group getGroupById(Long groupId) {
        Group group = groupMapper.getGroupById(groupId);
        if (group != null) {
            group.setDeptEntity(deptService.getDeptById((group.getDeptId())));
        }
        return group;
    }

    /**
     * 查询组别
     *
     * @param groupCode
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Group getGroupByCode(String groupCode) {
        Group group = groupMapper.getGroupByCode(groupCode);
        if (group != null) {
            group.setDeptEntity(deptService.getDeptById((group.getDeptId())));
        }
        return group;
    }

    /**
     * 查询组别列表
     *
     * @param group 组别
     * @return 组别集合
     */
    @Override
    @Transactional(readOnly = true)
    public List<Group> getGroupList(Group group) {
        List<Group> groupList = groupMapper.getGroupList(group);
        for (Group groupItem : groupList) {
            groupItem.setDeptEntity(deptService.getDeptById((groupItem.getDeptId())));
        }
        return groupList;
    }

    /**
     * 新增组别
     *
     * @param group 组别
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertGroup(Group group) {
        //check data
        if (StringUtils.isEmpty(group.getGroupCode())) {
            String msg = MessageUtils.message(MessageCode.GROUP_GROUP_CODE_EMPTY);
            throw new CustomException(msg);
        }
        Group checkObj = groupMapper.getGroupByCode(group.getGroupCode());
        if (checkObj != null) {
            String msg = MessageUtils.message(MessageCode.GROUP_GROUP_CODE_EXISTS, checkObj.getGroupCode());
            throw new CustomException(msg);
        }
        group.setCreateBy(tokenService.getUserCode());
        group.setCreateTime(DateUtils.getNowDate());
        return groupMapper.insertGroup(group);
    }

    /**
     * 修改组别
     *
     * @param group 组别
     * @return 结果
     */
    @Override

    @Transactional(rollbackFor = Exception.class)
    public int updateGroup(Group group) {
        group.setUpdateBy(tokenService.getUserCode());
        group.setUpdateTime(DateUtils.getNowDate());
        return groupMapper.updateGroup(group);
    }

    /**
     * 删除组别
     *
     * @param groupId 组别ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteGroupById(Long groupId) {
        return groupMapper.deleteGroupById(groupId);
    }

    /**
     * 批量删除组别
     *
     * @param groupIds 需要删除的数据ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteGroupByIds(Long[] groupIds) {
        return groupMapper.deleteGroupByIds(groupIds);
    }

    @Override
    public List<Group> getGroupByIdList(List<Long> groupIdList) {
        if (CollectionUtils.isEmpty(groupIdList)) {
            return new ArrayList<>();
        }
        groupIdList = groupIdList.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
        if (groupIdList.isEmpty()) {
            return new ArrayList<>();
        }
        return groupMapper.getGroupByIdList(groupIdList);
    }
}
