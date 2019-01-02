package com.phantom.service;

import com.phantom.entity.UserFile;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * @Author: Jason Xu
 * @Date: 2018/4/11
 * @Package: com.phantom.service
 * @Description:
 * @ModifiedBy:
 */
@Service
public interface FileService {

    //public void saveFile(SysUser user);
    /**
     * 服务端保存所有文件的根路径
     */
    String FILE_BASE = "E:" + File.separator + "images" + File.separator;
    /**
     * 所有上传文件URL的根
     */
    String URL_ROOT = "http://localhost:8080/clouddisk";

    default String getFullFilename(UserFile userFile) {
        return userFile.getFileName() + "." + userFile.getFileType();
    }
}
