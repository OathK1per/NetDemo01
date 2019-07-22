package Net03;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

/**
 * 基本流程：发送端(所有基本数据类型)
 * 1、使用DatagramSocket指定发送端，创建端口
 * 2、准备基本数据类型，必须转成字节数组
 * 3、封装成DatagramPacket包裹，需要指定目的地
 * 4、发送包裹send(DatagramPacket p)
 * 5、释放资源
 * socket套接字是传输层和应用层交流的接口，通过这可以传输数据到应用层，具体底层并不需要我们实现
 */
public class UdpDataSender {
    public static void main(String[] args) throws IOException {
        System.out.println("发送方启动中...");
        // 1、使用DatagramSocket指定发送端，创建端口
        DatagramSocket socket = new DatagramSocket(8888);
        // 2、准备基本数据类型，必须转成字节数组
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(os));
        dos.writeUTF("没有cc哈哈");
        dos.writeBoolean(true);
        dos.writeInt(13);
        dos.flush();
        byte[] datas = os.toByteArray();
        // 3、封装成DatagramPacket包裹，需要指定目的地
        DatagramPacket packet = new DatagramPacket(datas, 0, datas.length, new InetSocketAddress("localhost", 9999));
        // 4、发送包裹send(DatagramPacket p)
        socket.send(packet);
        // 5、释放资源
        socket.close();
    }
}
