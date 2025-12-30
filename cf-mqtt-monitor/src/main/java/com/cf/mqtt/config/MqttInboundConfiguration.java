package com.cf.mqtt.config;

import com.cf.mqtt.handler.MqttMessageHandlerManager;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

/**
 * 实现了对 inboundtopic 中的主题监听，当有消息推送到 inboundtopic 主题上时可以接受
 * MQTT 消费端
 * Create By Spring-2022/10/29
 */
@Slf4j
@Configuration
@IntegrationComponentScan // basePackages 属性：指定需要扫描的包路径。如果未指定，默认会扫描当前类所在包及其子包
public class MqttInboundConfiguration {
    private static Logger LOGGER = LoggerFactory.getLogger(MqttInboundConfiguration.class);

    @Autowired
    private MqttConfiguration mqttProperties;

    @Autowired
    private MqttMessageHandlerManager mqttMessageHandlerManager;

    @Bean
    public MessageChannel mqttInputChannel() {
        log.info("==>Init MQTT input channel...");
        return new DirectChannel();
    }


    @Bean
    public MqttPahoClientFactory mqttInClient() {
        log.info("==>Init MQTT MqttPahoClientFactory...");
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        String[] mqttServerUrls = mqttProperties.getUrl().split(",");
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(mqttServerUrls);

        options.setUserName(mqttProperties.getUsername());
        options.setPassword(mqttProperties.getPassword().toCharArray());
        options.setKeepAliveInterval(2);

        //接受离线消息
        options.setCleanSession(false);

        options.setAutomaticReconnect(true);

        factory.setConnectionOptions(options);

        return factory;
    }

    //  配置Client，监听Topic
    //  如果我要配置多个client，应该怎么处理呢？这个也简单, 模仿此方法，多写几个client
    @Bean
    public MessageProducer inbound() {
        String[] inboundTopics = mqttProperties.getReceiveTopics().split(",");
        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(mqttProperties.getClientId() + "_inbound",
                mqttInClient(), inboundTopics);
//        adapter.addTopic();  // 添加 TOPICS
        adapter.setCompletionTimeout(1000 * 5);
        adapter.setQos(0);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setOutputChannel(mqttInputChannel());
        return adapter;
    }

    // 通过通道获取数据,即处理 MQTT 发送过来的消息，可以通过 MQTTX 工具发送数据测试
    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")  // 异步处理
    public MessageHandler handler() {
        return new MessageHandler() {
            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                // 消息分发到对应处理器中处理
                mqttMessageHandlerManager.handleMessage(message);
            }
        };
    }

}
