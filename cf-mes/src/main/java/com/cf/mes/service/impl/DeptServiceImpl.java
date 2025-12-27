package com.cf.mes.service.impl;

import com.cf.common.annotation.Lang;
import com.cf.common.constant.MessageCode;
import com.cf.common.enums.LangInfoColumnName;
import com.cf.common.enums.LangInfoKeyFieldName;
import com.cf.common.enums.LangInfoTableName;
import com.cf.common.exception.CustomException;
import com.cf.common.utils.DateUtils;
import com.cf.common.utils.MessageUtils;
import com.cf.common.utils.StringUtils;
import com.cf.mes.domain.Dept;
import com.cf.mes.mapper.DeptMapper;
import com.cf.mes.service.IDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 部门Service业务层处理
 *
 * @author luorog
 */
@Service
@Lang(keyFieldName = LangInfoKeyFieldName.DEPT_ID_KEY_FILE_NAME, tableName = LangInfoTableName.DEPT_TABLE_NAME, columnNames = {LangInfoColumnName.DEPT_NAME_COLUMN_NAME})
public class DeptServiceImpl implements IDeptService {
    @Autowired
    private DeptMapper deptMapper;

    /**
     * 查询部门
     *
     * @param deptId 部门ID
     * @return 部门
     */
    @Override
    public Dept getDeptById(Long deptId) {
        return deptMapper.getDeptById(deptId);
    }


    @Override
    public Dept getDeptByCode(String deptCode) {
        return deptMapper.getDeptByCode(deptCode);
    }


    /**
     * 查询部门列表
     *
     * @param dept 部门
     * @return 部门
     */
    @Override
    public List<Dept> getDeptList(Dept dept) {
        return deptMapper.getDeptList(dept);
    }

    /**
     * 新增部门
     *
     * @param dept 部门
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertDept(Dept dept) {
        //check data
        if (StringUtils.isEmpty(dept.getDeptCode())) {
            String msg = MessageUtils.message(MessageCode.DEPT_DEPT_CODE_EMPTY);
            throw new CustomException(msg);
        }
        Dept checkObj = this.getDeptByCode(dept.getDeptCode());
        if (checkObj != null) {
            String msg = MessageUtils.message(MessageCode.DEPT_DEPT_CODE_EXISTS, checkObj.getDeptCode());
            throw new CustomException(msg);
        }
        dept.setCreateTime(DateUtils.getNowDate());
        return deptMapper.insertDept(dept);
    }

    /**
     * 修改部门
     *
     * @param dept 部门
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateDept(Dept dept) {
        dept.setUpdateTime(DateUtils.getNowDate());
        return deptMapper.updateDept(dept);
    }

    /**
     * 批量删除部门
     *
     * @param deptIds 需要删除的部门ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteDeptByIds(Long[] deptIds) {
        return deptMapper.deleteDeptByIds(deptIds);
    }

    /**
     * 删除部门信息
     *
     * @param deptId 部门ID
     * @return 结果
     */
    @Override

    @Transactional(rollbackFor = Exception.class)
    public int deleteDeptById(Long deptId) {
        return deptMapper.deleteDeptById(deptId);
    }
}
