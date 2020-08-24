package com.dylanlee.hellonetty.dubbo;

/**
 * @author Dylan.Lee
 * @date 2020/8/24
 * @since
 */

public interface HelloService {

    /**
     * 顶层服务接口
     *
     * @param words
     * @return
     */
    String say(String words);
}
