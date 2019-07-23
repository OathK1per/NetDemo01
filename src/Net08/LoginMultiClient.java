package Net08;

import java.io.*;
import java.net.Socket;

/**
 * 创建客户端：(多个客户端同时连接一个服务端)
 * 1. 建立连接：使用socket创建客户端+指定服务器地址和端口
 * 2. 输入输出流操作
 * 3. 释放资源
 */
public class LoginMultiClient {
    public static void main(String[] args) throws IOException {
        System.out.println("-----------client--------------");
        //1. 建立连接：使用socket创建客户端+指定服务器地址和端口
        Socket client = new Socket("localhost", 8888);
        // 2. 输入输出流操作(将需要输出给服务器的输入信息前置到创建连接之前)
        Send send = new Send(client);
        send.send();
        Receive receive = new Receive(client);
        receive.receive();
        // 3. 释放资源
        send.release();
        receive.release();
        client.close();
    }

    static class Send {
        private DataOutputStream dos;
        private Socket client;
        private BufferedReader br;
        private String msg;

        public Send(Socket client) {
            msg = init();
            this.client = client;
            try {
                dos = new DataOutputStream(client.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private String init() {
            br = new BufferedReader(new InputStreamReader(System.in));
            String username = "";
            String userpwd = "";
            try {
                System.out.print("请输入用户名：");
                username = br.readLine();
                System.out.print("请输入密码：");
                userpwd = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "username=" + username + "&userpwd=" + userpwd;
        }

        public void send() {
            try {
                dos.writeUTF(msg);
                dos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void release() {
            try {
                dos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static class Receive {
        private DataInputStream dis;
        private Socket client;

        public Receive(Socket client) {
            this.client = client;
            try {
                dis = new DataInputStream(this.client.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void receive() {
            try {
                String str = dis.readUTF();
                System.out.println(str);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void release() {
            try {
                dis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
