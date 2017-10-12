package com.xyxdie.service;

import java.util.List;

import com.xyxdie.model.User;


public interface UserService {

    User findById(int id);

    User findByName(String name);

    User findByEmail(String email);

    void saveUser(User user);

    void updateUser(User user);

    void deleteUser(User user);

    Long findUserCount();

    List<User> findUserByPage(int pageNo, int pageSize);

    List<User> findAllUser();

    boolean isUserEmailExist(String email);

    boolean isUserEmailExistExceptSelf(String sqlEmail, String localEmail);

}
