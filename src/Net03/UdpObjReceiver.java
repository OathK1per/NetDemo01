package Net03;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Date;

/**
 * 基本流程：接收端(对象类型)
 * 1、使用DatagramSocket指定端口，创建接收端
 * 2、准备容器，封装成DatagramPacket包裹
 * 3、阻塞式接收包裹receive(DatagramPacket p)
 * 4、分析得到的数据
 * 5、释放资源
 */
public class UdpObjReceiver {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        System.out.println("接收方启动中...");
        // 1、使用DatagramSocket指定端口，创建接收端
        DatagramSocket socket = new DatagramSocket(9999);
        // 2、准备容器，封装成DatagramPacket包裹
        byte[] datas = new byte[1024 * 60];
        DatagramPacket packet = new DatagramPacket(datas, 0, datas.length);
        // 3、阻塞式接收包裹receive(DatagramPacket p)
        socket.receive(packet);
        // 4、分析数据 getData(), getLength()
        ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new ByteArrayInputStream(datas)));
        Object object = ois.readObject();
        Object student = ois.readObject();
        if (object instanceof Date) {
            System.out.println(object);
        }
        if (student instanceof Student) {
            System.out.println(student.toString());
        }
        // 5、释放资源
        socket.close();
    }
}

class Student implements Serializable {
    private Integer id;
    private String name;

    public Student(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
