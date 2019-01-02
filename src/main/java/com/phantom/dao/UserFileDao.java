package com.phantom.dao;

import com.phantom.entity.UserFile;

import java.util.List;

/**
 * @Author: Jason Xu
 * @Date: 2018/4/17
 * @Package: com.phantom.dao
 * @Description:
 * @ModifiedBy:
 */
public interface UserFileDao {

    public void deleteByPrimaryKey(Integer fileId);
    public void updateByPrimaryKeySelective(UserFile file);
    public List<UserFile> listByName(Integer userId, String content);
    public List<UserFile> listByFileType(Integer userId, String[] TypeArray);
    public List<UserFile> listRecentFile(Integer userId);
    public void save(UserFile userFile);
    public UserFile getByPath(Integer userId, Integer parentId, String fileName, String fileType);
    public UserFile selectByPrimaryKey(Integer folderId);
    public List<UserFile> listByParentId(Integer userId, Integer parentId);
}
