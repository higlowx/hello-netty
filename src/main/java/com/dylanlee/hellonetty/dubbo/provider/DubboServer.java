package com.dylanlee.hellonetty.dubbo.provider;

import com.dylanlee.hellonetty.dubbo.DubboDecoder;
import com.dylanlee.hellonetty.dubbo.DubboEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author Dylan.Lee
 * @date 2020/8/24
 * @since
 */

public class DubboServer {

    private final int port;

    DubboServer(int port) {
        this.port = port;
    }

    public void start() {
        NioEventLoopGroup boss = new NioEventLoopGroup(1);
        NioEventLoopGroup worker = new NioEventLoopGroup();

        io.netty.bootstrap.ServerBootstrap bootstrap = new io.netty.bootstrap.ServerBootstrap();
        bootstrap.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline()
                                //加入字符串编解码器
                                .addLast("encoder", new StringEncoder())
                                .addLast("decoder", new StringDecoder())
                                //加入自定义协议编解码器
                                .addLast("dubboEncoder", new DubboEncoder())
                                .addLast("dubboDecoder", new DubboDecoder())
                                .addLast(new DubboServerHandler());
                    }
                });

    }
}
