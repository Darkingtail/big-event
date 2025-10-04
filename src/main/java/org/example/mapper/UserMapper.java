package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.pojo.User;

import java.time.LocalDateTime;

@Mapper
public interface UserMapper {
    User getUserByName(@Param("username") String username);

    void add(@Param("username") String username, @Param("md5String") String md5String);

    boolean update(User user);

    void updateAvatar(@Param("userPic") String avatar, @Param("id") Integer id, @Param("updateTime") LocalDateTime updateTime);

    void updatePassword(@Param("password") String password, @Param("id") Integer id, @Param("updateTime") LocalDateTime updateTime);
}
