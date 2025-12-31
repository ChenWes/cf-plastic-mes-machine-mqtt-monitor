package com.cf.mqtt.entity.machine;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 干燥机数据包
 *
 * @author coder-ren
 * @date 2025/12/30 19:02
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DryingMachinePayload extends HaiTianMachinePayload {
}
