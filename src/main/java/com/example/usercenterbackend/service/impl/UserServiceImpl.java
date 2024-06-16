package com.example.usercenterbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.usercenterbackend.model.User;
import com.example.usercenterbackend.service.UserService;
import com.example.usercenterbackend.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author 122
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2024-06-14 22:26:29
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




