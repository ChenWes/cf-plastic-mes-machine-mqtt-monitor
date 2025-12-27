package com.cf.common.constant;

/**
 * @Description
 * @Author ccw
 * @Date 2021/4/30
 */
public class MessageCode {
    //权限验证消息

    /**
     * 身份验证失败
     */
    public static final String AUTHENTICATION_ERROR = "authentication.error";

    /**
     * 没有权限，请联系管理员授权
     */
    public static final String AUTHORIZATION_FORBIDDEN = "authorization.forbidden";

    //用户相关
    /**
     * 用户不存在/密码错误
     */
    public static final String USER_PASSWORD_NOT_MATCH = "user.password.not.match";

    /**
     * 用户编号不能为空
     */
    public static final String USER_USER_CODE_EMPTY = "user.user_code.empty";

    /**
     * 用户编号已存在系统中
     */
    public static final String USER_USER_CODE_EXISTS = "user.user_code.exists";

    /**
     * 用户ID无效
     */
    public static final String USER_USER_ID_INVALID = "user.user_id.invalid";

    /**
     * 验证码已失效
     */
    public static final String USER_CAPTCHA_EXPIRE = "user.captcha.expire";

    //文件上传消息
    /**
     * 上传的文件大小超出限制的文件大小！
     */
    public static final String UPLOAD_EXCEED_MAXSIZE = "upload.exceed.maxSize";

    /**
     * 上传的文件名最长{0}个字符
     */
    public static final String UPLOAD_FILENAME_EXCEED_LENGTH = "upload.filename.exceed.length";

    /**
     * 上传文件格式错误
     */
    public static final String UPLOAD_FILE_FORMAT_ERROR = "upload.file.format.error";

    //操作结果
    /**
     * 操作成功
     */
    public static final String OPERATION_SUCCESS = "operation.success";

    /**
     * 操作失败
     */
    public static final String OPERATION_ERROR = "operation.error";

    //sql
    /**
     * 参数不符合规范
     */
    public static final String OPERATION_INVALID_PARAMETER = "operation.invalid.parameter";

    //角色
    /**
     * 角色编号不能为空
     */
    public static final String ROLE_ROLE_CODE_EMPTY = "role.role_code.empty";

    /**
     * 角色编号已存在系统中
     */
    public static final String ROLE_ROLE_CODE_EXISTS = "role.role_code.exists";


    //系统多语言
    /**
     * 系统中已存在表名称,字段名称,记录ID,语言相同的记录
     */
    public static final String LANG_INFO_KEY_EXISTS = "lang_info.key.exists";

    /**
     * 语言表名称不能为空
     */
    public static final String LANG_INFO_TABLE_NAME_EMPTY = "lang_info.table_name.empty";

    /**
     * 字段名称不能为空
     */
    public static final String LANG_INFO_COLUMN_NAME_EMPTY = "lang_info.column_name.empty";

    /**
     * 记录Id不能为空
     */
    public static final String LANG_INFO_RECORD_ID_EMPTY = "lang_info.record_id.empty";

    /**
     * 语言不能为空
     */
    public static final String LANG_INFO_LANG_EMPTY = "lang_info.lang.empty";

    //菜单
    /**
     * 菜单编号不能为空
     */
    public static final String MENU_MENU_CODE_EMPTY = "menu.menu_code.empty";

    /**
     * 菜单编号已存在系统中
     */
    public static final String MENU_MENU_CODE_EXISTS = "menu.menu_code.exists";

    /**
     * 父菜单不能为自己
     */
    public static final String MENU_PARENT_ID_ITSELF = "menu.parent_id.itself";

    /**
     * 父菜单不能为自己子菜单
     */
    public static final String MENU_PARENT_ID_CHILD = "menu.parent_id.child";

    //权限
    /**
     * 权限编号不能为空
     */
    public static final String PERMISSION_PERMISSION_CODE_EMPTY = "permission.permission_code.empty";

    /**
     * 权限编号已存在系统中
     */
    public static final String PERMISSION_PERMISSION_CODE_EXISTS = "permission.permission_code.exists";

    //字典
    /**
     * 字典编码不能为空
     */
    public static final String DIC_DIC_CODE_EMPTY = "dic.dic_code.empty";

    /**
     * 字典编码已存在系统中
     */
    public static final String DIC_DIC_CODE_EXISTS = "dic.dic_code.exists";

    /**
     * 字典类型
     */
    public static final String DIC_DIC_TYPE_NAME = "dic_dic_type_name";

    /**
     * 字典编码
     */
    public static final String DIC_DIC_CODE_NAME = "dic_dic_code_name";

    /**
     * 字典名称
     */
    public static final String DIC_DIC_NAME_NAME = "dic_dic_name_name";

    /**
     * 字典键值
     */
    public static final String DIC_DIC_VALUE_NAME = "dic_dic_value_name";

    /**
     * 顺序号
     */
    public static final String DIC_SEQ_NO_NAME = "dic_seq_no_name";

    /**
     * 状态
     */
    public static final String DIC_STATUS_NAME = "dic_status_name";

    /**
     * 父节点ID
     */
    public static final String DIC_PARENT_ID_NAME = "dic_parent_id_name";

    //任务
    /**
     * cron格式不正确
     */
    public static final String JOB_CRON_EXPRESSION_FORMAT_ERROR = "job.cron_expression.format.error";

    //部门
    /**
     * 部门编码不能为空
     */
    public static final String DEPT_DEPT_CODE_EMPTY = "dept.dept_code.empty";

    /**
     * 部门编码已存在系统中
     */
    public static final String DEPT_DEPT_CODE_EXISTS = "dept.dept_code.exists";

    //工厂
    /**
     * 工厂编码不能为空
     */
    public static final String FACTORY_FACTORY_CODE_EMPTY = "factory.factory_code.empty";

    /**
     * 工厂编码已存在系统中
     */
    public static final String FACTORY_FACTORY_CODE_EXISTS = "factory.factory_code.exists";

    /**
     * 工厂不能删除
     */
    public static final String FACTORY_FACTORY_NOT_DELETE = "factory.factory.not.delete";

    //岗位
    /**
     * 岗位编码不能为空
     */
    public static final String POST_POST_CODE_EMPTY = "post.post_code.empty";

    /**
     * 岗位编码已存在系统中
     */
    public static final String POST_POST_CODE_EXISTS = "post.post_code.exists";

    //车间
    /**
     * 车间编码不能为空
     */
    public static final String WORKSHOP_WORKSHOP_CODE_EMPTY = "workshop.workshop_code.empty";

    /**
     * 车间编码已存在系统中
     */
    public static final String WORKSHOP_WORKSHOP_CODE_EXISTS = "workshop.workshop_code.exists";

    /**
     * 车间不能为空
     */
    public static final String WORKSHOP_WORKSHOP_ID_EMPTY = "workshop.workshop_id.empty";

    /**
     * 工厂不能删除
     */
    public static final String WORKSHOP_WORKSHOP_NOT_DELETE = "workshop.workshop.not.delete";

    //组别
    /**
     * 组别编码不能为空
     */
    public static final String GROUP_GROUP_CODE_EMPTY = "group.group_code.empty";

    /**
     * 组别编码已存在系统中
     */
    public static final String GROUP_GROUP_CODE_EXISTS = "group.group_code.exists";

    //机器状态
    /**
     * 机器状态编码不能为空
     */
    public static final String MACHINE_STATUS_CODE_EMPTY = "machine_status.machine_status_code.empty";

    /**
     * 机器状态编码已存在系统中
     */
    public static final String MACHINE_STATUS_CODE_EXISTS = "machine_status.machine_status_code.exists";

    //班次
    /**
     * 班次编码不能为空
     */
    public static final String WORK_SHIFT_CODE_EMPTY = "work_shift.work_shift_code.empty";

    /**
     * 班次编码已存在系统中
     */
    public static final String WORK_SHIFT_CODE_EXISTS = "work_shift.work_shift_code.exists";

    //机器
    /**
     * 机器编码不能为空
     */
    public static final String MACHINE_MACHINE_CODE_EMPTY = "machine.machine_code.empty";

    /**
     * 机器编码已存在系统中
     */
    public static final String MACHINE_MACHINE_CODE_EXISTS = "machine.machine_code.exists";

    //机器类型
    /**
     * 机器类型编码不能为空
     */
    public static final String MACHINE_TYPE_CODE_EMPTY = "machine_type.machine_type_code.empty";

    /**
     * 机器类型编码已存在系统中
     */
    public static final String MACHINE_TYPE_CODE_EXISTS = "machine_type.machine_type_code.exists";

    /**
     * 机器类型不能删除
     */
    public static final String MACHINE_TYPE_NOT_DELETE = "machine_type.machine_type.not.delete";

    /**
     * 机器类型id不能为空
     */
    public static final String MACHINE_TYPE_MACHINE_TYPE_ID_EMPTY = "machine_type.machine_type_id.empty";

    //工序
    /**
     * 工序编码不能为空
     */
    public static final String PROCESS_CODE_EMPTY = "process.process_code.empty";

    /**
     * 工序编码已存在系统中
     */
    public static final String PROCESS_CODE_EXISTS = "process.process_code.exists";

    //员工
    /**
     * 员工编号不能为空
     */
    public static final String EMPLOYEE_EMPLOYEE_CODE_EMPTY = "employee.employee_code.empty";
    /**
     * 员工编号已存在系统中
     */
    public static final String EMPLOYEE_EMPLOYEE_CODE_EXISTS = "employee.employee_code.exists";
    /**
     * 员工卡号不能为空
     */
    public static final String EMPLOYEE_EMPLOYEE_CARD_NO_EMPTY = "employee.employee_card_no.empty";
    /**
     * 员工卡号已存在系统中
     */
    public static final String EMPLOYEE_EMPLOYEE_CARD_NO_EXISTS = "employee.employee_card_no.exists";


    /**
     * 通过员工卡号找不到员工
     */
    public static final String EMPLOYEE_EMPLOYEE_CAN_NOT_FOUND_BY_CARD_NO = "employee.employee_can_not_found_by_card_no";


    /**
     * 通过机器ID找不到机器
     */
    public static final String MACHINE_MACHINE_CAN_NOT_FOUND_BY_MACHINE_ID = "machine.machine_can_not_found_by_machine_id";

    /**
     * 插入相同的机器状态日志
     */
    public static final String MACHINE_STATUS_LOG_SAME_WITH_LATEST = "machine_status_log.data_same_with_latest";
    /**
     * 插入相同的机器状态日志
     */
    public static final String MACHINE_STATUS_LOG_START_TIME_BEFORE_WITH_LATEST = "machine_status_log.data_start_time_before_with_latest";

    //疵点
    /**
     * 疵点编号不能为空
     */
    public static final String DEFECT_DEFECT_CODE_EMPTY = "defect.defect_code_empty";

    /**
     * 疵点编号重复
     */
    public static final String DEFECT_DEFECT_CODE_EXISTS = "defect.defect_code_exists";

    //模具
    /**
     * 模具编号不能为空
     */
    public static final String MOULD_MOULD_CODE_EMPTY = "mould.mould_code_empty";

    /**
     * 模具编号重复
     */
    public static final String MOULD_MOULD_CODE_EXISTS = "mould.mould_code_exists";

    //产品
    /**
     * 模具编号不能为空
     */
    public static final String PRODUCT_PRODUCT_CODE_EMPTY = "product.product_code_empty";

    /**
     * 模具编号重复
     */
    public static final String PRODUCT_PRODUCT_CODE_EXISTS = "product.product_code_exists";

    //物料
    /**
     * 物料编号不能为空
     */
    public static final String MATERIAL_MATERIAL_CODE_EMPTY = "material.material_code_empty";

    /**
     * 物料编号重复
     */
    public static final String MATERIAL_MATERIAL_CODE_EXISTS = "material.material_code_exists";


    //工单
    /**
     * 工单ID为空
     */
    public static final String JOB_ORDER_JOB_ORDER_CODE_EMPTY = "jobOrder.job_order_code_empty";

    /**
     * 工单Number为空
     */
    public static final String JOB_ORDER_JOB_ORDER_NUMBER_EMPTY = "jobOrder.job_order_number_empty";

    /**
     * 工单ID重复
     */
    public static final String JOB_ORDER_JOB_ORDER_CODE_EXISTS = "jobOrder.job_order_code_exists";

    /**
     * 工单Number重复
     */
    public static final String JOB_ORDER_JOB_ORDER_NUMBER_EXISTS = "jobOrder.job_order_number_exists";
    /**
     * 工单检查状态不能删除
     */
    public static final String JOB_ORDER_JOB_ORDER_CHECK_STATUS_CAN_NOT_DELETE = "jobOrder.job_order_check_status_can_not_delete";


    /**
     * 工单上传数据检查
     */
    public static final String JOB_ORDER_JOB_ORDER_UPLOAD_CHECK_DATA = "jobOrder.upload_data_check";


    /**
     * 工单上传产品编号不存在
     */
    public static final String JOB_ORDER_JOB_ORDER_UPLOAD_PRODUCT_CODE_EMPTY = "jobOrder.upload_data_product_code_empty";

    /**
     * 工单上传产品编号不存在
     */
    public static final String JOB_ORDER_JOB_ORDER_UPLOAD_PRODUCT_CODE_NO_EXISTS = "jobOrder.upload_data_product_code_no_exists";


    /**
     * 工单上传工单ID为空
     */
    public static final String JOB_ORDER_JOB_ORDER_UPLOAD_JOB_ORDER_CODE_EMPTY = "jobOrder.upload_data_job_order_code_empty";

    /**
     * 工单上传工单ID已存在
     */
    public static final String JOB_ORDER_JOB_ORDER_UPLOAD_JOB_ORDER_CODE_EXISTS = "jobOrder.upload_data_job_order_code_exists";

    /**
     * 工单上传工单Number为空
     */
    public static final String JOB_ORDER_JOB_ORDER_UPLOAD_JOB_ORDER_NUMBER_EMPTY = "jobOrder.upload_data_job_order_number_empty";

    /**
     * 工单上传工单number已存在
     */
    public static final String JOB_ORDER_JOB_ORDER_UPLOAD_JOB_ORDER_NUMBER_EXISTS = "jobOrder.upload_data_job_order_number_exists";

    /**
     * 工单上传订单数量为空
     */
    public static final String JOB_ORDER_JOB_ORDER_UPLOAD_ORDER_QTY_EMPTY = "jobOrder.upload_data_order_qty_empty";

    /**
     * 工单上传计划完成日期为空
     */
    public static final String JOB_ORDER_JOB_ORDER_UPLOAD_PLAN_FINISH_DATE_EMPTY = "jobOrder.upload_data_plan_finish_date_empty";

    /**
     * 产品生产指引，状态已经是确认
     */
    public static final String INSTRUCTION_INSTRUCTION_CONTROL_STATUS_IF_CONFIRM = "instruction.control_status_is_confirm";

    //班次
    /**
     * 疵点编号不能为空
     */
    public static final String SHIFT_SHIFT_CODE_EMPTY = "shift.shift_code_empty";

    /**
     * 疵点编号重复
     */
    public static final String SHIFT_SHIFT_CODE_EXISTS = "shift.shift_code_exists";


    //班次组
    /**
     * 疵点编号不能为空
     */
    public static final String SHIFT_GROUP_SHIFT_GROUP_CODE_EMPTY = "shiftGroup.shift_group_code_empty";

    /**
     * 疵点编号重复
     */
    public static final String SHIFT_GROUP_SHIFT_GROUP_CODE_EXISTS = "shiftGroup.shift_group_code_exists";

    /**
     * 版本号重复
     */
    public static final String WINDOWS_CLIENT_VERSION_CONTROL_VERSION_CODE_EXISTS = "windowsClientVersionControl.version_code_exists";


    //车间负责人
    /**
     * 车间ID不能为空
     */
    public static final String WORKSHOP_RESPONSIBLE_PERSON_WORKSHOP_ID_EMPTY = "WorkshopResponsiblePerson.workshop_id_empty";

    /**
     * 车间ID已存在
     */
    public static final String WORKSHOP_RESPONSIBLE_PERSON_WORKSHOP_ID_EXISTS = "WorkshopResponsiblePerson.workshop_id_exists";


    //机器负责人
    /**
     * 机器ID不能为空
     */
    public static final String MACHINE_RESPONSIBLE_PERSON_MACHINE_ID_EMPTY = "MachineResponsiblePerson.machine_id_empty";

    /**
     * 员工ID不能为空
     */
    public static final String MACHINE_RESPONSIBLE_PERSON_EMPLOYEE_ID_EMPTY = "MachineResponsiblePerson.employee_id_empty";

    /**
     * 车间ID已存在
     */
    public static final String MACHINE_RESPONSIBLE_PERSON_MACHINE_ID_EXISTS = "MachineResponsiblePerson.machine_id_exists";


    //员工排班
    /**
     * 员工ID不能为空
     */
    public static final String EMPLOYEE_WORK_SCHEDULE_EMPLOYEE_ID_EMPTY = "EmployeeWorkSchedule.employee_id_empty";

    /**
     * 排班日期不能为空
     */
    public static final String EMPLOYEE_WORK_SCHEDULE_DATE_EMPTY = "EmployeeWorkSchedule.date_empty";

    /**
     * 班次ID不能为空
     */
    public static final String EMPLOYEE_WORK_SCHEDULE_SHIFT_ID_EMPTY = "EmployeeWorkSchedule.shift_id_empty";


    //员工排班上传
    /**
     * 工单上传数据检查
     */
    public static final String EMPLOYEE_WORK_SCHEDULE_UPLOAD_CHECK_DATA = "EmployeeWorkScheduleUpload.upload_data_check";
    /**
     * 员工ID不存在
     */
    public static final String EMPLOYEE_WORK_SCHEDULE_UPLOAD_EMPLOYEE_CODE_NOT_EXISTS = "EmployeeWorkScheduleUpload.employee_code_not_exists";

    /**
     * 班次Code不存在
     */
    public static final String EMPLOYEE_WORK_SCHEDULE_UPLOAD_SHIFT_CODE_NOT_EXISTS = "EmployeeWorkScheduleUpload.shift_code_not_exists";

    //员工签到
    /**
     * 员工签到机器ID为空
     */
    public static final String EMPLOYEE_SCAN_CARD_INFO_MACHINE_ID_EMPTY = "EmployeeScanCardInfo.machine_id_empty";

    /**
     * 员工签到员工ID为空
     */
    public static final String EMPLOYEE_SCAN_CARD_INFO_EMPLOYEE_ID_EMPTY = "EmployeeScanCardInfo.employee_id_empty";

    /**
     * 员工签到记录ID为空
     */
    public static final String EMPLOYEE_SCAN_CARD_INFO_EMPLOYEE_SCAN_CARD_INFO_ID_EMPTY = "EmployeeScanCardInfo.employee_scan_card_info_id_empty";


    //QC签到
    /**
     * QC签到机器ID为空
     */
    public static final String QC_SCAN_CARD_INFO_MACHINE_ID_EMPTY = "QCScanCardInfo.machine_id_empty";

    /**
     * QC签到员工ID为空
     */
    public static final String QC_SCAN_CARD_INFO_EMPLOYEE_ID_EMPTY = "QCScanCardInfo.employee_id_empty";

    /**
     * QC签到记录ID为空
     */
    public static final String QC_SCAN_CARD_INFO_QC_SCAN_CARD_INFO_ID_EMPTY = "QCScanCardInfo.qc_scan_card_info_id_empty";


    //客户端命令类型
    /**
     * 客户端命令类型编号不能为空
     */
    public static final String CLIENT_COMMAND_TYPE_CLIENT_COMMAND_TYPE_CODE_EMPTY = "clientCommandType.client_command_type_code_empty";

    /**
     * 客户端命令类型编号重复
     */
    public static final String CLIENT_COMMAND_TYPE_CLIENT_COMMAND_TYPE_CODE_EXISTS = "clientCommandType.client_command_type_code_exists";

    /**
     * 客户端命令类型编号不能为空
     */
    public static final String CLIENT_COMMAND_TYPE_CLIENT_COMMAND_TYPE_NAME_EMPTY = "clientCommandType.client_command_type_name_empty";


    //客户端命令
    /**
     * 客户端命令生效时间不能为空
     */
    public static final String CLIENT_COMMAND_ACTIVE_TIME_EMPTY = "clientCommand.active_time_empty";

    /**
     * 客户端命令客户端ID列表不能为空
     */
    public static final String CLIENT_COMMAND_CLIENT_ID_LIST_EMPTY = "clientCommand.client_id_list_empty";


    /**
     * 安卓客户端版本号重复
     */
    public static final String ANDROID_CLIENT_VERSION_CONTROL_VERSION_CODE_EXISTS = "androidClientVersionControl.version_code_exists";

    /**
     * 二维码已过期
     */
    public static final String QR_CODE_EXPIRED = "qr.code.expired";


    /**
     * 模具上传数据检查
     */
    public static final String MOULD_UPLOAD_CHECK_DATA = "mould.upload_data_check";

    /**
     * 模具上传模具编号不存在
     */
    public static final String MOULD_UPLOAD_MOULD_CODE_EMPTY = "mould.upload_data_mould_code_empty";

    /**
     * 模具上传模具编号已存在数据库
     */
    public static final String MOULD_UPLOAD_MOULD_CODE_EXISTS = "mould.upload_data_mould_code_exists";

    /**
     * 模具上传模具名称不存在
     */
    public static final String MOULD_UPLOAD_MOULD_NAME_EMPTY = "mould.upload_data_mould_name_empty";

    /**
     * 模具上传模具默认生命周期不存在
     */
    public static final String MOULD_UPLOAD_MOULD_STANDARD_PRODUCE_TIME_EMPTY = "mould.upload_data_mould_standard_produce_time_empty";


    /**
     * 产品上传数据检查
     */
    public static final String PRODUCT_UPLOAD_CHECK_DATA = "product.upload_data_check";

    /**
     * 产品上传产品编号不存在
     */
    public static final String PRODUCT_UPLOAD_PRODUCT_CODE_EMPTY = "product.upload_data_product_code_empty";

    /**
     * 产品上传产品编号已存在数据库
     */
    public static final String PRODUCT_UPLOAD_PRODUCT_CODE_EXISTS = "product.upload_data_product_code_exists";

    /**
     * 产品上传产品名称不存在
     */
    public static final String PRODUCT_UPLOAD_PRODUCT_NAME_EMPTY = "product.upload_data_product_name_empty";


    /**
     * 物料上传数据检查
     */
    public static final String MATERIAL_UPLOAD_CHECK_DATA = "material.upload_data_check";

    /**
     * 物料上传物料编号不存在
     */
    public static final String MATERIAL_UPLOAD_MATERIAL_CODE_EMPTY = "material.upload_data_material_code_empty";

    /**
     * 物料上传物料编号已存在数据库
     */
    public static final String MATERIAL_UPLOAD_MATERIAL_CODE_EXISTS = "material.upload_data_material_code_exists";

    /**
     * 物料上传物料名称不存在
     */
    public static final String MATERIAL_UPLOAD_MATERIAL_NAME_EMPTY = "material.upload_data_material_name_empty";


    /**
     * *********************************************
     */

    /**
     * 模具上传数据检查
     */
    public static final String MOULD_PRODUCT_MAP_UPLOAD_CHECK_DATA = "mould_product_map.upload_data_check";

    /**
     * 模具产品出数批量上传模具编号不存在
     */
    public static final String MOULD_PRODUCT_MAP_UPLOAD_MOULD_CODE_EMPTY = "mould_product_map.upload_data_mould_code_empty";

    /**
     * 模具产品出数批量上传模具标准生命周期不存在
     */
    public static final String MOULD_PRODUCT_MAP_UPLOAD_MOULD_STANDARD_PRODUCE_TIME_EMPTY = "mould_product_map.upload_data_mould_standard_produce_time_empty";

    /**
     * 模具产品出数批量上传产品编号不存在
     */
    public static final String MOULD_PRODUCT_MAP_UPLOAD_PRODUCT_CODE_EMPTY = "mould_product_map.upload_data_product_code_empty";

    /**
     * 模具产品出数批量上传产品组别不存在
     */
    public static final String MOULD_PRODUCT_MAP_UPLOAD_PRODUCT_GROUP_EMPTY = "mould_product_map.upload_data_product_group_empty";

    /**
     * 模具产品出数批量上传产品出数为零或不存在
     */
    public static final String MOULD_PRODUCT_MAP_UPLOAD_PRODUCT_COUNT_EMPTY = "mould_product_map.upload_data_product_count_empty";


    /**
     * 自动注册，随机码过期
     */
    public static final String AUTO_REGISTER_RANDOM_CODE_EXPIRED = "auto_register.random_code_expired";


    /**
     * 员工签到记录机器ID不存在
     */
    public static final String EMPLOYEE_SIGN_IN_LOG_MACHINE_ID_EMPTY = "employee_sign_in_log.machine_id_empty";

    /**
     * 员工签到记录员工ID不存在
     */
    public static final String EMPLOYEE_SIGN_IN_LOG_EMPLOYEE_ID_EMPTY = "employee_sign_in_log.employee_id_empty";

    /**
     * 员工签到记录模具编号不存在
     */
    public static final String EMPLOYEE_SIGN_IN_LOG_MOULD_CODE_EMPTY = "employee_sign_in_log.mould_code_empty";

    //客户端权限
    /**
     * 客户端权限编码不能为空
     */
    public static final String CLIENT_PERMISSION_CODE_EMPTY = "client_permission.client_permission_code.empty";

    /**
     * 客户端权限编码已存在系统中
     */
    public static final String CLIENT_PERMISSION_CODE_EXISTS = "client_permission.client_permission_code.exists";

    /**
     * 客户端权限不能删除
     */
    public static final String CLIENT_PERMISSION_NOT_DELETE = "client_permission.client_permission.not.delete";


    //工单生产首检
    /**
     * 不能首检，因为有送检还未完成
     */
    public static final String JOB_ORDER_PRODUCTION_FIRST_INSPECTION_HAS_SEND_INSPECTION_NO_RESULT = "job_order_production_first_inspection.has_send_inspection_no_result";

    /**
     * 不能接收，因为还没有送检
     */
    public static final String JOB_ORDER_PRODUCTION_FIRST_INSPECTION_HAS_NO_SEND_INSPECTION_CAN_NOT_RECEIVE = "job_order_production_first_inspection.has_not_send_inspection_can_not_receive";

    /**
     * 已经接收，不能再次接收
     */
    public static final String JOB_ORDER_PRODUCTION_FIRST_INSPECTION_HAS_RECEIVE_INSPECTION_CAN_NOT_RECEIVE = "job_order_production_first_inspection.has_receive_can_not_receive";

    /**
     * 工单已经暂停
     */
    public static final String JOB_ORDER_PRODUCTION_FIRST_INSPECTION_HAS_SUSPEND_JOB_ORDER = "job_order_production_first_inspection.has_suspend_job_order";

    /**
     * 不能检查，因为没有送检
     */
    public static final String JOB_ORDER_PRODUCTION_FIRST_INSPECTION_HAS_NO_SEND_INSPECTION_CAN_NOT_CHECK = "job_order_production_first_inspection.has_not_send_inspection_can_not_check";

    /**
     * 不能檢查，因为还没有接收送检
     */
    public static final String JOB_ORDER_PRODUCTION_FIRST_INSPECTION_HAS_NO_REVEICE_SEND_INSPECTION_CAN_NOT_CHECK = "job_order_production_first_inspection.has_not_receive_inspection_can_not_check";

    /**
     * 批号不能为空
     */
    public static final String JOB_ORDER_PRODUCTION_INSPECTION_BATCH_NO_CAN_NOT_BE_EMPTY = "job_order_production_inspection.batch_no_can_not_be_empty";

    /**
     * 生产批次号不存在
     */
    public static final String JOB_ORDER_PRODUCTION_BATCH_BATCH_NO_NOT_EXIST = "job_order_production_batch.batch_no_not_exist";


    //工单生产尾检
    /**
     * 不能尾检，因为有送检还未完成
     */
    public static final String JOB_ORDER_PRODUCTION_TAIL_INSPECTION_INSPECTION_IS_EMPTY = "job_order_production_tail_inspection.inspection_is_empty";

    /**
     * 已经接收，不能再次接收
     */
    public static final String JOB_ORDER_PRODUCTION_TAIL_INSPECTION_HAS_RECEIVED = "job_order_production_tail_inspection.has_received";

    /**
     * 尾检记录已存在
     */
    public static final String JOB_ORDER_PRODUCTION_INSPECTION_TAIL_INSPECTION_IS_EXIST = "job_order_production_inspection.tail_inspection_is_exist";


}
