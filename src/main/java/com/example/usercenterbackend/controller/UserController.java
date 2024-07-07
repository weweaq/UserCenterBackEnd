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
@RequestMapping("/api/user")
public class UserController {

    @Resource
    UserService userService;

    @GetMapping("/current")
    public UserVo queryCurrentState(HttpServletRequest request) {
        log.info("queryCurrentState start");
        UserVo userVo = userService.queryCurrentState(request);
        log.info("queryCurrentState finish");
        return userVo;
    }

    @PostMapping("/register")
    public Long userRegister(@RequestBody RegisterReq req) {
        log.info("userRegister start req:[{}]", req);
        long userId = userService.userRegister(req.getAccount(), req.getPassword(), req.getCheckPassword());
        log.info("userRegister finished rsp:[{}]", userId);

        return userId;
    }

    @PostMapping("/login")
    public UserVo userLogin(@RequestBody LoginReq req, HttpServletRequest request) {
        log.info("userLogin start req:[{}]", req);
        UserVo userVo = userService.userLogin(req.getAccount(), req.getPassword(), request);
        log.info("userLogin finished rsp:[{}]", userVo);
        return userVo;
    }

    @PostMapping("/logout")
    public void userLogout(HttpServletRequest request) {
        userService.userLogout(request);
    }

    @GetMapping("/select")
    public List<UserVo> userSelect(@RequestParam(required = false) String userName, HttpServletRequest request) {
        log.info("userSelect start req:[{}]", userName);
        List<UserVo> userVos = userService.userSelect(userName, request);
        log.info("userSelect finished rsp:[{}]", userVos);
        return userVos;
    }

    @DeleteMapping("/delete")
    public Boolean userDelete(@RequestParam long userId, HttpServletRequest request) {
        log.info("userDelete start req:[{}]", userId);
        boolean isDeleted = userService.userDelete(userId, request);
        log.info("userFind finished rsp:[{}]", isDeleted);
        return isDeleted;
    }


}
