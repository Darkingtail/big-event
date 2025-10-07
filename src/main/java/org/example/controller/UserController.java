package org.example.controller;

import jakarta.validation.constraints.Pattern;
import org.example.pojo.Result;
import org.example.pojo.User;
import org.example.service.UserService;
import org.example.utils.JwtUtil;
import org.example.utils.Md5Util;
import org.example.utils.ThreadLocalUtil;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private HttpServletRequest request;

    @Value("${token}")
    private String tokenHeaderName;

    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^[a-zA-Z0-9]{6,18}$", message = "用户名必须是6-18位数字或字母") String username, @Pattern(regexp = "^[a-zA-Z0-9]{6,18}$", message = "密码必须是6-18位数字或字母") String password) {
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
    public Result<String> login(@Pattern(regexp = "^[a-zA-Z0-9]{6,18}$", message = "用户名必须是6-18位数字或字母") String username, @Pattern(regexp = "^[a-zA-Z0-9]{6,18}$", message = "密码必须是6-18位数字或字母") String password) {
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
            // map.put("password", user.getPassword());
            String token = JwtUtil.genToken(map, 1000L * 30); // 30秒过期，测试redis
            // 将redis添加到redis,设置key和value都是token
            stringRedisTemplate.opsForValue().set(token, token, 30, TimeUnit.SECONDS);
            return Result.success(token);
        }
    }

    @PostMapping("/info")
    public Result<User> info() {
        Map<String, Object> stringObjectMap = (Map<String, Object>) ThreadLocalUtil.get();
        String userName = (String) stringObjectMap.get("username");
        return Result.success(userService.getUserByName(userName));
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user) {
        try {
            User userByName = userService.getUserByName(user.getUsername());
            if (null == userByName) {
                return Result.error("用户不存在");
            }
            boolean update = userService.update(user);
            System.out.println("/user/update 更新结果： " + update);
            return update ? Result.success(update) : Result.error("更新失败");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PatchMapping("/updateAavtar")
    public Result updateAavtar(@RequestParam @URL @Validated String avatar) {
        try {
            if (null == avatar) {
                return Result.error("头像不能为空");
            }
            userService.updateAvatar(avatar);
            return Result.success("更新头像成功");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PatchMapping("/updatePwd")
    public Result updatePassword(@RequestBody Map<String, String> params) {
        try {
            String old_pwd = params.get("old_pwd");
            String new_pwd = params.get("new_pwd");
            String re_pwd = params.get("re_pwd");
            if (!StringUtils.hasLength(old_pwd)) {
                return Result.error("旧密码不能为空");
            }
            if (!StringUtils.hasLength(new_pwd)) {
                return Result.error("新密码不能为空");
            }
            if (!StringUtils.hasLength(re_pwd)) {
                return Result.error("确认密码不能为空");
            }
            Map<String, Object> o = ThreadLocalUtil.get();
            String username = (String) o.get("username");
            Integer id = (Integer) o.get("id");
            String pwd_enter = Md5Util.getMD5String(old_pwd);
            String pwd = userService.getUserByName(username).getPassword();
            if (!pwd.equals(pwd_enter)) {
                return Result.error("旧密码错误");
            }
            if (!new_pwd.equals(re_pwd)) {
                return Result.error("新密码和确认密码不一致");
            }
            String p = Md5Util.getMD5String(new_pwd);
            userService.updatePassword(p, id);
            // 删除redis中老的token
            String token = request.getHeader(tokenHeaderName);
            if (StringUtils.hasLength(token)) {
                stringRedisTemplate.opsForValue().getOperations().delete(token);
            }
            return Result.success("更新密码成功");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
