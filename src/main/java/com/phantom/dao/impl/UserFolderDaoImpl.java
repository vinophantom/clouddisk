package com.phantom.dao.impl;

import com.phantom.dao.UserFolderDao;
import com.phantom.entity.UserFolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Jason Xu
 * @Date: 2018/4/17
 * @Package: com.phantom.dao.impl
 * @Description:
 * @ModifiedBy:
 */
@Repository
public class UserFolderDaoImpl implements UserFolderDao {

    @Autowired
    private HibernateTemplate hibernateTemplate;


    @Override
    public void delete(UserFolder userFolder) {
        hibernateTemplate.delete(userFolder);
    }

    @Override
    public void deleteByPrimaryKey(Integer fileId) {
        hibernateTemplate.delete(hibernateTemplate.get(UserFolder.class, fileId));
    }

    @Override
    public Integer save(UserFolder userFolder) {
        return (Integer)hibernateTemplate.save(userFolder);
    }

    @Override
    public void updateByPrimaryKeySelective(UserFolder userFolder) {
        hibernateTemplate.update(userFolder);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<UserFolder> listByName(Integer userId, String content) {
        return (List<UserFolder>) hibernateTemplate.find("from UserFolder where userId=? and parentId != 2 and parentId != 3 and folderName like '%" + content + "%'" , userId);
    }

    @Override
    public UserFolder selectByPrimaryKey(Integer folderId) {
        return hibernateTemplate.get(UserFolder.class, folderId);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<UserFolder> listByParentId(Integer userId, Integer parentId) {
        return (List<UserFolder>) hibernateTemplate.find("from UserFolder where (userId=? or userId=0) and parentId=?", userId, parentId);
    }
}
