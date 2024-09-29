package com.example.usercenterbackend.service;

import com.example.usercenterbackend.common.CommonRsp;
import com.example.usercenterbackend.model.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.usercenterbackend.model.UserVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author 122
 * @description 针对表【user(用户表)】的数据库操作Service
 * @createDate 2024-06-14 22:26:29
 */
public interface UserService {

    public static final String SALT = "weweaqa";

    String USER_LOGIN_STATE = "user_login_state";

    CommonRsp<Long> userRegister(String account, String password, String checkPassword);

    CommonRsp<UserVo> userLogin(String account, String password, HttpServletRequest request);

    void userLogout(HttpServletRequest request);

    CommonRsp<List<UserVo>> userSelect(String name, HttpServletRequest request);

    CommonRsp<Boolean> userDelete(long id, HttpServletRequest request);

    CommonRsp<UserVo> queryCurrentState(HttpServletRequest request);

}
