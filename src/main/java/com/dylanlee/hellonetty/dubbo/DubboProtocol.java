package com.dylanlee.hellonetty.dubbo;

/**
 * dubbo协议栈
 *
 * @author Dylan.Lee
 * @date 2020/8/24
 * @since
 */

public class DubboProtocol {

    String provider;
    String method;
    String[] args;
    String res;

    private DubboProtocol(String provider, String method, String[] args) {
        this.args = args;
        this.method = method;
        this.provider = provider;
    }

    public static DubboProtocol create(String provider, String method, String[] args) {
        return new DubboProtocol(provider, method, args);
    }

    public DubboProtocol out(String res) {
        this.res = res;
        return this;
    }

    public String getRes() {
        return res;
    }

    public String getProvider() {
        return provider;
    }

    public String[] getArgs() {
        return args;
    }

    public String getMethod() {
        return method;
    }
}
