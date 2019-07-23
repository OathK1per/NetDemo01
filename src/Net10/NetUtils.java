package Net10;

import java.io.Closeable;
import java.io.IOException;

public class NetUtils {
    public static void release(Closeable... ios) {
        for (Closeable io : ios) {
            if (io != null) {
                try {
                    io.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
