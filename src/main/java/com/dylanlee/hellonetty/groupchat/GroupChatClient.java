package com.dylanlee.hellonetty.groupchat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

/**
 * @author Dylan.Lee
 * @date 2020/8/11
 * @since
 */

public class GroupChatClient {

    private final int port;
    private final String host;

    private GroupChatClient(String host, int port) {
        this.port = port;
        this.host = host;
    }

    public void start() throws Exception {
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast("decoder", new StringDecoder())
                                    .addLast("encoder", new StringEncoder())
                                    .addLast(new GroupChatClientHandler());
                        }
                    });

            ChannelFuture future = bootstrap.connect(host, port).sync();
            System.out.println("GroupChat Client connected");

            //TODO 加了下面这一行就无法输出，为什么
            //future.channel().closeFuture().sync();

            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()) {
                future.channel().writeAndFlush(scanner.nextLine());
            }
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        GroupChatClient client = new GroupChatClient("127.0.0.1", 9999);
        client.start();
    }

}
