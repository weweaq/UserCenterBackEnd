package com.example.usercenterbackend.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.usercenterbackend.constant.UserType;
import com.example.usercenterbackend.model.User;
import com.example.usercenterbackend.model.UserVo;
import com.example.usercenterbackend.service.UserService;
import com.example.usercenterbackend.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author 122
 * @description 针对表【user(用户表)】的数据库操作Service实现
 * @createDate 2024-06-14 22:26:29
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Resource
    UserMapper userMapper;

    @Override
    public long userRegister(String account, String password, String checkPassword) {
        Integer x = checkPassedInValid(account, password, checkPassword);
        if (x != null) return x;

        String digestAsHexPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes(StandardCharsets.UTF_8));

        User entity = User.builder().userAccount(account).userPassword(digestAsHexPassword).build();

        int insertCount = userMapper.insert(entity);
        if (insertCount != 1) {
            return -8;
        }

        System.out.println("userRegister success, user id: [].");
        return entity.getId();
    }

    @Override
    public UserVo userLogin(String account, String password, HttpServletRequest request) {
        Integer checkPassword = checkAccountAndPassword(account, password, "checkPassword");
        if (!Objects.isNull(checkPassword)) {
            return null;
        }

        User entity = new LambdaQueryChainWrapper<>(userMapper).eq(User::getUserAccount, account).eq(User::getUserPassword, DigestUtils.md5DigestAsHex((SALT + password).getBytes(StandardCharsets.UTF_8))).one();
        if (!Objects.isNull(request)) {
            request.getSession().setAttribute(USER_LOGIN_STATE, entity);
        }
        return new UserVo(entity);
    }

    @Override
    public List<UserVo> userSelect(String name, HttpServletRequest request) {
        if (StringUtils.isAnyBlank(name)) {
            log.error("invalid name in userSelect!");
            return Collections.emptyList();
        }
        if (isAdminUser(request)){
            return Collections.emptyList();
        }
        List<User> list = new LambdaQueryChainWrapper<>(userMapper).like(User::getUserName, name).list();
        return list.stream().map(UserVo::new).collect(Collectors.toList());
    }

    private boolean isAdminUser(HttpServletRequest request) {
        Object attribute = request.getSession().getAttribute(USER_LOGIN_STATE);
        if (attribute == null) {
            log.error("user not login in userSelect!");
            return true;
        }

        // TODO: 2024/6/20 把用户类型整成枚举
        User user = (User) attribute;
        if (user.getUserType() != UserType.ADMIN_USER) {
            log.error("invalid user type in userSelect!");
            return true;
        }
        return false;
    }

    @Override
    public boolean userDelete(long id, HttpServletRequest request) {
        if (isAdminUser(request)){
            return false;
        }
        return userMapper.deleteById(id) == 1;
    }

    private Integer checkPassedInValid(String account, String password, String checkPassword) {
        Integer x = checkAccountAndPassword(account, password, checkPassword);
        if (x != null) return x;

        if (!Objects.equals(password, checkPassword)) {
            return -5;
        }

        if (new LambdaQueryChainWrapper<>(userMapper).eq(User::getUserAccount, account).exists()) {
            return -6;
        }
        return null;
    }

    private Integer checkAccountAndPassword(String account, String password, String checkPassword) {
        if (StringUtils.isAnyBlank(account, password, checkPassword)) {
            return -1;
        }

        if (account.length() < 4 || isAccountSpecial(account)) {
            return -2;
        }

        if (password.length() < 8) {
            return -3;
        }
        return null;
    }

    // TODO: 2024/6/19 需要校验特殊字符
    private boolean isAccountSpecial(String account) {
        return false;
    }
}




