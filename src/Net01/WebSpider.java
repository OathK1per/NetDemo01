package Net01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * 网络爬虫(一般版)
 * 对接受爬虫代码的网站有效
 */
public class WebSpider {
    public static void main(String[] args) throws IOException {
        // 获取URL
        URL url = new URL("https://www.jd.com");
        // 下载资源
        InputStream is = url.openStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String str;
        while ((str = br.readLine()) != null) {
            System.out.println(str);
        }
        br.close();
    }
}
