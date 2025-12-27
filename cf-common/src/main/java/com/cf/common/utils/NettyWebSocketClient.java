package com.cf.common.utils;

import com.google.common.net.HttpHeaders;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@Component("NettyWebSocketClient")
public class NettyWebSocketClient {

    @Value("${webSocketUri}")
    private String webSocketUri;

    //保存频道信息，可以公开发送数据
    public Channel mc_channel;

    /**
     * 默认启动时，会连接至WebSocket服务器
     * @throws InterruptedException
     */
    //默认会运行一次
    @PostConstruct
    public void NettyWebSocketClient() throws InterruptedException {
        //netty基本操作，线程组
        EventLoopGroup group = new NioEventLoopGroup();
        //netty基本操作，启动类
        Bootstrap boot = new Bootstrap();
        boot.option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .group(group)
                .handler(new LoggingHandler(LogLevel.INFO))
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast("http-codec", new HttpClientCodec());
                        pipeline.addLast("aggregator", new HttpObjectAggregator(1024 * 1024 * 10));

                        //处理消息相关，即从服务器发送过来的消息及连接会在该类处理
                        pipeline.addLast("hookedHandler", new WebSocketClientHandler());
                    }

                });


        //websocke连接的地址
        URI websocketURI = null;
        try {
            websocketURI = new URI(webSocketUri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        DefaultHttpHeaders httpHeaders = new DefaultHttpHeaders();
        //进行握手
        WebSocketClientHandshaker handshaker = WebSocketClientHandshakerFactory.newHandshaker(websocketURI, WebSocketVersion.V13, (String) null, true, httpHeaders);


        //客户端与服务端连接的通道，final修饰表示只会有一个
        final Channel channel = boot.connect(websocketURI.getHost(), websocketURI.getPort()).sync().channel();


        WebSocketClientHandler handler = (WebSocketClientHandler) channel.pipeline().get("hookedHandler");
        handler.setHandshaker(handshaker);
        handshaker.handshake(channel);

        //阻塞等待是否握手成功
        handler.handshakeFuture().sync();
        log.info("握手成功");

        //把它公开
        mc_channel = channel;

        //给服务端发送的内容，如果客户端与服务端连接成功后，可以多次掉用这个方法发送消息
//        sendMessage(channel);
    }


    /**
     * 默认发送一次消息给服务端
     *
     * @param channel
     */
    public void sendMessage(Channel channel) {
        //发送的内容，是一个文本格式的内容
        String putMessage = "你好，我是客户端";
        TextWebSocketFrame frame = new TextWebSocketFrame(putMessage);
        channel.writeAndFlush(frame).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()) {
                    log.info("消息发送成功，发送的消息是：" + putMessage);
                } else {
                    log.info("消息发送失败 " + channelFuture.cause().getMessage());
                }
            }
        });
    }

    /**
     * 可以多次发送消息
     *
     * @param message
     */
    public void sendMessageToServer(String message) throws Exception {

        /**
         * 判断频道连接
         */
        if (mc_channel == null) {
            throw new Exception("发送Socket消息时，频道连接为空！无法发送消息。");
        }

        /**
         * 将文本转成相对应的数据
         */
        TextWebSocketFrame frame = new TextWebSocketFrame(message);


        /**
         * 频道向服务器发送消息
         */
        mc_channel.writeAndFlush(frame).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()) {
                    log.info("消息发送成功，发送的消息是：" + message);
                } else {
                    log.info("消息发送失败，原因是： " + channelFuture.cause().getMessage());
                }
            }
        });
    }
}
