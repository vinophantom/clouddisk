package com.phantom.util;

import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Author: Jason Xu
 * @Date: 2018/4/2
 * @Package: com.phantom.util
 * @Description:
 * @ModifiedBy:
 */
public class LoginUtil {

    /**
     *
     * @param pwd 原密码
     * @param salt 盐
     * @return
     */
    public static String encodePwd(String pwd, String salt) {
        String rtn = "";
        try {
        //确定计算方法
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            BASE64Encoder base64en = new BASE64Encoder();
            rtn = base64en.encode(md5.digest((salt + pwd + salt).getBytes("utf-8")));
        } catch (NoSuchAlgorithmException e) {
        } catch (UnsupportedEncodingException e) {
        }
        return rtn;
    }
}
