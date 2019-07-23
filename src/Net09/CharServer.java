package Net09;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 单个用户发送单个信息：服务器
 */
public class CharServer {
    public static void main(String[] args) throws IOException {
        System.out.println("-----------server--------------");
        // 1. 指定端口，使用ServerSocket创建服务器
        ServerSocket server = new ServerSocket(8888);
        // 2. 阻塞式等待连接accept
        Socket client = server.accept();
        System.out.println("一个客户端创建连接");
        // 3. 接收消息
        DataInputStream dis = new DataInputStream(client.getInputStream());
        String msg = dis.readUTF();
        // 4. 返回消息
        DataOutputStream dos = new DataOutputStream(client.getOutputStream());
        dos.writeUTF(msg);
        dos.flush();
        // 5. 释放资源
        dis.close();
        dos.close();
        client.close();
        server.close();
    }
}
