package com.cf.common.utils;


import io.netty.channel.*;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WebSocketClientHandler extends SimpleChannelInboundHandler<Object> {

    //握手的状态信息
    WebSocketClientHandshaker handshaker;
    //netty自带的异步处理
    ChannelPromise handshakeFuture;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {

        log.info("当前握手的状态" + this.handshaker.isHandshakeComplete());

        Channel ch = ctx.channel();
        FullHttpResponse response;


        // 进行握手操作
        // 即还未握手完成
        if (!this.handshaker.isHandshakeComplete()) {
            try {

                response = (FullHttpResponse) msg;

                //握手协议返回，设置结束握手
                this.handshaker.finishHandshake(ch, response);

                //设置成功
                this.handshakeFuture.setSuccess();
                log.info("服务端的消息" + response.headers());

            } catch (WebSocketHandshakeException var7) {

                FullHttpResponse res = (FullHttpResponse) msg;
                String errorMsg = String.format("握手失败,status:%s,reason:%s", res.status(), res.content().toString(CharsetUtil.UTF_8));

                this.handshakeFuture.setFailure(new Exception(errorMsg));
            }
        } else if (msg instanceof FullHttpResponse) {

            response = (FullHttpResponse) msg;
            throw new IllegalStateException("Unexpected FullHttpResponse (getStatus=" + response.status() + ", content=" + response.content().toString(CharsetUtil.UTF_8) + ')');

        } else {

            //接收服务端的消息
            WebSocketFrame frame = (WebSocketFrame) msg;

            //文本信息
            if (frame instanceof TextWebSocketFrame) {
                TextWebSocketFrame textFrame = (TextWebSocketFrame) frame;
                log.info("客户端接收的消息是:" + textFrame.text());
            }

            //二进制信息
            if (frame instanceof BinaryWebSocketFrame) {
                BinaryWebSocketFrame binFrame = (BinaryWebSocketFrame) frame;
                log.info("BinaryWebSocketFrame");
            }

            //ping信息
            if (frame instanceof PongWebSocketFrame) {
                log.info("WebSocket Client received pong");
            }

            //关闭消息
            if (frame instanceof CloseWebSocketFrame) {
                log.info("receive close frame");
                ch.close();
            }
        }
    }

    /**
     * Handler活跃状态，表示连接成功
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("与服务端连接成功");
    }

    /**
     * 非活跃状态，没有连接远程主机的时候。
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("主机关闭");
    }

    /**
     * 异常处理
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info("连接异常：" + cause.getMessage());
        ctx.close();
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        this.handshakeFuture = ctx.newPromise();
    }

    public WebSocketClientHandshaker getHandshaker() {
        return handshaker;
    }

    public void setHandshaker(WebSocketClientHandshaker handshaker) {
        this.handshaker = handshaker;
    }

    public ChannelPromise getHandshakeFuture() {
        return handshakeFuture;
    }

    public void setHandshakeFuture(ChannelPromise handshakeFuture) {
        this.handshakeFuture = handshakeFuture;
    }

    public ChannelFuture handshakeFuture() {
        return this.handshakeFuture;
    }
}
