package com.phantom.dao.conn;

import com.phantom.util.common.PropertiesUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Map;

/**
 * @Author: Jason Xu
 * @Date: 2018/4/6
 * @Package: com.phantom.dao.conn
 * @Description:
 * @ModifiedBy:
 */
@Repository("hdfsConn")
public class HdfsConn {
    private FileSystem fileSystem = null;
    private Configuration configuration = null;

    private static class SingletonHolder {
        private static final HdfsConn INSTANCE = new HdfsConn();
    }

    private HdfsConn() {
        try {
            configuration = new Configuration();
            Map<String, String> map = PropertiesUtils.getPropertiesMap("/hadoop.properties");
            String hdfsUrl = map.get("hdfs.url");
            configuration.set("fs.defaultFS", hdfsUrl);
            fileSystem = FileSystem.get(configuration);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static FileSystem getFileSystem() {
        return SingletonHolder.INSTANCE.fileSystem;
    }

    public static Configuration getConfiguration() {
        return SingletonHolder.INSTANCE.configuration;
    }

}
