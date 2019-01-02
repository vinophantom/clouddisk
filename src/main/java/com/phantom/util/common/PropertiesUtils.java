package com.phantom.util.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.InputStream;
import java.util.*;

/**
 * @Author: Jason Xu
 * @Date: 2018/4/6
 * @Package: com.phantom.util.common
 * @Description:
 * @ModifiedBy:
 */
public class PropertiesUtils {
    private static Log log = LogFactory.getLog(PropertiesUtils.class);


    /**
     * 获取Properties对象
     * @param fname properties文件名（包含路径）
     * @return
     */
    public static Properties getProperties(String fname) {
        if (fname == null) {
            return null;
        }
        InputStream fis = null;
        java.util.Properties props = new java.util.Properties();
        try {
            fis = PropertiesUtils.class.getResourceAsStream(fname);
            props.load(fis);
            return props;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        } finally {
            StreamUtils.closeStream(fis);
        }
    }

    /**
     *
     *
     * @param fname
     * @return
     */
    public static Map<String, String> getPropertiesMap(String fname) {
        Properties p = getProperties(fname);
        Map<String, String> rtnMap = new HashMap<String, String>();
        try {
            String value = "";
            Set<Object> keySet = p.keySet();
            for (Object key : keySet) {
                value = p.getProperty((String)key);
                rtnMap.put((String)key, value);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return rtnMap;

    }

}
