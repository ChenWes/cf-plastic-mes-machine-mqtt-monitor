package com.cf.common.constant;

/**
 * @Description
 * @Author ccw
 * @Date 20/12/2021
 */
public class RabbitMqConstants {


    /**
     * 发送指令Exchange Name
     */
    public static final String SEND_CLIENT_COMMAND_EXCHANGE_NAME = "SendClientCommand";

    /**
     * Routing key
     */
    public static final String SEND_CLIENT_COMMAND_ROUTING_KEY = "client_";

    /**
     * 客户端队列名前缀
     */
    public static final String CLIENT_QUEUE_NAME = "client_";

    /**
     * 直连Exchange Name
     */
    public static final String CLIENT_SYNC_DATA_DIRECT_EXCHANGE_NAME = "client_sync_data";

    /**
     * 直连Routing key
     */
    public static final String CLIENT_SYNC_DATA_DIRECT_ROUTING_KEY_PREFIX = "client_";

    /**
     * 直连Queue前缀
     */
    public static final String CLIENT_SYNC_DATA_DIRECT_QUEUE_NAME_PERFIX = "client_";
}
