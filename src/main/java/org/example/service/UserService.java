package org.example.service;

import org.example.pojo.User;
import org.hibernate.validator.constraints.URL;

public interface UserService {
    User getUserByName(String username);

    void register(String username, String password);

    boolean update(User user);

    void updateAvatar(@URL String avatar);

    void updatePassword(String newPwd, Integer id);
}
