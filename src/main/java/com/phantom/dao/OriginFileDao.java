package com.phantom.dao;

import com.phantom.entity.OriginFile;

/**
 * @Author: Jason Xu
 * @Date: 2018/4/17
 * @Package: com.phantom.dao
 * @Description:
 * @ModifiedBy:
 */
public interface OriginFileDao {
    public OriginFile selectByPrimaryKey(Integer originFileId);
    public OriginFile getByFileMd5(String md5);
    public Integer save (OriginFile originFile);
}
