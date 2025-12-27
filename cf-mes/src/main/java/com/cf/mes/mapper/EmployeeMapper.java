package com.cf.mes.mapper;

import com.cf.mes.domain.Employee;

import java.util.Date;
import java.util.List;

/**
 * @Description 员工数据接口
 * @Author ccw
 * @Date 2021/5/27
 */
public interface EmployeeMapper {
    /**
     * 查询员工信息
     *
     * @param employeeId 员工信息ID
     * @return 员工信息
     */
    Employee getEmployeeById(Long employeeId);


    List<Employee> getEmployeeByIds(Long[] employeeIds);

    /**
     * 查询员工信息
     *
     * @param employeeCode
     * @return
     */
    Employee getEmployeeByCode(String employeeCode);

    /**
     * 根据卡号查询员工信息
     *
     * @param cardNo
     * @return
     */
    Employee getEmployeeByCardNo(String cardNo);

    /**
     * 查询员工信息列表
     *
     * @param employee 员工信息
     * @return 员工信息集合
     */
    List<Employee> getEmployeeList(Employee employee);

    /**
     * 新增员工信息
     *
     * @param employee 员工信息
     * @return 结果
     */
    int insertEmployee(Employee employee);

    /**
     * 修改员工信息
     *
     * @param employee 员工信息
     * @return 结果
     */
    int updateEmployee(Employee employee);

    int updateEmployeeByAvatar(String avatar);

    /**
     * 删除员工信息
     *
     * @param employeeId 员工信息ID
     * @return 结果
     */
    int deleteEmployeeById(Long employeeId);

    /**
     * 批量删除员工信息
     *
     * @param employeeIds 需要删除的数据ID
     * @return 结果
     */
    int deleteEmployeeByIds(Long[] employeeIds);

    List<Employee> getEmployeeListByIds(List<Long> employeeIds);

    List<Employee> getBirthdayEmployeeList(Date birthday);
}
