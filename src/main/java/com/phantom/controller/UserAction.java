package com.phantom.controller;

import com.phantom.entity.SysUser;
import com.phantom.entity.SysUserImage;
import com.phantom.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Jason Xu
 * @Date: 2018/4/14
 * @Package: com.phantom.controller
 * @Description:
 * @ModifiedBy:
 */
@RestController
@RequestMapping("/user/")
public class UserAction {

    private static Log logger = LogFactory.getLog(UserAction.class);

    @Resource
    private UserService userService;


    @RequestMapping(value = "{username}", produces = "application/json; charset=utf-8", method = RequestMethod.GET)
    @ResponseBody
    public Object getUser(@PathVariable("username") String username) {
        try {
            SysUser user = userService.getUser(username);
            if (user==null) {
                //404
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            } else {
                Map<String, Object> reponseData = new HashMap<String, Object>();
                reponseData.put("user", user);
                return ResponseEntity.status(HttpStatus.OK).body(reponseData);
            }
        } catch (Exception e) {

        }

        return null;
    }


    @RequestMapping(value = "{username}/image", produces = "image/jpeg", method = RequestMethod.GET)
    public void getUserImage(@PathVariable("username") String username, HttpServletResponse response) {
        OutputStream out = null;
        try {
            out = response.getOutputStream();
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "No-cache");
            response.setDateHeader("Expires", -1);
            // 设置Content-type字段
            response.setContentType("image/jpeg");
            SysUserImage image = userService.getUserImage(username);
            byte[] imageBytes = image.getContent();
            int l = imageBytes.length;
            out.write(imageBytes);
//            int off = 0;
//            int len = 1024;
//            while (off < l) {
//                if((off + len) > l) {
//                    len = l - off - 1;
//                }
//                out.write(imageBytes, off, len);
//                off += len;
//            }

        } catch (Exception e) {
            logger.error(e);
        } finally {
            if(out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
