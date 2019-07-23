package Net06;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 创建服务器：
 * 1. 指定端口，使用ServerSocket创建服务器(创建的服务器端)
 * 2. 阻塞式等待连接accept(当有客户端连接进来时阻塞解除)
 * 3. 输入输出流操作(建议使用Data流，内部封装的都是socket的流方法)
 * 4. 释放资源(输出流和客户端socket都需要关闭，server一般不关闭)
 */
public class ServerInit {
    public static void main(String[] args) throws IOException {
        System.out.println("-----------server--------------");
        // 1. 指定端口，使用ServerSocket创建服务器
        ServerSocket server = new ServerSocket(8888);
        // 2. 阻塞式等待连接accept
        Socket client = server.accept();
        System.out.println("一个客户端创建连接");
        // 3. 输入输出流操作
        DataInputStream dis = new DataInputStream(client.getInputStream());
        String str = dis.readUTF();
        System.out.println(str);
        // 4. 释放资源
        dis.close();
        client.close();
        server.close();
    }
}
