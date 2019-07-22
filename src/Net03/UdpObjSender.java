package Net03;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.Date;

/**
 * 基本流程：发送端(对象类型)
 * 1、使用DatagramSocket指定发送端，创建端口
 * 2、准备对象类型，必须转成字节数组
 * 3、封装成DatagramPacket包裹，需要指定目的地
 * 4、发送包裹send(DatagramPacket p)
 * 5、释放资源
 * socket套接字是传输层和应用层交流的接口，通过这可以传输数据到应用层，具体底层并不需要我们实现
 */
public class UdpObjSender {
    public static void main(String[] args) throws IOException {
        System.out.println("发送方启动中...");
        // 1、使用DatagramSocket指定发送端，创建端口
        DatagramSocket socket = new DatagramSocket(8888);
        // 2、准备对象类型，必须转成字节数组
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(baos));
        oos.writeObject(new Date());
        oos.writeObject(new Student(213, "ama"));
        oos.flush();
        byte[] datas = baos.toByteArray();
        // 3、封装成DatagramPacket包裹，需要指定目的地
        DatagramPacket packet = new DatagramPacket(datas, 0, datas.length, new InetSocketAddress("localhost", 9999));
        // 4、发送包裹send(DatagramPacket p)
        socket.send(packet);
        // 5、释放资源
        socket.close();
    }
}
