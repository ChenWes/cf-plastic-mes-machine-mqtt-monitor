package com.cf.mes.domain;

import com.cf.common.enums.RabbitMQSyncDataType;
import lombok.Data;


/**
 * RabbitMQ同步数据类型
 */
@Data
public class RabbitMQSyncData {
    private static final long serialVersionUID = 1L;

    //接收者客户端ID
    private Long comsumerClientId;

    //接收的数据类型
    private RabbitMQSyncDataType dataType;

    //接收的数据实体
    private Object dataEntity;
}
