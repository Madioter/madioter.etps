package com.madiot.enterprise.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2015/10/7 0007.
 */
@Controller
@RequestMapping("/")
public class LoginController {

    /**
     * 登陆页面
     * @return
     */
    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    /**
     * 登陆函数
     */
    @RequestMapping("/doLogin")
    public void doLogin(){

    }
}
