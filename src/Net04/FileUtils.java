package Net04;

import java.io.*;

/**
 * 实现方法:fileToByteArray and byteArrayToFile
 */
public class FileUtils {

    public static void main(String[] args) {

        byte[] bytes = fileToByteArray("src/resources/WeChat Image_20190602031908.jpg");
        System.out.println(bytes.length);
        byteArrayToFile(bytes, "src/resources/passport.jpg");
    }

    static byte[] fileToByteArray(String filePath) {
        File file = new File(filePath);
        InputStream is = null;
        ByteArrayOutputStream baos = null;

        try {
            is = new FileInputStream(file);
            baos = new ByteArrayOutputStream();
            byte[] bytes = new byte[1024];
            int len = -1;
            while ((len = is.read(bytes)) != -1) {
                baos.write(bytes, 0, len);
            }
            return baos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    static void byteArrayToFile(byte[] src, String destPath) {
        File dest = new File(destPath);
        ByteArrayInputStream bais = null;
        OutputStream os = null;

        try {
            bais = new ByteArrayInputStream(src);
            os = new FileOutputStream(dest);

            byte[] array = new byte[1024];
            int len = -1;
            while ((len = bais.read(array)) != -1) {
                os.write(array, 0, len);
            }
            os.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                if(os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
