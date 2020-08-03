package com.wizard.controller;

import com.wizard.config.WebSecurityConfig;
import com.wizard.model.CommonResult;
import com.wizard.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@RequestMapping("/")
@Controller
@Slf4j
public class LoginController {

    private long lastTime = 0;

    @GetMapping("/login")
    public @ResponseBody CommonResult login(String userName,String passWord,HttpSession session) {

        String pwd = CommonUtil.getNewPwd(userName);

        if(StringUtils.isEmpty(userName) || StringUtils.isEmpty(passWord)){
            return CommonResult.getFailResult("密码错误");
        }

        if(System.currentTimeMillis() - lastTime < 30*1000){
            return CommonResult.getFailResult("等会再试");
        }

        if(!"wizard".equals(userName) && !"amplee".equals(userName)){
            return CommonResult.getFailResult("密码错误");
        }

        if(!pwd.equals(passWord)){
            lastTime = System.currentTimeMillis();
            return CommonResult.getFailResult("密码错误");
        }

        session.setAttribute(WebSecurityConfig.SESSION_KEY, userName);
        session.setMaxInactiveInterval(60 * 60 * 24 * 7);

        return CommonResult.getSuccResult();
    }

    @GetMapping("/index")
    public String index() {
        return "views/index.html";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "views/user/login.html";
    }

}
