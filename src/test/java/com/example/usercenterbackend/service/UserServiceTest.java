package com.example.usercenterbackend.service;

import com.example.usercenterbackend.model.User;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Date;

import static org.junit.Assert.*;

@SpringBootTest
public class UserServiceTest {
    @Resource
    UserService uerService;

    @Before
    public void setUp() throws Exception {
    }


    @Test
    public void testAddUser(){
        User user = new User();
        user.setId(0L);
        user.setUserName("zhang3");
        user.setUserAccount("123123");
        user.setAvatarUrl("https://i0.hdslb.com/bfs/face/70b60d4a0f0d7df8a09c3ac77066a44a5099b78c.jpg@240w_240h_1c_1s_!web-avatar-space-header.avif");
        user.setGender(0);
        user.setUserPassword("123abc");
        user.setPhone("187676");
        user.setEmail("1773465183@qq.com");
        user.setUserStatus(0);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        user.setDeleted(0);

        boolean save = uerService.save(user);
        System.out.println(save);


    }
}