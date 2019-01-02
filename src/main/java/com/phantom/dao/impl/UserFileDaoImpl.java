package com.phantom.dao.impl;

import com.phantom.dao.UserFileDao;
import com.phantom.entity.UserFile;
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
public class UserFileDaoImpl implements UserFileDao {
    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Override
    public void deleteByPrimaryKey(Integer fileId) {
        hibernateTemplate.delete(hibernateTemplate.get(UserFile.class, fileId));
    }

    @Override
    public void updateByPrimaryKeySelective(UserFile file) {
        hibernateTemplate.update(file);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<UserFile> listByName(Integer userId, String content) {
        return (List<UserFile>) hibernateTemplate.find("from UserFile where parentId != 2 and parentId != 3 and userId=? and concat(fileName,'.',fileType) like '%" + content + "%' ", userId);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<UserFile> listByFileType(Integer userId, String[] typeArray) {
        StringBuilder hql = new StringBuilder("from UserFile where parentId !=2 and parentId != 3 and userId=? and fileType in (");
        int count = 0;
        for (String type : typeArray) {
            if(count > 0)
                hql.append(",");
            hql.append("'").append(type).append("'");
            count ++;
        }
        hql.append(")");
        return (List<UserFile>) hibernateTemplate.find(
                hql.toString(), userId);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<UserFile> listRecentFile(Integer userId) {
        return (List<UserFile>) hibernateTemplate.find("from UserFile where userId=?", userId);
    }

    @Override
    public void save(UserFile userFile) {
        System.out.println("save()  userFile========" + userFile);
        hibernateTemplate.save(userFile);
    }

    @Override
    public UserFile getByPath(Integer userId, Integer parentId, String fileName, String fileType) {
        @SuppressWarnings("unchecked")
        List<UserFile> userFileList =
                (List<UserFile>) hibernateTemplate.find(
                "from UserFile where userId=? and parentId=? and fileName=? and fileType=?",
                userId,
                parentId,
                fileName,
                fileType);
        if(userFileList == null || userFileList.size() == 0) {
            return null;
        } else {
            return userFileList.get(0);
        }
    }

    @Override
    public UserFile selectByPrimaryKey(Integer folderId) {
        return hibernateTemplate.get(UserFile.class, folderId);
    }

    @Override
    public List<UserFile> listByParentId(Integer userId, Integer parentId) {
        return (List<UserFile>) hibernateTemplate.find("from UserFile where userId=? and parentId=?", userId, parentId);
    }
}
