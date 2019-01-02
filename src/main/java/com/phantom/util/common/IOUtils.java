package com.phantom.util.common;

import java.io.*;

/**
 * @Author: Jason Xu
 * @Date: 2018/4/25
 * @Package: com.phantom.util.common
 * @Description:
 * @ModifiedBy:
 */
public class IOUtils {


    /**
     * 关闭io资源
     * @param rec
     * @throws IOException
     */
    public static void closeResource(Closeable rec) throws IOException {
        if (rec != null) try {
            rec.close();
        } catch (IOException e) {
            throw e;
        }
    }


    public static byte[] InputStream2Bytes(InputStream is){
        byte[] buffer = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = is.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            is.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

}
