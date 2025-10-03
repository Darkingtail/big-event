package org.example.controller;

import jakarta.validation.constraints.Pattern;
import org.example.pojo.Result;
import org.example.pojo.User;
import org.example.service.UserService;
import org.example.utils.JwtUtil;
import org.example.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result register(
            @Pattern(regexp = "^[a-zA-Z0-9]{6,18}$", message = "用户名必须是6-18位数字或字母") String username,
            @Pattern(regexp = "^[a-zA-Z0-9]{6,18}$", message = "密码必须是6-18位数字或字母") String password) {
        User user = userService.getUserByName(username);
        if (null == user) {
            // 注册
            userService.register(username, password);
            return Result.success();
        } else {
            return Result.error("用户名已存在");
        }
    }

    @PostMapping("/login")
    public Result<String> login(
            @Pattern(regexp = "^[a-zA-Z0-9]{6,18}$", message = "用户名必须是6-18位数字或字母") String username,
            @Pattern(regexp = "^[a-zA-Z0-9]{6,18}$", message = "密码必须是6-18位数字或字母") String password) {
        User user = userService.getUserByName(username);
        if (null == user) {
            return Result.error("用户不存在");
        } else {

            if (!user.getPassword().equals(Md5Util.getMD5String(password))) {
                return Result.error("密码错误");
            }

            Map<String, Object> map = new HashMap<>();
            map.put("id", user.getId());
            map.put("username", user.getUsername());
            map.put("password", user.getPassword());
            String s = JwtUtil.genToken(map, 1000L * 60);
            return Result.success(s);
        }
    }
}
