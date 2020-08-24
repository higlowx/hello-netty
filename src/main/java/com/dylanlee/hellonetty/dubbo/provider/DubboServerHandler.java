package com.dylanlee.hellonetty.dubbo.provider;

import com.dylanlee.hellonetty.dubbo.DubboProtocol;
import com.dylanlee.hellonetty.dubbo.HelloService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author Dylan.Lee
 * @date 2020/8/24
 * @since
 */

public class DubboServerHandler extends SimpleChannelInboundHandler<DubboProtocol> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DubboProtocol msg) throws Exception {
        if (!HelloService.class.getSimpleName().equals(msg.getProvider())) {
            ctx.writeAndFlush("no provider");
            return;
        }
        HelloService service = new HelloServiceImpl();
        if (!"say".equals(msg.getMethod())) {
            ctx.writeAndFlush("no method");
            return;
        }
        String res = service.say(msg.getArgs()[0]);
        ctx.writeAndFlush(msg.out(res));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("新增客户端连接 " + ctx.channel().remoteAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
