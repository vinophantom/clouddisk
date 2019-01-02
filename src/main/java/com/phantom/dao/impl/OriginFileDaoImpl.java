package com.phantom.dao.impl;

import com.phantom.dao.OriginFileDao;
import com.phantom.entity.OriginFile;
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
public class OriginFileDaoImpl implements OriginFileDao {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Override
    public OriginFile selectByPrimaryKey(Integer originFileId) {
        return hibernateTemplate.get(OriginFile.class, originFileId);
    }

    @Override
    public OriginFile getByFileMd5(String md5) {
        @SuppressWarnings("unchecked")
        List<OriginFile> originFileList =
                (List<OriginFile>) hibernateTemplate.find("from OriginFile where fileMd5=?", md5);
        if (originFileList == null || originFileList.size() == 0) {
            return null;
        } else {
            return originFileList.get(0);
        }
    }

    @Override
    public Integer save(OriginFile originFile) {
        Integer originFileId = (Integer) hibernateTemplate.save(originFile);
        return originFileId;
    }
}
