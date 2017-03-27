package com.awwhome.mobilesecurityguards.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;

/**
 * Created by awwho on 2017/3/27.
 */
public class StreamUtil {

    /**
     * 将输入流转化成字符串
     *
     * @param is 流
     * @return 字符串
     */
    public static String stream2String(InputStream is) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        byte[] bytes = new byte[1024];
        int temp = -1;

        try {
            while ((temp = is.read(bytes)) != -1) {
                baos.write(bytes, 0, temp);
            }
            // 返回读取的数据
            return baos.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
