package com.cf.mes.mqtt.util;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 机器参数 Map 工具
 *
 * @author coder-ren
 * @date 2026/1/12 10:11
 */
public class MachineParamUtil {

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

    public static boolean isValueInRange(BigDecimal value, BigDecimal min, BigDecimal max) {
        if (value == null) {
            return false;
        }
        // 检查下限
        if (min != null && value.compareTo(min) < 0) {
            return false;
        }
        // 检查上限
        if (max != null && value.compareTo(max) > 0) {
            return false;
        }
        return true;
    }
}
