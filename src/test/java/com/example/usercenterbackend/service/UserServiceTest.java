package com.example.usercenterbackend.service;

import com.example.usercenterbackend.common.CommonRsp;
import com.example.usercenterbackend.mapper.UserMapper;
import com.example.usercenterbackend.model.User;
import com.example.usercenterbackend.model.UserVo;
import org.assertj.core.internal.Lists;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@SpringBootTest
public class UserServiceTest {
    @Resource
    UserService uerService;

    @Resource
    UserMapper userMapper;

    @Before
    public void setUp() throws Exception {
    }


    @Test
    public void testAddUser() {
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



    }


    @Test
    public void testSelectUser() {
        List<User> users = userMapper.selectList(null);

        for (User user : users) {
            System.out.println(user);
        }
    }

    @Test
    void userRegister() {
//        long weTest001 = uerService.userRegister("weTest001", "12345678", "12345678");
//        assert weTest001 > 0;
    }

    @Test
    void userLogin() {
        CommonRsp<UserVo> weTest001 = uerService.userLogin("weTest001", "12345678", null);
        System.out.println(weTest001);
        assert weTest001 != null;
    }

    @Test
    void userCombine() {
        User user1 = new User();
        user1.setUserName("user1");
        user1.setGender(10);
        user1.setUserStatus(1);
        User user2 = new User();
        user2.setUserName("user2");
        user2.setGender(3);
        user2.setUserStatus(1);

        User user3 = new User();
        user3.setUserName("user3");
        user3.setGender(13);
        user3.setUserStatus(6);

        List<User> users = Arrays.asList(user1, user2, user3);

        // 根据userStatus分组，然后将每个分组中的用户按照gender进行累加得到不同userStatus组下的gender总和 以map类型返回，使用stream.collect(Collector.groupBy())方法
        Map<Integer, Integer> collect = users.stream().collect(Collectors.groupingBy(User::getUserStatus,
                Collector.of(() -> new int[1]
                        , (sum, user) -> sum[0] +=
                                user.getGender(), (sum1, sum2) -> {
                            sum1[0] += sum2[0];
                            return sum1;
                        }, sum -> sum[0])));
        System.out.println(collect);
    }
}