package com.cf.mqtt.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 结果类型枚举
 *
 * @author coder-ren
 * @date 2025/2/26 22:22
 */
@Getter
public enum ResultTypeEnum {

    BUTTON_RETURN(253, "按键返回"),
    COMMUNICATION_RETURN(254, "通信返回"),
    HEARTBEAT_RETURN(255, "心跳返回");

    private final Integer code;
    private final String desc;

    /**
     * 使用静态 Map 缓存枚举值，提高查询效率
     */
    private static final Map<Integer, ResultTypeEnum> ENUM_MAP = new HashMap<>();

    static {
        for (ResultTypeEnum type : ResultTypeEnum.values()) {
            ENUM_MAP.put(type.getCode(), type);
        }
    }

    ResultTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 根据 code 获取对应的枚举类型
     *
     * @param code 结果类型码
     * @return 对应的枚举类型，如果不存在则返回 null
     */
    public static ResultTypeEnum getResultTypeEnum(Integer code) {
        return ENUM_MAP.get(code);
    }

    /**
     * 判断是否存在指定的 code
     *
     * @param code 结果类型码
     * @return 如果存在则返回 true，否则返回 false
     */
    public static boolean containsCode(Integer code) {
        return ENUM_MAP.containsKey(code);
    }

    /**
     * 获取所有枚举类型的描述信息
     *
     * @return 描述信息列表
     */
    public static String getAllDescriptions() {
        StringBuilder sb = new StringBuilder();
        for (ResultTypeEnum type : ResultTypeEnum.values()) {
            sb.append("Code: ").append(type.getCode()).append(", Desc: ").append(type.getDesc()).append("\n");
        }
        return sb.toString();
    }
}