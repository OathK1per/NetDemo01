package Net05;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

public class TalkSender implements Runnable {
    private DatagramSocket socket;
    private String Ip;
    private int receiverPort;

    public TalkSender(int port, String Ip, int receiverPort) {
        try {
            this.Ip = Ip;
            this.receiverPort = receiverPort;
            socket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                String str = bufferedReader.readLine();
                byte[] datas = str.getBytes();
                DatagramPacket packet =
                        new DatagramPacket(datas, 0, datas.length, new InetSocketAddress(Ip, receiverPort));
                socket.send(packet);
                if(str.equals("quit")) {
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        socket.close();
    }
}
