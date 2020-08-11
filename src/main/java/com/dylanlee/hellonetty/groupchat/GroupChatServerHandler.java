package com.dylanlee.hellonetty.groupchat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.joda.time.DateTime;

/**
 * channel的状态 注册 -> 活跃 -> 不活跃 -> 注销
 *
 * @author Dylan.Lee
 * @date 2020/8/11
 * @since
 */


public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {

    static String DATE_FORMAT = "yyyy-MM-dd hh:mm:ss";

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        for (Channel channel : GroupChatRepo.GROUP) {
            if (ctx.channel().equals(channel)) {
                ctx.writeAndFlush("[自己]发送了 " + DateTime.now().toString(DATE_FORMAT) + "\n" + msg);
            } else {
                channel.writeAndFlush("[客户端]" + channel.remoteAddress() + "发送了 " + DateTime.now().toString(DATE_FORMAT) + "\n" + msg);
            }
        }
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        GroupChatRepo.GROUP.add(channel);
        System.out.println("[客户端]" + channel.remoteAddress() + "登录了 " + DateTime.now().toString(DATE_FORMAT));
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        GroupChatRepo.GROUP.remove(channel);
        System.out.println("[客户端]" + channel.remoteAddress() + "下线了 " + DateTime.now().toString(DATE_FORMAT));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        GroupChatRepo.GROUP.writeAndFlush("[客户端]" + ctx.channel().remoteAddress() + "进入了聊天 " + DateTime.now().toString(DATE_FORMAT));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        GroupChatRepo.GROUP.writeAndFlush("[客户端]" + ctx.channel().remoteAddress() + "退出了聊天 " + DateTime.now().toString(DATE_FORMAT));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        Channel channel = ctx.channel();
        GroupChatRepo.GROUP.remove(channel);
        System.out.println("[客户端]" + channel.remoteAddress() + "异常下线 " + DateTime.now().toString(DATE_FORMAT));
        ctx.close();
    }

}
