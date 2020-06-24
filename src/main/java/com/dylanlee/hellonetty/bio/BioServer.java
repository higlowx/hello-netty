package com.dylanlee.hellonetty.bio;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Dylan.Lee
 * @date 2020/6/23
 * @since
 */

public class BioServer {


    private final int port;

    public BioServer(int port) {
        this.port = port;
    }

    public void start() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(this.port);
            for (; ; ) {
                Socket socket = serverSocket.accept();
                Thread thread = new BioServerHandler(socket);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        BioServer server = new BioServer(8080);
        server.start();
    }
}

class BioServerHandler extends Thread {

    Socket socket;

    public BioServerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader reader = null;
        PrintWriter writer = null;
        try {
            InputStream inputStream = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            writer = new PrintWriter(socket.getOutputStream(), true);
            String body;
            while ((body = reader.readLine()) != null && body.length() != 0) {
                System.out.println("接收到请求数据：" + body);
                writer.println("已收到 " + body + " 予以回复");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != reader) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != writer) {
                writer.close();
            }
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
