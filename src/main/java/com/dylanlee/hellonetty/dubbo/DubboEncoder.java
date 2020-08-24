package com.dylanlee.hellonetty.dubbo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * @author Dylan.Lee
 * @date 2020/8/24
 * @since
 */

public class DubboEncoder extends MessageToMessageEncoder<DubboProtocol> {
    @Override
    protected void encode(ChannelHandlerContext ctx, DubboProtocol msg, List<Object> out) throws Exception {
        if (msg == null) {
            return;
        }
        StringBuilder argsStr = new StringBuilder("{");
        for (String arg : msg.args) {
            argsStr.append(",").append(arg);
        }
        out.add(msg.provider + "#"
                + msg.method +
                argsStr.append("}").toString().replaceFirst(",", "")
                + msg.res);
    }
}
