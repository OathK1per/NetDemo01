package Net07;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 创建服务器：(双向)
 * 1. 指定端口，使用ServerSocket创建服务器(创建的服务器端)
 * 2. 阻塞式等待连接accept(当有客户端连接进来时阻塞解除)
 * 3. 输入输出流操作(建议使用Data流，内部封装的都是socket的流方法)
 * 4. 释放资源(输出流和客户端socket都需要关闭，server一般不关闭)
 */
public class LoginServer {
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
        String[] datas = str.split("&");
        String username = "";
        String userpwd = "";
        for (String data : datas) {
            String[] info = data.split("=");
            if (info[0].equals("username")) {
                System.out.println("您的用户名为：" + info[1]);
                username = info[1];
            } else if (info[0].equals("userpwd")) {
                System.out.println("您的密码为：" + info[1]);
                userpwd = info[1];
            }
        }
        DataOutputStream dos = new DataOutputStream(client.getOutputStream());
        String msg;

        if (username.equals("aaa") && userpwd.equals("fgnb")) {
            msg = "登陆成功，欢迎回来";
        } else {
            msg = "用户名或者密码错误";
        }
        dos.writeUTF(msg);
        dos.flush();
        // 4. 释放资源
        dos.close();
        dis.close();
        client.close();
        server.close();
    }
}
