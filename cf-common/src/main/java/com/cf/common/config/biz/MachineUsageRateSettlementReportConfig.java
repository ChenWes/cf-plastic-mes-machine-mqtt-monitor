package com.cf.common.config.biz;

import com.cf.common.config.biz.entity.MachineUsageRateField;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 机器使用率报表配置
 *
 * @author mao
 */
@Data
@Component
@ConfigurationProperties(prefix = "mes.report.machine-usage-rate-settlement")
public class MachineUsageRateSettlementReportConfig {
    private List<MachineUsageRateField> fields;
}
