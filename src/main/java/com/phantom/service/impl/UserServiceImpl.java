package com.phantom.service.impl;

import com.phantom.dao.UserDao;
import com.phantom.entity.SysUser;
import com.phantom.entity.SysUserImage;
import com.phantom.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Jason Xu
 * @Date: 2018/4/15
 * @Package: com.phantom.service.impl
 * @Description:
 * @ModifiedBy:
 */
@Service

public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Override
    public SysUser getUser(int id) {
        return userDao.get(id);
    }

    @Transactional(value = "hibernateTxManager", readOnly = true)
    @Override
    public SysUser getUser(String username) {
        List<SysUser> userList = userDao.findUserByUsername(username);
        try {
            if (userList == null || userList.size() == 0) {
                return null;
            } else {
                return userList.get(0);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    public SysUserImage getUserImage(String username) {
        return userDao.findUserImgByUsername(username);
    }

    @Override
    public boolean isUserStorageEnough(int userId, long fileSize) {
        SysUser user = userDao.get(userId);
        if (user.getUsedSize() + fileSize > user.getMemorySize()) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isUserStorageEnough(String username, long fileSize) {
        List<SysUser> userList = userDao.findUserByUsername(username);
        if (userList == null || userList.size() == 0) {
            return false;
        } else {
            SysUser user = userList.get(0);
            if (user.getUsedSize() + fileSize > user.getMemorySize()) {
                return false;
            }
            return true;
        }
    }


}
