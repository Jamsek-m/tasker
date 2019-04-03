package com.mjamsek.tasker.mappers;

import com.mjamsek.tasker.entities.dto.User;
import com.mjamsek.tasker.entities.persistence.UserEntity;

public class UserMapper {
    
    public static User fromEntity(UserEntity entity) {
        User user = new User();
        user.setUsername(entity.getUsername());
        user.setId(entity.getId());
        return user;
    }
}
