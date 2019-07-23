package Net09;

import java.io.*;
import java.net.Socket;

/**
 * 单个用户发送单个信息：客户端
 */
public class ChatClient {
    public static void main(String[] args) throws IOException {
        System.out.println("-----------client--------------");
        // 1. 建立连接：使用socket创建客户端+指定服务器地址和端口
        Socket client = new Socket("localhost", 8888);
        // 2. 客户端发送消息
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String msg = br.readLine();
        DataOutputStream dos = new DataOutputStream(client.getOutputStream());
        dos.writeUTF(msg);
        dos.flush();
        // 3. 客户端获取消息
        DataInputStream dis = new DataInputStream(client.getInputStream());
        msg = dis.readUTF();
        System.out.println(msg);
        // 4. 释放资源
        dos.close();
        dis.close();
        client.close();
    }
}
