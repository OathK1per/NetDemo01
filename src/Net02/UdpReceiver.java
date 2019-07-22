package Net02;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * 基本流程：接收端
 * 1、使用DatagramSocket指定端口，创建接收端
 * 2、准备容器，封装成DatagramPacket包裹
 * 3、阻塞式接收包裹receive(DatagramPacket p)
 * 4、分析数据 getData(), getLength()
 * 5、释放资源
 */
public class UdpReceiver {
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
        byte[] bytes = packet.getData();
        int len = packet.getLength();
        System.out.println(new String(bytes, 0, len));
        // 和使用getData得到的数据相同
        System.out.println(new String(datas, 0, datas.length));
        // 5、释放资源
        socket.close();
    }
}
