package com.phantom.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author: Jason Xu
 * @Date: 2018/4/14
 * @Package: com.phantom.controller
 * @Description:
 * @ModifiedBy:
 */
@Controller
public class ViewAction {

    private static Log logger = LogFactory.getLog(ViewAction.class);


    @RequestMapping(value="home", produces = "text/xml", method = RequestMethod.GET)
    public String home() {
        return "home";
    }

    @RequestMapping(value = "login", produces = "text/xml", method = RequestMethod.GET)
    public String loginUI() {return "login"; }

}
