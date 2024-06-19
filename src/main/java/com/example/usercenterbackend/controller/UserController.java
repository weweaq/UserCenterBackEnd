package com.example.usercenterbackend.controller;


import com.example.usercenterbackend.model.LoginReq;
import com.example.usercenterbackend.model.RegisterReq;
import com.example.usercenterbackend.model.UserVo;
import com.example.usercenterbackend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    UserService userService;

    @PostMapping("/register")
    public Long userRegister(@RequestBody RegisterReq req){
        log.info("userRegister start req:[{}]", req);
        long userId = userService.userRegister(req.getAccount(), req.getPassword(), req.getCheckPassword());
        log.info("userRegister finished rsp:[{}]", userId);

        return userId;
    }

    @PostMapping("/login")
    public UserVo userLogin(@RequestBody LoginReq req, HttpServletRequest request){
        log.info("userLogin start req:[{}]", req);
        UserVo userVo = userService.userLogin(req.getAccount(), req.getPassword(),  request);
        log.info("userLogin finished rsp:[{}]", userVo);
        return userVo;
    }

}
