package Net10;

import java.io.*;
import java.net.Socket;

/**
 * 使用多线程实现多个用户发送多个信息：客户端
 */
public class OOPClient {
    public static void main(String[] args) throws IOException {
        System.out.println("-----------client--------------");
        // 1. 建立连接：使用socket创建客户端+指定服务器地址和端口
        Socket client = new Socket("localhost", 8888);
        new Thread(new Send(client)).start();
        new Thread(new Receive(client)).start();
    }
}
