package Net10;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 单个用户发送多个信息：服务器
 * 实现面向对象封装
 */
public class OOPServer {
    public static void main(String[] args) throws IOException {
        System.out.println("-----------server--------------");
        // 1. 指定端口，使用ServerSocket创建服务器
        ServerSocket server = new ServerSocket(8888);
        CopyOnWriteArrayList<Channel> channels = new CopyOnWriteArrayList<>();
        while (true) {
            // 2. 阻塞式等待连接accept
            Socket client = server.accept();
            System.out.println("一个客户端创建连接");
            Channel channel = new Channel(client, channels);
            channels.add(channel);
            new Thread(channel).start();
        }
    }

    static class Channel implements Runnable {
        private DataInputStream dis;
        private DataOutputStream dos;
        private Socket client;
        private boolean isRun = true;
        private CopyOnWriteArrayList<Channel> channels;
        public Channel(Socket client, CopyOnWriteArrayList<Channel> channels) {
            this.client = client;
            this.channels = channels;
            try {
                dis = new DataInputStream(client.getInputStream());
                dos = new DataOutputStream(client.getOutputStream());
            } catch (IOException e) {
                System.out.println("----------1--------");
                release();
            }
        }
        // 接收消息
        private String receive() {
            String msg = "";
            try {
                msg = dis.readUTF();
            } catch (IOException e) {
                System.out.println("----------2--------");
                release();
            }
            return msg;
        }
        // 发送消息
        private void send(String msg) {
            try {
                dos.writeUTF(msg);
                dos.flush();
            } catch (IOException e) {
                System.out.println("----------3--------");
                release();
            }
        }
        // 发送消息给群聊
        private void sendToOthers(String msg) {
            try {
                for (Channel channel : channels) {
                    if (channel == this) {
                        continue;
                    }
                    channel.dos.writeUTF(msg);
                    channel.dos.flush();
                }
            } catch (IOException e) {
                System.out.println("----------3--------");
                release();
            }
        }
        // 释放资源
        private void release() {
            this.isRun = false;
            channels.remove(this);
            NetUtils.release(dis, dos, client);
        }

        @Override
        public void run() {
            while (isRun) {
                String msg = receive();
                if (!msg.equals("")) {
                    sendToOthers(msg);
                }
            }
            release();
        }
    }
}
