package com.dylanlee.hellonetty.dubbo.customer;

import com.dylanlee.hellonetty.dubbo.DubboDecoder;
import com.dylanlee.hellonetty.dubbo.DubboEncoder;
import com.dylanlee.hellonetty.dubbo.provider.DubboServerHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author Dylan.Lee
 * @date 2020/8/26
 * @since
 */

public class DubboClient {

    private final int port;
    private final String host;

    DubboClient(final int port, final String host) {
        this.host = host;
        this.port = port;
    }

    public void connect() throws Exception{
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            io.netty.bootstrap.Bootstrap bootstrap = new io.netty.bootstrap.Bootstrap();
            bootstrap.group(worker)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    //加入字符串编解码器
                                    .addLast("encoder", new StringEncoder())
                                    .addLast("decoder", new StringDecoder())
                                    //加入自定义协议编解码器
                                    .addLast("dubboEncoder", new DubboEncoder())
                                    .addLast("dubboDecoder", new DubboDecoder())
                                    .addLast(null);
                        }
                    });
            ChannelFuture future = bootstrap.connect(host,port).sync();


        } finally {
            worker.shutdownGracefully();
        }
    }
}
