package org.example.service.impl;

import jakarta.annotation.Resource;
import org.example.mapper.UserMapper;
import org.example.pojo.User;
import org.example.service.UserService;
import org.example.utils.Md5Util;
import org.example.utils.ThreadLocalUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User getUserByName(String username) {
        return userMapper.getUserByName(username);
    }

    @Override
    public void register(String username, String password) {
        // 加密
        String md5String = Md5Util.getMD5String(password);
        // 注册
        userMapper.add(username, md5String);
    }

    @Override
    public boolean update(User user) {
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(Md5Util.getMD5String(user.getPassword()));
        }
        user.setUpdateTime(LocalDateTime.now());
        return userMapper.update(user);
    }

    @Override
    public void updateAvatar(String avatar) {
        Map<String, Object> o = ThreadLocalUtil.get();
        Integer id = (Integer) o.get("id");
        userMapper.updateAvatar(avatar, id, LocalDateTime.now());
    }

    @Override
    public void updatePassword(String password, Integer id) {
        userMapper.updatePassword(password, id, LocalDateTime.now());
    }
}