package com.xyxdie.service;

import java.util.List;
import com.xyxdie.model.User;


public interface UserService {

    User findById(int id);
    
    User findByName(String name);

    void saveUser(User user);

    void updateUser(User user);

    void deleteUser(User user);

    Long findUserCount();
    
    Long findUserCount(String str);

    List<User> findUserByPage(int pageNo, int pageSize);

    List<User> findAllUser();

	List<User> findUserByOpenId(String str);
}
