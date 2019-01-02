package com.phantom.service.impl;

import com.phantom.dao.UserDao;
import com.phantom.entity.SysUser;
import com.phantom.service.VerifyService;
import com.phantom.util.JwtUtil;
import com.phantom.util.LoginUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Jason Xu
 * @Date: 2018/4/14
 * @Package: com.phantom.service.impl
 * @Description:
 * @ModifiedBy:
 */
@Service
public class VerifyServiceImpl implements VerifyService {


    @Resource
    private UserDao userDao;


    /**
     *
     * 登陆操作，
     * @param username
     * @param password
     * @return token,不存在此用户名===null，错误的用户密码==="error",服务器错误===failed
     */
    public String login(String username, String password) {
        try {
            List<SysUser> userList = userDao.findUserByUsername(username);
            if(userList == null || userList.size() == 0) {
                return null;
            } else {
                SysUser user = userList.get(0);
                String salt = user.getSalt();
                String encodedPwd = LoginUtil.encodePwd(password, salt);
                if (user.getPassword().equals(encodedPwd)) {
                    return JwtUtil.generateToken(username);
                } else {
                    return "error";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "failed";
        }
    }
}
