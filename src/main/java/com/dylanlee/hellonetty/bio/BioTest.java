package com.dylanlee.hellonetty.bio;

import java.io.IOException;

/**
 * @author Dylan.Lee
 * @date 2020/6/24
 * @since
 */

public class BioTest {


    public static void main(String[] args) throws InterruptedException {

        final BioClient client = new BioClient("127.0.0.1", 8080);
        Thread thread0 = new Thread(new Runnable() {
            public void run() {
                try {
                    client.send("hello, i am 0 !");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread thread1 = new Thread(new Runnable() {
            public void run() {
                try {
                    client.send("hello, i am 1 !");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            public void run() {
                try {
                    client.send("hello, i am 2 !");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        thread0.start();
        thread1.start();
        Thread.sleep(5000);
        thread2.start();


    }
}
