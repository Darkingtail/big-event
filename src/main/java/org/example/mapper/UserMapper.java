package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.pojo.User;

@Mapper
public interface UserMapper {
    User getUserByName(@Param("username") String username);

    void add(@Param("username") String username, @Param("md5String") String md5String);
}
