package com.cf.mes.service;

import com.cf.mes.domain.Employee;

/**
 * @Description 客户端业务接口
 * @Author ccw
 * @Date 5/12/2022
 */
public interface IMachineInterfaceService {

    /**
     * 卡号查询员工
     *
     * @param cardNo 卡号
     * @return
     */
    Employee getEmployeeByCardNo(String cardNo);

}
