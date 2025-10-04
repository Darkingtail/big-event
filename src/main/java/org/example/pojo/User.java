package org.example.pojo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;

// lombok 在编译阶段，为实体类自动生成getter、setter、toString
@Data
public class User {
    @NotNull
    private Integer id;//主键ID
    @NotNull
    private String username;//用户名
    @JsonIgnore // 在spring转换为json字符串的时候忽略这个字段
    private String password;//密码
    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9]{6,18}$", message = "昵称必须是6-18位数字或字母")
    private String nickname;//昵称
    @NotNull
    @Email
    private String email;//邮箱
    @NotNull
    @URL
    private String userPic;//用户头像地址
    private LocalDateTime createTime;//创建时间
    private LocalDateTime updateTime;//更新时间
}
