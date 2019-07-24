package Net10;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 单个用户发送多个信息：服务器
 * 创建容器，实现群聊
 * 使用指定的聊天格式，实现私聊
 */
public class OOPServer {
    public static void main(String[] args) throws IOException {
        System.out.println("-----------server--------------");
        // 1. 指定端口，使用ServerSocket创建服务器
        ServerSocket server = new ServerSocket(8888);
        // 使用线程安全的容器
        CopyOnWriteArrayList<Channel> channels = new CopyOnWriteArrayList<>();
        while (true) {
            // 2. 阻塞式等待连接accept
            Socket client = server.accept();
            Channel channel = new Channel(client, channels);
            System.out.println("一个客户端创建连接");
            channels.add(channel);
            new Thread(channel).start();
        }
    }

    static class Channel implements Runnable {
        private DataInputStream dis;
        private DataOutputStream dos;
        private Socket client;
        private boolean isRun = true;
        private String name = "";
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
            // 进群时发送进群通知
            String name = receive();
            if (!name.equals("")) {
                this.name = name;
                String str = name + "被邀请入群";
                sendToOthers(str);
                str = "欢迎亲爱的" + name + "进入群聊";
                send(str);
            }
        }
        // 接收消息，在直接关闭一个客户端时，在这里会有异常，进入release()
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
        // 发送消息，这里没有指定发送的频道，所以只能发回给自己
        private void send(String msg) {
            try {
                dos.writeUTF(msg);
                dos.flush();
            } catch (IOException e) {
                System.out.println("----------3--------");
                release();
            }
        }

        /**
         * 在这里加入boolean isSys 和 isPrivate 参数能更加方便处理msg
         * 在不同情况下返回不同msg格式，可以再优化整合在一个地方
         * @param msg
         */
        private void sendToOthers(String msg) {
            try {
                // 私聊版
                if (msg.startsWith("@")) {
                    int index = msg.indexOf(":");
                    String privateName = msg.substring(1, index);
                    for (Channel channel : channels) {
                        if (privateName.equals(channel.name)) {
                            msg = this.name + "悄悄对你说：" + msg.substring(index + 1);
                            channel.dos.writeUTF(msg);
                            channel.dos.flush();
                            break;
                        }
                    }
                } else {
                    // 群聊版
                    for (Channel channel : channels) {
                        if (channel == this) {
                            continue;
                        }
                        channel.dos.writeUTF(msg);
                        channel.dos.flush();
                    }
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
                    if (!msg.startsWith("@")) {
                        msg = this.name + "说：" + msg;
                    }
                    sendToOthers(msg);
                }
            }
            // 退出时发送退群通知，可以把这消息整合到release中，不过要注意这后面的release()就需要去掉，不然会有重复
            String str = name + "已退出群聊";
            sendToOthers(str);
            release();
        }
    }
}
