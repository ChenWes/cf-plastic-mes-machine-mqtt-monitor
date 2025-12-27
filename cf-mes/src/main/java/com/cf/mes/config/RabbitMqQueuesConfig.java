package com.cf.mes.config;

import com.cf.common.constant.RabbitMqConstants;
import com.cf.mes.domain.Machine;
import com.cf.mes.service.IMachineService;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * @Description
 * @Author ccw
 * @Date 22/12/2021
 */
@Component
public class RabbitMqQueuesConfig {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private IMachineService machineService;

    @PostConstruct
    private void createQueues() throws IOException, TimeoutException {
        List<Machine> machineList = machineService.getRegisterMachineList();
        if (machineList != null) {
            ConnectionFactory connectionFactory = rabbitTemplate.getConnectionFactory();
            Connection connection = connectionFactory.createConnection();
            Channel channel = connection.createChannel(false);
            channel.exchangeDeclare(RabbitMqConstants.SEND_CLIENT_COMMAND_EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
            for (Machine machineItem : machineList) {
                String queueName = RabbitMqConstants.CLIENT_QUEUE_NAME + machineItem.getMachineId();
                String routingKey = RabbitMqConstants.SEND_CLIENT_COMMAND_ROUTING_KEY + machineItem.getMachineId();
                channel.queueDeclare(queueName, true, false, false, null);
                channel.queueBind(queueName, RabbitMqConstants.SEND_CLIENT_COMMAND_EXCHANGE_NAME, routingKey);
            }
            channel.close();
            connection.close();
        }
    }
}
