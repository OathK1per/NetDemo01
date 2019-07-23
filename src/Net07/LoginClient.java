package Net07;

import java.io.*;
import java.net.Socket;

/**
 * 创建客户端：
 * 1. 建立连接：使用socket创建客户端+指定服务器地址和端口
 * 2. 输入输出流操作
 * 3. 释放资源
 */
public class LoginClient {
    public static void main(String[] args) throws IOException {
        System.out.println("-----------client--------------");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("请输入用户名：");
        String username = br.readLine();
        System.out.print("请输入密码：");
        String userpwd = br.readLine();
        //1. 建立连接：使用socket创建客户端+指定服务器地址和端口
        Socket client = new Socket("localhost", 8888);
        // 2. 输入输出流操作(将需要输出给服务器的输入信息前置到创建连接之前)
        DataOutputStream dos = new DataOutputStream(client.getOutputStream());
        dos.writeUTF("username=" + username + "&userpwd=" + userpwd);
        dos.flush();

        DataInputStream dis = new DataInputStream(client.getInputStream());
        String str = dis.readUTF();
        System.out.println(str);
        // 3. 释放资源
        dos.close();
        dis.close();
        client.close();
    }
}
