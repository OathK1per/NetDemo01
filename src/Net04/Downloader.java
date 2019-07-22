package Net04;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * 基本流程：接收端(下载文件)
 * 1、使用DatagramSocket指定端口，创建接收端
 * 2、准备容器，封装成DatagramPacket包裹
 * 3、阻塞式接收包裹receive(DatagramPacket p)
 * 4、得到下载文件
 * 5、释放资源
 * 能接收的数据大小仅限于60k，且接收得到的数据无论之前多大，都会变成我们设的大小
 */
public class Downloader {
    public static void main(String[] args) throws IOException {
        System.out.println("接收方启动中...");
        // 1、使用DatagramSocket指定端口，创建接收端
        DatagramSocket socket = new DatagramSocket(9999);
        // 2、准备容器，封装成DatagramPacket包裹
        byte[] datas = new byte[1024 * 60];
        DatagramPacket packet = new DatagramPacket(datas, 0, datas.length);
        // 3、阻塞式接收包裹receive(DatagramPacket p)
        socket.receive(packet);
        // 4、得到下载文件
        FileUtils.byteArrayToFile(datas, "src/resources/copy-1.png");
        // 5、释放资源
        socket.close();
    }
}
