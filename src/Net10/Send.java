package Net10;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * 使用多线程发送消息
 */
public class Send implements Runnable{
    private DataOutputStream dos;
    private Socket client;
    private BufferedReader br;
    private boolean isRun = true;

    public Send(Socket client) {
        this.client = client;
        br = new BufferedReader(new InputStreamReader(System.in));
        try {
            dos = new DataOutputStream(client.getOutputStream());
        } catch (IOException e) {
            System.out.println("----------1--------");
            release();
        }
    }

    private String fromConsole() {
        try {
            return br.readLine();
        } catch (IOException e) {
            System.out.println("----------2--------");
            release();
        }
        return "";
    }

    private void send(String msg) {
        try {
            dos.writeUTF(msg);
            dos.flush();
        } catch (IOException e) {
            System.out.println("----------3--------");
            release();
        }
    }

    @Override
    public void run() {
        String name = fromConsole();
        if (!name.equals("")) {
            send(name);
        }
        while (isRun) {
            String msg = fromConsole();
            if (!msg.equals("")) {
                send(msg);
            }
            if (msg.equals("quit")) {
                break;
            }
        }
        release();
    }

    private void release() {
        isRun = false;
        send("quit");
        NetUtils.release(dos, client);
    }
}
