package Net03;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * 基本流程：接收端(基本数据类型)
 * 1、使用DatagramSocket指定端口，创建接收端
 * 2、准备容器，封装成DatagramPacket包裹
 * 3、阻塞式接收包裹receive(DatagramPacket p)
 * 4、分析得到的数据
 * 5、释放资源
 */
public class UdpDataReceiver {
    public static void main(String[] args) throws IOException {
        System.out.println("接收方启动中...");
        // 1、使用DatagramSocket指定端口，创建接收端
        DatagramSocket socket = new DatagramSocket(9999);
        // 2、准备容器，封装成DatagramPacket包裹
        byte[] datas = new byte[1024 * 60];
        DatagramPacket packet = new DatagramPacket(datas, 0, datas.length);
        // 3、阻塞式接收包裹receive(DatagramPacket p)
        socket.receive(packet);
        // 4、分析数据 getData(), getLength()
        DataInputStream dis = new DataInputStream(new BufferedInputStream(new ByteArrayInputStream(datas)));
        String s = dis.readUTF();
        boolean b = dis.readBoolean();
        int i = dis.readInt();
        System.out.println(s + " " + b + " " + i);
        // 5、释放资源
        socket.close();
    }
}
