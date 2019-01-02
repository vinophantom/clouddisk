package com.phantom.service;

import com.phantom.entity.SysUser;
import com.phantom.entity.SysUserImage;

/**
 * @Author: Jason Xu
 * @Date: 2018/4/15
 * @Package: com.phantom.service
 * @Description:
 * @ModifiedBy:
 */
public interface UserService {

    public SysUser getUser(int id);

    public SysUser getUser(String username);

    public SysUserImage getUserImage(String username);

    public boolean isUserStorageEnough(int userId, long fileSize);

    public boolean isUserStorageEnough(String username, long fileSize);

}
