package com.phantom.dao;

import com.phantom.entity.UserFolder;

import java.util.List;

/**
 * @Author: Jason Xu
 * @Date: 2018/4/17
 * @Package: com.phantom.dao
 * @Description:
 * @ModifiedBy:
 */
public interface UserFolderDao {
    public void delete(UserFolder userFolder);
    public void deleteByPrimaryKey(Integer fileId);
    public Integer save(UserFolder userFolder);
    public void updateByPrimaryKeySelective(UserFolder userFolder);
    public List<UserFolder> listByName(Integer userId, String content);
    public UserFolder selectByPrimaryKey(Integer folderId);
    public List<UserFolder> listByParentId(Integer userId,Integer parentId);
}
