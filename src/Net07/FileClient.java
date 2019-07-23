package Net07;

import java.io.*;
import java.net.Socket;

/**
 * 创建客户器：
 * 1. 建立连接：使用socket创建客户端+指定服务器地址和端口
 * 2. 输入输出流操作
 * 3. 释放资源
 */
public class FileClient {
    public static void main(String[] args) throws IOException {
        System.out.println("-----------client--------------");
        //1. 建立连接：使用socket创建客户端+指定服务器地址和端口
        Socket client = new Socket("localhost", 8888);
        // 2. 输入输出流操作
        InputStream is = new BufferedInputStream(new FileInputStream("src/resources/client.png"));
        OutputStream os = new BufferedOutputStream(client.getOutputStream());
        byte[] array = new byte[1024];
        int len = -1;
        while ((len = is.read(array)) != -1) {
            os.write(array, 0, len);
        }
        os.flush();
        // 3. 释放资源
        is.close();
        os.close();
        client.close();
    }
}
