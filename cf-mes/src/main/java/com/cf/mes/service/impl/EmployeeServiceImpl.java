package com.cf.mes.service.impl;

import com.cf.common.annotation.Lang;
import com.cf.common.constant.MessageCode;
import com.cf.common.core.domain.entity.UploadFile;
import com.cf.common.core.minio.IEmployeeAvatarService;
import com.cf.common.core.minio.IUploadFileService;
import com.cf.common.core.token.ITokenService;
import com.cf.common.enums.LangInfoColumnName;
import com.cf.common.enums.LangInfoKeyFieldName;
import com.cf.common.enums.LangInfoTableName;
import com.cf.common.exception.CustomException;
import com.cf.common.utils.DateUtils;
import com.cf.common.utils.MessageUtils;
import com.cf.common.utils.StringUtils;
import com.cf.mes.domain.Employee;
import com.cf.mes.mapper.EmployeeMapper;
import com.cf.mes.service.IEmployeeService;
import com.cf.mes.service.IGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Description 员工业务接口实现
 * @Author ccw
 * @Date 2021/5/27
 */
@Service
@Lang(keyFieldName = LangInfoKeyFieldName.EMPLOYEE_ID_KEY_FILE_NAME, tableName = LangInfoTableName.EMPLOYEE_TABLE_NAME, columnNames = {LangInfoColumnName.EMPLOYEE_NAME_COLUMN_NAME})
public class EmployeeServiceImpl implements IEmployeeService, IEmployeeAvatarService {
    @Autowired
    private ITokenService tokenService;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private IGroupService groupService;


    @Autowired
    private IUploadFileService uploadFileService;

    /**
     * 查询员工信息
     *
     * @param employeeId 员工信息ID
     * @return 员工信息
     */
    @Override
    @Transactional(readOnly = true)
    public Employee getEmployeeById(Long employeeId) {
        Employee employee = employeeMapper.getEmployeeById(employeeId);
        if (employee != null) {
            employee.setGroupEntity(groupService.getGroupById(employee.getGroupId()));
            employee.setUploadFileEntity(uploadFileService.getUploadFileById(employee.getAvatar()));
        }
        return employee;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employee> getEmployeeByIds(Long[] employeeIds) {
        List<Employee> employeeList = employeeMapper.getEmployeeByIds(employeeIds);

//        for (Employee employeeItem:employeeList             ) {
//
//
//            if (employeeItem != null) {
//                employeeItem.setGroupEntity(groupService.getGroupById(employeeItem.getGroupId()));
//                employeeItem.setPostEntity(postService.getPostById(employeeItem.getPostId()));
//                employeeItem.setUploadFileEntity(uploadFileService.getUploadFileById(employeeItem.getAvatar()));
//            }
//        }

        return employeeList;
    }

    @Override
    public Employee getEmployeeByIdForJobOrderDetails(Long employeeId) {
        Employee employee = employeeMapper.getEmployeeById(employeeId);
        return employee;
    }

    @Override
    public Employee getEmployeeWithDifferentLang(Employee employee) {
        return employee;
    }

    /**
     * 查询员工信息
     *
     * @param employeeCode
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Employee getEmployeeByCode(String employeeCode) {
        Employee employee = employeeMapper.getEmployeeByCode(employeeCode);
        if (employee != null) {
            employee.setGroupEntity(groupService.getGroupById(employee.getGroupId()));
            ///employee.setPostEntity(postService.getPostById(employee.getPostId()));
            employee.setUploadFileEntity(uploadFileService.getUploadFileById(employee.getAvatar()));
        }
        return employee;
    }

    @Override
    public Employee getEmployeeByCardNo(String cardNo) {
        Employee employee = employeeMapper.getEmployeeByCardNo(cardNo);
        if (employee != null) {
            employee.setGroupEntity(groupService.getGroupById(employee.getGroupId()));
            //employee.setPostEntity(postService.getPostById(employee.getPostId()));
            employee.setUploadFileEntity(uploadFileService.getUploadFileById(employee.getAvatar()));
        }
        return employee;
    }

    /**
     * 查询员工信息列表
     *
     * @param employee 员工信息
     * @return 员工信息集合
     */
    @Override
    @Transactional(readOnly = true)
    public List<Employee> getEmployeeList(Employee employee) {
        List<Employee> employeeList = employeeMapper.getEmployeeList(employee);
        for (Employee employeeItem : employeeList) {
            employeeItem.setGroupEntity(groupService.getGroupById(employeeItem.getGroupId()));
            //employeeItem.setPostEntity(postService.getPostById(employeeItem.getPostId()));
            employeeItem.setUploadFileEntity(uploadFileService.getUploadFileById(employeeItem.getAvatar()));
        }
        return employeeList;
    }

    @Override
    public List<Employee> getBirthdayEmployeeList(Date birthday) {
        List<Employee> employeeList = employeeMapper.getBirthdayEmployeeList(birthday);
        if (CollectionUtils.isEmpty(employeeList)) {
            return new ArrayList<>();
        }
        ///List<Long> groupIdList = employeeList.stream().map(Employee::getGroupId).collect(Collectors.toList());
        ///List<Long> postIdList = employeeList.stream().map(Employee::getPostId).collect(Collectors.toList());
        List<String> fileIdList = employeeList.stream().map(Employee::getAvatar).collect(Collectors.toList());

        ///List<Group> groups = groupService.getGroupByIdList(groupIdList);
        ///List<Post> posts = postService.getPostByIdList(postIdList);
        List<UploadFile> uploadFiles = uploadFileService.getUploadFileListByIds(fileIdList);

        for (Employee employeeItem : employeeList) {
            ///groups.stream().filter(r -> r.getGroupId().equals(employeeItem.getGroupId())).findFirst().ifPresent(employeeItem::setGroupEntity);
            ///posts.stream().filter(r -> r.getPostId().equals(employeeItem.getPostId())).findFirst().ifPresent(employeeItem::setPostEntity);
            uploadFiles.stream().filter(r -> r.getUploadFileId().equals(employeeItem.getAvatar())).findFirst().ifPresent(employeeItem::setUploadFileEntity);
        }
        return employeeList;
    }

    @Override
    public List<Employee> getI18nEmployeeList(List<Employee> employeeList) {
        return employeeList;
    }

    @Override
    public List<Employee> getEmployeeListByIds(List<Long> employeeIds) {
        if (CollectionUtils.isEmpty(employeeIds)) {
            return new ArrayList<>();
        }
        employeeIds = employeeIds.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
        if (employeeIds.isEmpty()) {
            return new ArrayList<>();
        }
        return employeeMapper.getEmployeeListByIds(employeeIds);
    }

    /**
     * 新增员工信息
     *
     * @param employee 员工信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertEmployee(Employee employee) {
        //员工编号不能为空
        if (StringUtils.isEmpty(employee.getEmployeeCode())) {
            String msg = MessageUtils.message(MessageCode.EMPLOYEE_EMPLOYEE_CODE_EMPTY);
            throw new CustomException(msg);
        }
        //员工卡号不能为空
        if (StringUtils.isEmpty(employee.getCardNo())) {
            String msg = MessageUtils.message(MessageCode.EMPLOYEE_EMPLOYEE_CARD_NO_EMPTY);
            throw new CustomException(msg);
        }
        //员工编号唯一
        Employee checkObj = employeeMapper.getEmployeeByCode(employee.getEmployeeCode());
        if (checkObj != null) {
            String msg = MessageUtils.message(MessageCode.EMPLOYEE_EMPLOYEE_CODE_EXISTS, checkObj.getEmployeeCode());
            throw new CustomException(msg);
        }
        //在职员工卡号唯一
        Employee checkCardNo = employeeMapper.getEmployeeByCardNo(employee.getCardNo());
        if (checkCardNo != null) {
            String msg = MessageUtils.message(MessageCode.EMPLOYEE_EMPLOYEE_CARD_NO_EXISTS, employee.getCardNo());
            throw new CustomException(msg);
        }
        employee.setCreateBy(tokenService.getUserCode());
        employee.setCreateTime(DateUtils.getNowDate());
        return employeeMapper.insertEmployee(employee);
    }

    /**
     * 修改员工信息
     *
     * @param employee 员工信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateEmployee(Employee employee) {
        //员工卡号不能为空
        if (StringUtils.isEmpty(employee.getCardNo())) {
            String msg = MessageUtils.message(MessageCode.EMPLOYEE_EMPLOYEE_CARD_NO_EMPTY);
            throw new CustomException(msg);
        }
        //在职员工卡号唯一
        Employee checkCardNo = employeeMapper.getEmployeeByCardNo(employee.getCardNo());
        if (checkCardNo != null) {
            //不包含自身
            if (!employee.getEmployeeId().equals(checkCardNo.getEmployeeId())) {
                String msg = MessageUtils.message(MessageCode.EMPLOYEE_EMPLOYEE_CARD_NO_EXISTS, employee.getCardNo());
                throw new CustomException(msg);
            }
        }
        employee.setUpdateBy(tokenService.getUserCode());
        employee.setUpdateTime(DateUtils.getNowDate());
        return employeeMapper.updateEmployee(employee);
    }

    @Override
    public int updateEmployeeByAvatar(String avatar) {
        return employeeMapper.updateEmployeeByAvatar(avatar);
    }

    /**
     * 删除员工信息
     *
     * @param employeeId 员工信息ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteEmployeeById(Long employeeId) {
        return employeeMapper.deleteEmployeeById(employeeId);
    }

    /**
     * 批量删除员工信息
     *
     * @param employeeIds 需要删除的数据ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteEmployeeByIds(Long[] employeeIds) {
        return employeeMapper.deleteEmployeeByIds(employeeIds);
    }

    @Override
    public Employee getEmployeeWithAvatarById(Long employeeId) {
        Employee employee = employeeMapper.getEmployeeById(employeeId);
        if (employee != null) {
            employee.setUploadFileEntity(uploadFileService.getUploadFileById(employee.getAvatar()));
        }
        return employee;
    }
}
