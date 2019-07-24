package Net10;

import java.io.*;
import java.net.Socket;

/**
 * 使用多线程实现多个用户发送多个信息：客户端
 */
public class OOPClient {
    public static void main(String[] args) throws IOException {
        System.out.println("-----------client--------------");
        System.out.print("请输入用户名：");
        // 1. 建立连接：使用socket创建客户端+指定服务器地址和端口
        Socket client = new Socket("localhost", 8888);
        // 可以在这里就将client的名称通过send方法发送给server端
        new Thread(new Send(client)).start();
        new Thread(new Receive(client)).start();
    }
}
