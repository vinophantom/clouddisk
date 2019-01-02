package com.phantom.service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * @Author: Jason Xu
 * @Date: 2018/4/17
 * @Package: com.phantom.service.impl
 * @Description:
 * @ModifiedBy:
 */
public interface DownloadService extends FileService {
    /**
     * 生成zip文件名
     */
    String generateZipFileName(List<Integer> files, List<Integer> folders);

    /**
     * 将所有文件及文件夹打包成一个zip文件返回给客户端
     */
    void download(Integer userId, List<Integer> files, List<Integer> folders, OutputStream out) throws IOException;
}
