package com.cf.mes.service.impl;

import com.alibaba.fastjson.JSON;
import com.cf.common.constant.RedisConst;
import com.cf.common.core.redis.RedisCache;
import com.cf.mes.domain.dto.MouldTemperatureMachineParams;
import com.cf.mes.service.IMachineParamService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


/**
 * 机器参数采集服务
 */
@Service
public class MachineParamServiceImpl implements IMachineParamService {

    @Autowired
    private RedisCache redisCache;


    @Override
    public MouldTemperatureMachineParams getMouldTemperatureMachineParams(Long machineId, String machineCode) {
        MouldTemperatureMachineParams machineParams = new MouldTemperatureMachineParams();
        machineParams.setSupportMachineId(machineId);
        machineParams.setSupportMachineCode(machineCode);
        machineParams.setParams(Maps.newHashMap());
        // 获取redis数据
        String key = RedisConst.MOULD_TEMPERATURE_MACHINE_PARAM_HASH_KEY + machineCode;
        Map<String, String> cacheMap = redisCache.getCacheMap(key);

        if (cacheMap != null && !cacheMap.isEmpty()) {
            Map<String, Map<String, Object>> params = new HashMap<>();
            for (Map.Entry<String, String> entry : cacheMap.entrySet()) {
                params.put(entry.getKey(), JSON.parseObject(entry.getValue()));
            }
            machineParams.setParams(params);
        }
        return machineParams;
    }
}
