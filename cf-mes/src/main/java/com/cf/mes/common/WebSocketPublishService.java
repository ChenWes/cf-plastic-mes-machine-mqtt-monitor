package com.cf.mes.common;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cf.common.utils.NettyWebSocketClient;
import com.cf.common.utils.WebSocketMessageFormat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * websocket发布服务
 */
@Slf4j
@Service
public class WebSocketPublishService {

    @Autowired
    private NettyWebSocketClient nettyWebSocketClient;

    @Autowired
    private WebSocketMessageFormat webSocketMessageFormat;

    /**
     * 发送web socket 消息
     *
     * @param channelId
     * @param data
     * @param <T>
     * @throws Exception
     */
    public <T> void publish(String channelId, T data) throws Exception {
        try {
            //数据处理完成
            webSocketMessageFormat.setData(JSONObject.toJSONString(data, SerializerFeature.DisableCircularReferenceDetect));
            //自动注册数据发送
            webSocketMessageFormat.setChannelId(channelId);
            nettyWebSocketClient.sendMessageToServer(JSONObject.toJSONString(webSocketMessageFormat));
        } catch (Exception ex) {
            // log.error(ex.getMessage());
            throw ex;
        }
    }
}
