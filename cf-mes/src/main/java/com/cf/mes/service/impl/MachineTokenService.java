package com.cf.mes.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.cf.common.constant.MachineTokenConstants;
import com.cf.common.utils.StringUtils;
import com.cf.mes.domain.MachineToken;
import com.cf.mes.service.IMachineTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;


@Component("MachineTokenService")
public class MachineTokenService implements IMachineTokenService {

    @Autowired
    RedisTemplate redisTemplate;


    /**
     * 初始化数据，即清除列表即可
     */
    @Override
    public void InitializeMachineRegistrationCodeList() {
        //机器注册码列表存在
        if (redisTemplate.hasKey(MachineTokenConstants.All_Machine_Registration_Code_List_Redis_Key)) {

            //清除机器注册码列表
            redisTemplate.delete(MachineTokenConstants.All_Machine_Registration_Code_List_Redis_Key);
        }
    }

    /**
     * 插入数据至机器注册码列表
     *
     * @param machineId
     * @param registrationCode
     * @return
     */
    @Override
    public boolean InsertMachineRegistrationCodeList(Long machineId, String registrationCode) {
        boolean resultFlag = false;

        String processRegistrationCode = registrationCode.replaceAll("-", "").toLowerCase();
        String insertElement = machineId + "-" + processRegistrationCode;

        redisTemplate.opsForList().leftPush(MachineTokenConstants.All_Machine_Registration_Code_List_Redis_Key, insertElement);

        resultFlag = true;

        return resultFlag;
    }

    /**
     * 删除机器时，将原有的数据删除
     *
     * @param machineId
     * @param registrationCode
     * @return
     */
    @Override
    public boolean RemoveMachineRegistrationCodeList(Long machineId, String registrationCode) {
        boolean resultFlag = false;

        String processRegistrationCode = registrationCode.replaceAll("-", "").toLowerCase();
        String findElement = machineId + "-" + processRegistrationCode;

        //机器注册码列表存在
        if (redisTemplate.hasKey(MachineTokenConstants.All_Machine_Registration_Code_List_Redis_Key)) {

            //机器注册码列表删除该值
            redisTemplate.opsForList().remove(MachineTokenConstants.All_Machine_Registration_Code_List_Redis_Key, 0, findElement);
        }

        resultFlag = true;

        return resultFlag;
    }


    /**
     * 从缓存中提取token，验证时，需要提供机器ID和注册码
     *
     * @param machineId
     * @param registrationCode
     * @return
     */
    @Override
    public MachineToken GetToken(Long machineId, String registrationCode) {
        MachineToken machineToken = null;

        //处理传入的注册码
        String processRegistrationCode = registrationCode.replaceAll("-", "").toLowerCase();
        String findElement = machineId + "-" + processRegistrationCode;

        //存在机器注册码列表
        if (redisTemplate.hasKey(MachineTokenConstants.All_Machine_Registration_Code_List_Redis_Key)) {

            //机器注册码列表能够找到该权限
            if (redisTemplate.opsForList().range(MachineTokenConstants.All_Machine_Registration_Code_List_Redis_Key, 0, -1).indexOf(findElement) != -1) {

                //从缓存中提取token字符串
                String machineTokenString = redisTemplate.opsForValue().get(MachineTokenConstants.Token_Redis_Key).toString();

                //将Token字符串转换成对象
                machineToken = JSON.parseObject(machineTokenString, new TypeReference<MachineToken>() {
                });
            }

        }

        return machineToken;
    }


    /**
     * 将token存放到缓存中
     *
     * @param machineToken
     */
    @Override
    public void SetToken(MachineToken machineToken) {

        String newJson = JSON.toJSONString(machineToken);
        redisTemplate.opsForValue().set(MachineTokenConstants.Token_Redis_Key, newJson);
    }

    @Override
    public MachineToken GetToken() {
        MachineToken machineToken;
        //从缓存中提取token字符串
        String machineTokenString = redisTemplate.opsForValue().get(MachineTokenConstants.Token_Redis_Key).toString();
        if (StringUtils.isEmpty(machineTokenString)) {
            return null;
        }
        //将Token字符串转换成对象
        machineToken = JSON.parseObject(machineTokenString, new TypeReference<MachineToken>() {
        });
        return machineToken;
    }
}
