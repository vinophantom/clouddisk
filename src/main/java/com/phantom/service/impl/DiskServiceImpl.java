package com.phantom.service.impl;

import com.phantom.dao.OriginFileDao;
import com.phantom.dao.UserDao;
import com.phantom.dao.UserFileDao;
import com.phantom.dao.UserFolderDao;
import com.phantom.entity.OriginFile;
import com.phantom.entity.SysUser;
import com.phantom.entity.UserFile;
import com.phantom.entity.UserFolder;
import com.phantom.service.DTOConvertUtil;
import com.phantom.service.DiskService;
import com.phantom.service.dto.UserDTO;
import com.phantom.service.dto.UserFileDTO;
import com.phantom.service.dto.UserFolderDTO;
import com.phantom.util.SortUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Transactional
@Service
public class DiskServiceImpl implements DiskService {
    private static Logger logger = LoggerFactory.getLogger(DiskServiceImpl.class);

    @Resource
    private UserDao userDao;
    @Resource
    private OriginFileDao originFileDao;
    @Resource
    private UserFileDao userFileDao;
    @Resource
    private UserFolderDao userFolderDao;
    @Resource
    private SortUtil sorter;
    @Resource
    private DTOConvertUtil convertor;

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getMenuContents(Integer userId, String menu) {
        List<UserFile> files = null;
        // 默认按字母排序
        int sortType = 0;
        switch (menu) {
            case "recent":
                files = userFileDao.listRecentFile(userId);
                // 按时间排序
                sortType = 1;
                break;
            case "doc":
                String[] docTypeArray = {"txt", "doc", "docx", "ppt", "xls"};
                files = userFileDao.listByFileType(userId, docTypeArray);
                break;
            case "photo":
                String[] photoTypeArray = {"jpeg", "jpg", "png", "gif"};
                files = userFileDao.listByFileType(userId, photoTypeArray);
                break;
            case "video":
                String[] videoTypeArray = {"mp4"};
                files = userFileDao.listByFileType(userId, videoTypeArray);
                break;
            case "audio":
                String[] audioTypeArray = {"mp3"};
                files = userFileDao.listByFileType(userId, audioTypeArray);
                break;
            case "disk":
                return getFolderContents(userId, 1, 0);
            case "recycle":
                return getFolderContents(userId, 3, 0);
            case "safebox":
                // TODO 功能未实现
                return getFolderContents(userId, 2, 0);
            case "share":
                // TODO 功能未实现
                break;
        }

        if (files == null) {
            throw new IllegalArgumentException("Illegal argument: " + menu);
        }

        List<UserFileDTO> fileDTOList = convertor.convertFileList(files);
        UserFileDTO[] fileDTOArray = fileDTOList.toArray(new UserFileDTO[fileDTOList.size()]);
        //sorter.sort(fileDTOArray, sortType);

        Map<String, Object> result = new HashMap<>();
        result.put("files", fileDTOArray);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getFolderContents(Integer userId, Integer folderId, Integer sortType) {
        //// 设置查询参数
        //Map<String, Object> queryParam = new HashMap<>();
        //// 这三个文件夹ID为公用ID，分别代表网盘，保险箱，回收站
        //if (folderId == 1 || folderId == 2 || folderId == 3) {
        //    queryParam.put("userId", userId);
        //}
        //queryParam.put("parent", folderId);

        List<UserFolder> folderList = userFolderDao.listByParentId(userId, folderId);
        //for (UserFolder folder:folderList) {
        //    logger.error("!!!!!!!!!!!!!!!!!!!!!!   ParentId:" + folder.getParentId() + "  folderId" + folder.getFolderId());
        //}
        List<UserFolderDTO> folderDTOList = convertor.convertFolderList(folderList);
        UserFolderDTO[] folderDTOArray = folderDTOList.toArray(new UserFolderDTO[folderDTOList.size()]);
        //sorter.sort(folderDTOArray, sortType);

        List<UserFile> fileList = userFileDao.listByParentId(userId, folderId);
        //for (UserFile file:fileList) {
        //    logger.error("!!!!!!!!!!!!!!!!!!!!!!   " + file.getParentId()+ "  fileId" + file.getFileId());
        //}
        List<UserFileDTO> fileDTOList = convertor.convertFileList(fileList);
        UserFileDTO[] fileDTOArray = fileDTOList.toArray(new UserFileDTO[fileDTOList.size()]);
        //sorter.sort(fileDTOArray, sortType);

        Map<String, Object> result = new HashMap<>();
        result.put("folders", folderDTOArray);
        result.put("files", fileDTOArray);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> search(Integer userId, String input) {
        List<UserFolder> localFolderList = userFolderDao.listByName(userId, input);
        List<UserFolderDTO> folderDTOList = convertor.convertFolderList(localFolderList);

        List<UserFile> localFileList = userFileDao.listByName(userId, input);
        List<UserFileDTO> fileDTOList = convertor.convertFileList(localFileList);

        Map<String, Object> result = new HashMap<>();
        result.put("folders", folderDTOList);
        result.put("files", fileDTOList);
        return result;
    }

    @Override
    public Map<String, Object> move(List<Integer> folders, List<Integer> files, Integer dest) {
        List<UserFolder> localFolderList = new ArrayList<>();
        for (int i = 0; i < folders.size(); i++) {
            UserFolder localFolder = userFolderDao.selectByPrimaryKey(folders.get(i));
            localFolder.setParentId(dest);
            localFolder.setModifyTime(new Date());
            userFolderDao.updateByPrimaryKeySelective(localFolder);
            localFolderList.add(localFolder);
        }
        List<UserFolderDTO> folderDTOList = convertor.convertFolderList(localFolderList);

        List<UserFile> localFileList = new ArrayList<>();
        for (int i = 0; i < files.size(); i++) {
            UserFile localFile = userFileDao.selectByPrimaryKey(files.get(i));
            localFile.setParentId(dest);
            localFile.setModifyTime(new Date());
            userFileDao.updateByPrimaryKeySelective(localFile);
            localFileList.add(localFile);
        }
        List<UserFileDTO> fileDTOList = convertor.convertFileList(localFileList);

        Map<String, Object> result = new HashMap<>();
        result.put("folders", folderDTOList);
        result.put("files", fileDTOList);
        return result;
    }

    @Override
    public UserFolderDTO renameFolder(Integer folderId, String fileName) {
        UserFolder folder = userFolderDao.selectByPrimaryKey(folderId);
        folder.setFolderName(fileName);
        folder.setModifyTime(new Date());
        userFolderDao.updateByPrimaryKeySelective(folder);

        return convertor.convertToDTO(folder);
    }

    @Override
    public UserFileDTO renameFile(Integer originFileId, String fileName, String fileType) {
        UserFile file = userFileDao.selectByPrimaryKey(originFileId);
        file.setFileName(fileName);
        file.setFileType(fileType);
        file.setModifyTime(new Date());
        userFileDao.updateByPrimaryKeySelective(file);

        return convertor.convertToDTO(file, null);
    }

    @Override
    public UserFolderDTO newFolder(UserFolder unsaved) {
        unsaved.setCreateTime(new Date());
        unsaved.setModifyTime(unsaved.getCreateTime());
        Integer newFolderId = userFolderDao.save(unsaved);
        System.out.println("newFolderId==========" + newFolderId);
        unsaved = userFolderDao.selectByPrimaryKey(newFolderId);
        return convertor.convertToDTO(unsaved);
    }

    @Override
    public UserDTO shred(List<Integer> folders, List<Integer> files, Integer userId) {
        // 统计删除的总字节
        long removedCap = 0L;

        // 删除文件
        for (int i = 0; i < files.size(); i++) {
            UserFile localFile = userFileDao.selectByPrimaryKey(files.get(i));
            OriginFile file = originFileDao.selectByPrimaryKey(localFile.getFileId());
            removedCap += file.getFileSize();
            userFileDao.deleteByPrimaryKey(localFile.getFileId());
        }

        // 删除文件夹和该文件夹内的所有子文件夹，以及它们包含的文件
        for (int i = 0; i < folders.size(); i++) {
            Queue<Integer> queue = new LinkedList<>();
            queue.add(folders.get(i));
            while (!queue.isEmpty()) {
                Integer parent = queue.poll();
                List<UserFile> localFileList = userFileDao.listByParentId(userId, parent);
                for (UserFile localFile : localFileList) {
                    OriginFile file = originFileDao.selectByPrimaryKey(localFile.getFileId());
                    removedCap += file.getFileSize();
                    userFileDao.deleteByPrimaryKey(localFile.getFileId());
                }

                List<UserFolder> localFolderList = userFolderDao.listByParentId(userId, parent);
                for (UserFolder localFolder : localFolderList) {
                    queue.add(localFolder.getFolderId());
                }
                userFolderDao.deleteByPrimaryKey(userFolderDao.selectByPrimaryKey(parent).getFolderId());
            }
        }

        // 更新用户已用空间信息
        SysUser user = userDao.get(userId);
        user.setUsedSize(user.getUsedSize() - removedCap);
        userDao.update(user);
        return convertor.convertToDTO(user);
    }
}