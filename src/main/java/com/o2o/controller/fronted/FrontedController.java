package com.o2o.controller.fronted;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author He
 * @Date 2019/8/1
 * @Time 21:22
 * @Description 转发路由
 **/
@Controller
@RequestMapping("fronted")
public class FrontedController {

    @RequestMapping(value = "/index")
    public String index() {
        return "fronted/index";
    }

    @RequestMapping(value = "/productdetail", method = RequestMethod.GET)
    public String showProductDetail() {
        return "fronted/productdetail";
    }


    @RequestMapping(value = "/shoplist", method = RequestMethod.GET)
    public String shoplist() {
        return "fronted/shoplist";
    }

    @RequestMapping(value = "/shopdetail", method = RequestMethod.GET)
    public String shopDetail() {
        return "fronted/shopdetail";
    }


    @RequestMapping(value = "/userregister")
    public String userRegister() {
        return "fronted/userRegister";
    }

    @RequestMapping(value = "/shopownerregister")
    public String shopownerRegister() {
        return "fronted/shopownerRegister";
    }

    @RequestMapping(value = "/login")
    public String login() {
        return "fronted/login";
    }


}
