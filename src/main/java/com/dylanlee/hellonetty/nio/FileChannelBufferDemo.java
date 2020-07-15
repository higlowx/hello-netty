package com.dylanlee.hellonetty.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author Dylan.Lee
 * @date 2020/6/29
 * @since
 */

public class FileChannelBufferDemo {

    public static void main(String[] args) throws IOException {
//        FileChannelBufferDemo.fileOut();
        FileChannelBufferDemo.fileIn();
    }


    public static void fileOut() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("D://fileout.txt");
        //通过fileOutputStream派生出FileChannel
        FileChannel channel = fileOutputStream.getChannel();
        //声明一个字节缓冲ByteBuffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //向Buffer中写入文件数据
        String data = "hello，netty";
        buffer.put(data.getBytes());
        //Buffer进行写->读翻转
        buffer.flip();
        //由Buffer向Channel写入,详见注释：Writes a sequence of bytes to this channel from a subsequence of thegiven buffers
        channel.write(buffer);
        //关闭输出流
        fileOutputStream.close();
    }

    public static void fileIn() throws IOException {
        FileInputStream fileInputStream = new FileInputStream("D://fileout.txt");
        FileChannel channel = fileInputStream.getChannel();
        //声明一个字节缓冲ByteBuffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //由FileChannel向Buffer中写入文件数据
        channel.read(buffer);
        //Buffer进行写->读翻转
        buffer.flip();
        //由Buffer中的数据组装结果data
        String data = new String(buffer.array());
        System.out.println(data);
    }
}
