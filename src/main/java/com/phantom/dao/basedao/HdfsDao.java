package com.phantom.dao.basedao;

import com.phantom.dao.conn.HdfsConn;
import com.phantom.entity.SysUser;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @Author: Jason Xu
 * @Date: 2018/4/6
 * @Package: com.phantom.dao.basedao
 * @Description:
 * @ModifiedBy:
 */

public class HdfsDao {
    private final String basePath = "/clouddisk/";

    /**
     * 获得在hdfs中的目录
     * @param file
     * @param user
     * @return
     */
    private String formatPathMethod(SysUser user, File file) {
        return basePath + user.getId() + "/" + file.getPath();
    }

    /**
     * 上传文件
     * @param inputStream
     * @param file
     * @param user
     */
    public void put(InputStream inputStream, File file, SysUser user) {
        try {
            String formatPath = formatPathMethod(user, file);
            OutputStream outputStream = HdfsConn.getFileSystem().create(new Path(formatPath), new Progressable() {

                public void progress() {

                }
            });
            IOUtils.copyBytes(inputStream, outputStream, 2048, true);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
