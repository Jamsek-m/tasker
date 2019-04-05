package com.mjamsek.tasker.services;

import com.mjamsek.tasker.entities.dto.User;
import com.mjamsek.tasker.entities.exceptions.BadLoginException;
import com.mjamsek.tasker.entities.persistence.auth.UserEntity;

public interface UserService {
    
    UserEntity getUserByUsername(String username);
    
    UserEntity validateLogin(User user) throws BadLoginException;
    
    UserEntity getUserById(long id);
    
    void changePassword(User dto, long userId);
    
    boolean createUser(User dto);
}
