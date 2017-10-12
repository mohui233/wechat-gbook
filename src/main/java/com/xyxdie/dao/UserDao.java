package com.xyxdie.dao;

import java.util.List;

import com.xyxdie.model.User;

public interface UserDao {


    User findById(int id);

    List<User> findByName(String name);

    List<User> findByEmail(String email);

    void saveUser(User user);

    void updateUser(User user);

    void deleteUser(User user);

    Long findUserCount();

    List<User> findAllUser();

    List<User> findUserByPage(int pageNo, int pageSize);

}
