package com.phantom.util.common;

/**
 * @Author: Jason Xu
 * @Date: 2018/4/25
 * @Package: com.phantom.util.common
 * @Description:
 * @ModifiedBy:
 */
public class ByteUtils {



    public static byte[] subBytes(byte[] src, int offset, int length) {
        byte[] bs = new byte[length];
        System.arraycopy(src, offset, bs, 0, length);
        return bs;
    }
}
