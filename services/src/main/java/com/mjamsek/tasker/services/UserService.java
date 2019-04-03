package com.mjamsek.tasker.services;

import com.mjamsek.tasker.entities.dto.User;
import com.mjamsek.tasker.entities.persistence.UserEntity;

public interface UserService {
    
    UserEntity getUserByUsername(String username);
    
    UserEntity validateLogin(User user);
    
    UserEntity getUserById(long id);
    
    void changePassword(User dto, long userId);
    
    boolean createUser(User dto);
}
