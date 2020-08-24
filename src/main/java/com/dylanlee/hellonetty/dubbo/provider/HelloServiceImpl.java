package com.dylanlee.hellonetty.dubbo.provider;

import com.dylanlee.hellonetty.dubbo.HelloService;

/**
 * @author Dylan.Lee
 * @date 2020/8/24
 * @since
 */

public class HelloServiceImpl implements HelloService {


    public String say(String words) {
        if ("hello".equals(words)) {
            return "hello, long time no see!";
        } else if ("bye".equals(words)) {
            return "bye, see you again";
        }
        return "sorry, i don not know your mean";
    }

}
