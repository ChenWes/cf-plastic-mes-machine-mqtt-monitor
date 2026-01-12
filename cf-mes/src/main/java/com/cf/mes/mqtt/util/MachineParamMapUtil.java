package com.cf.mes.mqtt.util;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 机器参数 Map 工具
 *
 * @author coder-ren
 * @date 2026/1/12 10:11
 */
public class MachineParamMapUtil {

    public static Object getParamValue(Map<String, Map<String, Object>> params, String paramName, String valueName) {
        Map<String, Object> paramValueMap = params.get(paramName);
        if (paramValueMap == null) {
            return null;
        }
        return paramValueMap.get(valueName);
    }

    public static BigDecimal getBigDecimal(Map<String, Map<String, Object>> params, String paramName, String valueName, BigDecimal defValue) {
        BigDecimal value = defValue;
        Object paramValue = getParamValue(params, paramName, valueName);
        if (paramValue != null) {
            value = new BigDecimal(String.valueOf(paramValue));
        }
        return value;
    }
}
