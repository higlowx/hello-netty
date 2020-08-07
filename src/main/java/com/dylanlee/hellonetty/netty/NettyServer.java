package com.dylanlee.hellonetty.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.CharsetUtil;

/**
 * @author Dylan.Lee
 * @date 2020/8/6
 * @since
 */

public class NettyServer {

    public static void main(String[] args) throws InterruptedException {

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap()
                    .group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(new ChannelInboundHandlerAdapter() {

                                @Override
                                public void channelReadComplete(ChannelHandlerContext ctx) {
                                    System.out.println("channel读取完毕，进行同步响应");
                                    ctx.writeAndFlush(Unpooled.copiedBuffer("i gotta it, thanks, from your server", CharsetUtil.UTF_8));
                                }

                                @Override
                                public void channelRead(ChannelHandlerContext channelHandlerContext, Object o) {
                                    ByteBuf inboundMsg = (ByteBuf) o;
                                    System.out.println("channel读取到: " + inboundMsg.toString(CharsetUtil.UTF_8));
                                }

                                @Override
                                public void channelActive(ChannelHandlerContext ctx) {
                                    System.out.println("channel读取开始");
                                }
                            });
                        }
                    });

            ChannelFuture future = bootstrap.bind(9999).sync();
            System.out.println("Netty Server is ready ...");

            future.channel().closeFuture().sync();

        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
