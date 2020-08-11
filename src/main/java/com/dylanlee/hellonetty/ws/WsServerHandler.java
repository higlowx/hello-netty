package com.dylanlee.hellonetty.ws;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * @author Dylan.Lee
 * @date 2020/8/11
 * @since
 */

public class WsServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
        System.out.println("服务器接收到来自客户端的消息：" + msg.text());
        TextWebSocketFrame frame = new TextWebSocketFrame("服务器接收到您发出的消息: '" + msg.text() + "'\n");
        ctx.writeAndFlush(frame);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        System.out.println("客户端与服务器建立连接 "+ctx.channel().id().asLongText());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        System.out.println("客户端与服务器断开连接 "+ctx.channel().id().asLongText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
