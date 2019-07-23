package Net09;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 单个用户发送多个信息：服务器
 * 问题：
 * 代码写在一起不好维护
 * 客户端读写没有分开，必须先写后读
 */
public class MultiPersonCharServer {
    public static void main(String[] args) throws IOException {
        System.out.println("-----------server--------------");
        // 1. 指定端口，使用ServerSocket创建服务器
        ServerSocket server = new ServerSocket(8888);
        while (true) {
            // 2. 阻塞式等待连接accept
            Socket client = server.accept();
            System.out.println("一个客户端创建连接");
            new Thread(() -> {
                DataInputStream dis = null;
                DataOutputStream dos = null;
                try {
                    dis = new DataInputStream(client.getInputStream());
                    dos = new DataOutputStream(client.getOutputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                while (true) {
                    // 3. 接收消息
                    String msg = null;
                    try {
                        msg = dis.readUTF();
                        if (msg.equals("quit")) {
                            break;
                        }
                        // 4. 返回消息
                        dos.writeUTF(msg);
                        dos.flush();
                    } catch (IOException e) {
                        break;
                    }
                }
                // 5. 释放资源
                try {
                    if (dis != null) {
                        dis.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    if (dos != null) {
                        dos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    if (client != null) {
                        client.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
