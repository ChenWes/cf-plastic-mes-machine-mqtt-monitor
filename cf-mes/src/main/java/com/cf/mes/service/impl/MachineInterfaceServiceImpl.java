package com.cf.mes.service.impl;

import com.cf.common.utils.StringUtils;
import com.cf.mes.domain.Employee;
import com.cf.mes.service.IEmployeeService;
import com.cf.mes.service.IMachineInterfaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author ccw
 * @Date 5/12/2022
 */
@Service
public class MachineInterfaceServiceImpl implements IMachineInterfaceService {
    @Autowired
    private IEmployeeService iEmployeeService;


    /**
     * 卡号查询员工
     *
     * @param cardNo 卡号
     * @return
     */
    @Override
    public Employee getEmployeeByCardNo(String cardNo) {
        if (StringUtils.isEmpty(cardNo)) {
            return null;
        }
        //取出员工
        Employee employee = iEmployeeService.getEmployeeByCardNo(cardNo);
        return employee;
    }

}
