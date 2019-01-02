package com.phantom.util.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Closeable;
import java.io.IOException;

/**
 * @Author: Jason Xu
 * @Date: 2018/4/6
 * @Package: com.phantom.util.common
 * @Description:
 * @ModifiedBy:
 */
public class StreamUtils {
    private static Log log = LogFactory.getLog(StreamUtils.class);
    /**
     * 关闭流
     * @param stream
     */
    public static void closeStream(Closeable stream) {
        if(stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
    }
}
