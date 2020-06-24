package com.dylanlee.hellonetty.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author Dylan.Lee
 * @date 2020/6/23
 * @since
 */

public class BioClient {

    private final int port;
    private final String host;

    public BioClient(String host, int port) {
        this.port = port;
        this.host = host;
    }

    public void send(String body) throws IOException {
        Socket socket = new Socket(host, port);
        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer.println(body);
        String res;
        while ((res = reader.readLine()) != null && body.length() != 0) {
            System.out.println("接收到响应：" + res);
        }

        writer.close();
        reader.close();
        socket.close();
    }
}
