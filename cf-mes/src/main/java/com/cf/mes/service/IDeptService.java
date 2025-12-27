package com.cf.mes.service;

import java.util.List;

import com.cf.mes.domain.Dept;

/**
 * 部门Service接口
 *
 * @author luorog
 */
public interface IDeptService {
    /**
     * 查询部门
     *
     * @param deptId 部门ID
     * @return 部门
     */
    Dept getDeptById(Long deptId);

    /**
     * 查询部门
     *
     * @param deptCode
     * @return
     */
    Dept getDeptByCode(String deptCode);

    /**
     * 查询部门列表
     *
     * @param dept 部门
     * @return 部门集合
     */
    List<Dept> getDeptList(Dept dept);

    /**
     * 新增部门
     *
     * @param dept 部门
     * @return 结果
     */
    int insertDept(Dept dept);

    /**
     * 修改部门
     *
     * @param dept 部门
     * @return 结果
     */
    int updateDept(Dept dept);

    /**
     * 批量删除部门
     *
     * @param deptIds 需要删除的部门ID
     * @return 结果
     */
    int deleteDeptByIds(Long[] deptIds);

    /**
     * 删除部门信息
     *
     * @param deptId 部门ID
     * @return 结果
     */
    int deleteDeptById(Long deptId);
}
