package com.cf.mes.domain;

import com.cf.common.constant.MessageCode;
import com.cf.common.core.domain.BaseEntity;
import com.cf.common.core.domain.entity.UploadFile;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @Description 员工实体
 * @Author ccw
 * @Date 2021/5/27
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Employee extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 员工ID
     */
    private Long employeeId;

    /**
     * 员工编码
     */
    @NotBlank(message = MessageCode.EMPLOYEE_EMPLOYEE_CARD_NO_EMPTY)
    private String employeeCode;

    /**
     * 员工名称
     */
    private String employeeName;

    /**
     * 卡号
     */
    @NotBlank(message = MessageCode.EMPLOYEE_EMPLOYEE_CARD_NO_EMPTY)
    private String cardNo;

    /**
     * 头像路径
     */
    private String avatar;

    /**
     * 头像上传记录
     */
    private UploadFile uploadFileEntity;

    /**
     * 组别Id
     */
    private Long groupId;

    /**
     * 组别实体
     */
    private Group groupEntity;

    /**
     * 职位Id
     */
    private Long postId;

    /**
     * 职位实体
     */
    //private Post postEntity;

    /**
     * 电话
     */
    private String phone;

    /**
     * 邮件
     */
    private String email;

    /**
     * 职级
     */
    private String jobPositionRank;

    /**
     * 岗位职责
     */
    private String jobPositionResponsibility;

    /**
     * 状态
     */
    private String status;

    /**
     * 生日
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    /**
     * 性别
     */
    private String sex;

}
