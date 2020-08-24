package com.dylanlee.hellonetty.dubbo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.Arrays;
import java.util.List;

/**
 * @author Dylan.Lee
 * @date 2020/8/24
 * @since
 */

public class DubboDecoder extends MessageToMessageDecoder<String> {


    @Override
    protected void decode(ChannelHandlerContext ctx, String msg, List<Object> out) throws Exception {
        if (msg.length() == 0) {
            return;
        }
        int i = msg.indexOf("#");
        int j = msg.indexOf("{");
        int k = msg.indexOf("}");
        String provider = msg.substring(0, i);
        String method = msg.substring(i + 1, j);
        String argsStr = msg.substring(j + 1, k);
        String[] args = argsStr.split(",");
        String res = "";
        if (msg.length() - 1 > k) {
            res = msg.substring(k + 1);
        }
        out.add(DubboProtocol.create(provider, method, args).out(res));
    }

    public static void main(String[] args) {
        String msg = "Hello#say{1,2}";
        int i = msg.indexOf("#");
        int j = msg.indexOf("{");
        String provider = msg.substring(0, i);
        System.out.println(provider);
        String method = msg.substring(i + 1, j);
        System.out.println(method);
        String argsStr = msg.substring(j + 1, msg.length() - 2);
        String[] arg = argsStr.split(",");
        System.out.println(Arrays.toString(arg));
    }
}
