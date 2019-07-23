package Net05;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * 后续：以面向对象的思维再封装，将run中的内容改写为多个方法
 */
public class TalkReceiver implements Runnable {
    private DatagramSocket socket;
    private String from;

    public TalkReceiver(int port, String from) {
        try {
            this.from = from;
            socket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                byte[] datas = new byte[1024 * 60];
                DatagramPacket packet = new DatagramPacket(datas, 0, datas.length);
                socket.receive(packet);
                String str = new String(datas, 0, datas.length);
                System.out.println(from + "说: " + str);
                if (str.equals("quit")) {
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        socket.close();
    }
}
