package com.dylanlee.hellonetty.groupchat;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @author Dylan.Lee
 * @date 2020/8/11
 * @since
 */

public class GroupChatRepo {

    public static ChannelGroup GROUP = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
}
