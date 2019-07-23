package Net08;

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
public class LoginMultiServer {
    public static void main(String[] args) throws IOException {
        System.out.println("-----------server--------------");
        // 1. 指定端口，使用ServerSocket创建服务器
        ServerSocket server = new ServerSocket(8888);
        // 2. 阻塞式等待连接accept
        boolean isChannel = true;
        // 3. 创建多线程连接，在accept阻塞式之后就将线程分出去
        while (isChannel) {
            Socket client = server.accept();
            System.out.println("一个客户端创建连接");
            new Thread(new Channel(client)).start();
        }
        server.close();
    }

    /**
     * 使用内部静态类，为以后抽取出来做准备
     */
    static class Channel implements Runnable {
        private Socket client;
        private DataInputStream dis;
        private DataOutputStream dos;

        public Channel(Socket client) throws IOException {
            this.client = client;
            dis = new DataInputStream(client.getInputStream());

            dos = new DataOutputStream(client.getOutputStream());
        }

        /**
         * 线程体中只需要涉及业务逻辑
         */
        @Override
        public void run() {
            String username = "";
            String userpwd = "";
            String str = null;
            try {
                str = receive();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //分析
            String[] datas = str.split("&");
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
            String msg;
            if (username.equals("aaa") && userpwd.equals("fgnb")) {
                msg = "登陆成功，欢迎回来";
            } else {
                msg = "用户名或者密码错误";
            }
            try {
                send(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        private void send(String msg) throws IOException {
            dos.writeUTF(msg);
            dos.flush();
        }

        private String receive() throws IOException {
            return dis.readUTF();
        }

        // 4. 释放资源
        private void release() throws IOException {
            if (dos != null) {
                dos.close();
            }
            if (dis != null) {
                dis.close();
            }
            if (client != null) {
                client.close();
            }
        }
    }
}
