package Net06;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * 创建客户器：
 * 1. 建立连接：使用socket创建客户端+指定服务器地址和端口
 * 2. 输入输出流操作
 * 3. 释放资源
 */
public class ClientInit {
    public static void main(String[] args) throws IOException {
        System.out.println("-----------client--------------");
        //1. 建立连接：使用socket创建客户端+指定服务器地址和端口
        Socket client = new Socket("localhost", 8888);
        // 2. 输入输出流操作
        DataOutputStream dos = new DataOutputStream(client.getOutputStream());
        dos.writeUTF("Hello World!");
        dos.flush();
        // 3. 释放资源
        dos.close();
        client.close();
    }
}
