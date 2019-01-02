package com.phantom.controller;

import com.phantom.controller.reqbody.LoginReqBody;
import com.phantom.service.VerifyService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value="/verify/")
public class VerifyAction {

    private static Log logger = LogFactory.getLog(VerifyAction.class);

    @Autowired
    private VerifyService verifyService;




    /**
     * 登陆操作
     * @param loginReqBody
     * @param session
     * @return
     */
    @RequestMapping(value = "authentication", produces = "application/json; charset=utf-8", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity authentication(@RequestBody @Valid LoginReqBody loginReqBody, HttpSession session) {
        try {
            String token = verifyService.login(loginReqBody.getUsername(), loginReqBody.getPassword());

            if ("failed".equals(token)) {
                //500
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            } else if ("error".equals(token)) {
                //401
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            } else if (token == null) {
                //404
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            } else {
                Map<String, Object> responseBody = new HashMap<String, Object>();
                responseBody.put("token", token);
                responseBody.put("username", loginReqBody.getUsername());
                session.setAttribute("userToken", token);
                return ResponseEntity.status(HttpStatus.OK).body(responseBody);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } finally {
        }
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity test(@PathVariable("id")String id) {
        System.out.println(id+id);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id",id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(map);
    }

}
