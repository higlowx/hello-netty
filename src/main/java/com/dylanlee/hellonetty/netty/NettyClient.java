package com.dylanlee.hellonetty.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

/**
 * @author Dylan.Lee
 * @date 2020/8/6
 * @since
 */

public class NettyClient {

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            socketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                                @Override
                                public void channelRead(ChannelHandlerContext channelHandlerContext, Object o) {
                                    ByteBuf msg = (ByteBuf) o;
                                    System.out.println("接收到: " + msg.toString(CharsetUtil.UTF_8));
                                }
                                @Override
                                public void channelActive(ChannelHandlerContext ctx) {
                                    ctx.writeAndFlush(Unpooled.copiedBuffer("hello server, i am client",CharsetUtil.UTF_8));
                                }
                            });
                        }
                    });
            ChannelFuture future = bootstrap.connect("127.0.0.1", 9999).sync();
            System.out.println("Netty Client sync");
            future.channel().closeFuture().sync();

        } finally {
            workGroup.shutdownGracefully();
        }
    }
}
