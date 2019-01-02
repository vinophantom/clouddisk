package com.phantom.service.impl;

import com.phantom.constant.HbaseConstants;
import com.phantom.dao.OriginFileDao;
import com.phantom.dao.UserDao;
import com.phantom.dao.UserFileDao;
import com.phantom.dao.basedao.HbaseDao;
import com.phantom.entity.OriginFile;
import com.phantom.entity.SysUser;
import com.phantom.entity.UserFile;
import com.phantom.service.DTOConvertUtil;
import com.phantom.service.UploadService;
import com.phantom.util.common.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.Part;
import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Jason Xu
 * @Date: 2018/4/17
 * @Package: com.phantom.service.impl
 * @Description:
 * @ModifiedBy:
 */
@Service
public class UploadServiceImpl implements UploadService {

    private static Log logger = LogFactory.getLog(UploadServiceImpl.class);

    @Resource
    private UserDao userDao;
    @Resource
    private OriginFileDao originFileDao;
    @Resource
    private UserFileDao userFileDao;
    @Resource
    private DTOConvertUtil convertor;
    @Resource
    private HbaseDao hbaseDao;

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public synchronized Map<String, Object> serveFirstPart(Part part, String fileMd5, UserFile userFile) throws IOException {
        OriginFile file = originFileDao.getByFileMd5(fileMd5);
        if (file != null) {
            // 服务器存在该文件，执行秒传逻辑
            Map<String, Object> result = new HashMap<>();

            userFile.setOriginId(file.getOriginFileId());
            // 检查客户端上传路径下是否存在同名文件
            UserFile duplicate = userFileDao.getByPath(userFile.getUserId()
                    , userFile.getParentId(), userFile.getFileName(), userFile.getFileType());
            while (duplicate != null) {
                /*
                 * 如果存在同名文件，且两文件对应的真实文件相同，则返回相应的业务状态码和消息，在客户端执行对应的操作
                 * 如果存在同名文件，但两文件对应的真实文件不同，则为上传文件的文件名添加数字下标，然后继续检查新文件名是否重名，直到不存在重名文件为止
                 */
                if (duplicate.getFileId() == userFile.getFileId()) {
                    result.put("status_code", 111);
                    result.put("msg", "file already exists");
                    return result;
                } else {
                    userFile.setFileName(resolveFileNameConflict(userFile.getFileName()));
                    duplicate = userFileDao.getByPath(userFile.getUserId()
                            , userFile.getParentId(), userFile.getFileName(), userFile.getFileType());
                }
            }
            // 新建一行UserFile数据，并更新用户使用空间
            userFile.setCreateTime(new Date());
            userFile.setModifyTime(userFile.getCreateTime());
            userFileDao.save(userFile);

            //result.put("status_code", 222);
            result.put("status_code", 222);
            System.out.println("222");
            result.put("msg", "instant uploading");
            result.put("file", convertor.convertToDTO(userFile, file));
            //result.put("file", userFile, file);
            result.put("user", convertor.convertToDTO(updateUserCap(userFile.getUserId(), file.getFileSize(), true)));
            //result.put("user", updateUserCap(userFile.getUserId(), file.getFileSize(), true));
            return result;
        }
        // 服务器不存在该文件，开始文件上传
        savePart(part, fileMd5);
        return null;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public synchronized Map<String, Object> serveLastPart(UserFile userFile, OriginFile file) throws IOException {
        // 保存新上传文件的信息
        file.setFileUrl(URL_ROOT + "/images/" + file.getFileMd5());
        file.setCreateTime(new Date());
        file.setModifyTime(file.getCreateTime());
        Integer originFileId = originFileDao.save(file);

        // 如果存在重名，则为文件名添加数字下标，直到无重名为止
        while (userFileDao.getByPath(userFile.getUserId()
                , userFile.getParentId(), userFile.getFileName(), userFile.getFileType()) != null) {
            userFile.setFileName(resolveFileNameConflict(userFile.getFileName()));
        }
        // 新建一行UserFile数据，并更新用户使用空间
        //userFile.setOriginId(file.getOriginFileId());
        userFile.setOriginId(originFileId);
        userFile.setCreateTime(new Date());
        userFile.setModifyTime(userFile.getCreateTime());
        userFileDao.save(userFile);

        Map<String, Object> result = new HashMap<>();
        result.put("status_code", 333);
        System.out.println("333");
        result.put("msg", "Uploading accomplished");
        result.put("file", convertor.convertToDTO(userFile, file));
        result.put("user", convertor.convertToDTO(updateUserCap(userFile.getUserId(), file.getFileSize(), true)));
        return result;
    }

    /**
     * 保存上传的大文件的分段
     */
    @Override
    public void savePart(Part part, String fileMd5) throws IOException {
        savePart2Hbase(part, fileMd5);
    }

    /**
     * 上传小文件
     */
    @Override
    @Transactional
    public Map<String, Object> serveSmallFile(Part part, String fileMd5, UserFile userFile) throws IOException {
        Map<String, Object> result = serveFirstPart(part, fileMd5, userFile);
        if (result == null) {
            OriginFile file = new OriginFile();
            file.setFileMd5(fileMd5);
            file.setFileType(part.getHeader("content-type"));
            file.setFileSize(part.getSize());
            return serveLastPart(userFile, file);
        } else {
            return result;
        }
    }

    /**
     * 取消上传文件
     */
    @Override
    @Transactional(readOnly = true)
    public synchronized void cancel(String fileMd5) {
        if (originFileDao.getByFileMd5(fileMd5) == null) {
            File file = new File(FILE_BASE + fileMd5);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    /**
     * 继续上传文件
     */
    @Override
    public Long resume(String fileMd5) {
        File file = new File(FILE_BASE + fileMd5);
        if (file.exists()) {
            long uploadedBytes = file.length();
            System.out.println(uploadedBytes);
            return uploadedBytes;
        }
        return null;
    }

    /**
     * 当文件上传路径发生重名时，为文件名添加一个数字下标防止冲突
     *
     * @param fileName 原文件名
     * @return 原文件名加上一个数字下标的字符串
     */
    private String resolveFileNameConflict(String fileName) {
        if (fileName.length() > 2) {
            String end = fileName.substring(fileName.length() - 3);
            // 判断localName是否以形如“(n)”的字符串结尾
            if (end.matches("\\(\\d\\)") && end.charAt(1) != '0') {
                int i = Integer.parseInt(end.substring(1, 2));// 括号中间的数字
                i++;
                StringBuilder sb = new StringBuilder();
                sb.append(fileName.substring(0, fileName.length() - 3));
                sb.append('(').append(i).append(')');

                return sb.toString();
            }
        }
        return fileName + "(1)";
    }

    /**
     * 更新ID=userId的用户所使用的空间，更新量为size
     *
     * @param plus = true 增加使用空间，plus=false减少使用空间
     * @return 更新后的用户对象
     */
    private SysUser updateUserCap(Integer userId, Long size, boolean plus) {
        SysUser user = userDao.get(userId);
        if (plus) {
            user.setUsedSize(user.getUsedSize() + size);
        } else {
            user.setUsedSize(user.getUsedSize() - size);
        }
        userDao.update(user);
        return user;
    }

    private void savePart2Hbase (Part part, String md5) {
        try {
            //long size = part.getSize();
            InputStream is = part.getInputStream();
            byte[] value = IOUtils.InputStream2Bytes(is);
            //分片计数
            int count = 0;
            //查询已经存在的分片数量
            Cell c = hbaseDao.getCellByRowkey(
                    HbaseConstants.CLOUD_FILE_TABLENAME,
                    md5,
                    HbaseConstants.CLOUD_FILE_INFO_FAMILYNAME,
                    HbaseConstants.CLOUD_FILE_INFO_NUMBER_OF_PARTS
            );
            //如果hbase表中没有记录，计数为0
            if (c != null) count = Bytes.toInt(c.getValueArray(), c.getValueOffset(), c.getValueLength());
            hbaseDao.updateOneData(
                    HbaseConstants.CLOUD_FILE_TABLENAME,
                    md5 + "[" + count + "]",
                    HbaseConstants.CLOUD_FILE_BYTES_FAMILYNAME,
                    HbaseConstants.CLOUD_FILE_BYTES_BYTES,
                    value
            );
            count ++;
            //更新分片数量
            hbaseDao.updateOneData(
                HbaseConstants.CLOUD_FILE_TABLENAME,
                    md5,
                    HbaseConstants.CLOUD_FILE_INFO_FAMILYNAME,
                    HbaseConstants.CLOUD_FILE_INFO_NUMBER_OF_PARTS,
                    Bytes.toBytes(count)
            );
        } catch (IOException e) {
            logger.error(e);
        }


    }
}
