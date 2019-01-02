package com.phantom.dao.impl;

import com.phantom.dao.UserDao;
import com.phantom.entity.SysUser;
import com.phantom.entity.SysUserImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Jason Xu
 * @Date: 2018/4/14
 * @Package: com.phantom.dao.impl
 * @Description:
 * @ModifiedBy:
 */
@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private HibernateTemplate hibernateTemplate;


    public List<SysUser> findUserByUsername(String username) {
        return (List<SysUser>) hibernateTemplate.find("from SysUser where username=?", username);
    }

    public SysUserImage findUserImgByUsername(String username) {
        try {
            List<SysUser> userList = findUserByUsername(username);
            if (userList == null || userList.size() ==0) {
                return null;
            } else {
                int userId = userList.get(0).getId();
                List<SysUserImage> imageList = (List<SysUserImage>)hibernateTemplate.find("from SysUserImage where userid=?", userId);
                if (imageList == null || imageList.size() == 0) {
                    return null;
                }
                return imageList.get(0);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void save(SysUser user) {
        hibernateTemplate.save(user);
    }

    public void update(SysUser user) {
        hibernateTemplate.update(user);
    }

    @Override
    public void delete(String username) {
        List<SysUser> userList = findUserByUsername(username);
        if (userList == null || userList.size() == 0) {
            throw new RuntimeException("不存在此记录！");
        } else {
            hibernateTemplate.delete(userList.get(0));
        }
    }

    @Override
    public SysUser get(int id) {
        return hibernateTemplate.get(SysUser.class, id);
    }


}
