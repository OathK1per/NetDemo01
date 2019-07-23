package Net09;

import java.io.*;
import java.net.Socket;

/**
 * 使用多线程实现多个用户发送多个信息：客户端
 */
public class MultiPersonChatClient {
    public static void main(String[] args) throws IOException {
        System.out.println("-----------client--------------");
        // 1. 建立连接：使用socket创建客户端+指定服务器地址和端口
        Socket client = new Socket("localhost", 8888);
        // 2. 客户端发送消息
        DataOutputStream dos = new DataOutputStream(client.getOutputStream());
        DataInputStream dis = new DataInputStream(client.getInputStream());
        while (true) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String msg = br.readLine();
            dos.writeUTF(msg);
            dos.flush();
            if (msg.equals("quit")) {
                break;
            }
            // 3. 客户端获取消息
            msg = dis.readUTF();
            System.out.println(msg);
        }
        // 4. 释放资源
        dos.close();
        dis.close();
        client.close();
    }
}
