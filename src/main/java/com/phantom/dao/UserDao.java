package com.phantom.dao;

import com.phantom.entity.SysUser;
import com.phantom.entity.SysUserImage;

import java.util.List;

/**
 * @Author: Jason Xu
 * @Date: 2018/4/14
 * @Package: com.phantom.dao
 * @Description:
 * @ModifiedBy:
 */
public interface UserDao {
    public List<SysUser> findUserByUsername (String username);
    public SysUserImage findUserImgByUsername (String username);
    public void save(SysUser user);
    public void update(SysUser user);
    public void delete(String username);
    public SysUser get(int id);
}
