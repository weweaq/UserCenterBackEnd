package com.example.usercenterbackend.controller;


import com.example.usercenterbackend.model.LoginReq;
import com.example.usercenterbackend.model.RegisterReq;
import com.example.usercenterbackend.model.UserVo;
import com.example.usercenterbackend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

    // TODO: 2024/6/20 @RequestBody是什么注解，咋整的？get方法用什么传参？ 
    @PostMapping("/select")
    public List<UserVo> userSelect(@RequestBody String userName, HttpServletRequest request){
        log.info("userFind start req:[{}]", userName);
        List<UserVo> userVos = userService.userSelect(userName, request);
        log.info("userFind finished rsp:[{}]", userVos);
        return userVos;
    }

    @PostMapping("/delete")
    public Boolean userDelete(@RequestBody long userId, HttpServletRequest request){
        log.info("userDelete start req:[{}]", userId);
        boolean isDeleted = userService.userDelete(userId, request);
        log.info("userFind finished rsp:[{}]", isDeleted);
        return isDeleted;
    }


}
