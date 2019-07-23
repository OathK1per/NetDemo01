package Net10;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * 使用多线程接收消息
 */
public class Receive implements Runnable {
    private DataInputStream dis;
    private Socket client;
    private boolean isRun = true;

    public Receive(Socket client) {
        this.client = client;
        try {
            dis = new DataInputStream(client.getInputStream());
        } catch (IOException e) {
            System.out.println("----------4--------");
            release();
        }
    }

    private String receive() {
        try {
            return dis.readUTF();
        } catch (IOException e) {
            System.out.println("----------5--------");
            release();
        }
        return "";
    }

    @Override
    public void run() {
        while (isRun) {
            String msg = receive();
            if (!msg.equals("")) {
                System.out.println(msg);
            }
        }
        release();
    }

    private void release() {
        isRun = false;
        NetUtils.release(dis, client);
    }
}
