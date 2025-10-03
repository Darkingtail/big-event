package org.example.service;

import org.example.pojo.User;

public interface UserService {
    User getUserByName(String username);

    void register(String username, String password);
}
