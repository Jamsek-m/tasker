package com.mjamsek.tasker.services.impl;

import com.mjamsek.tasker.entities.dto.User;
import com.mjamsek.tasker.entities.exceptions.BadLoginException;
import com.mjamsek.tasker.entities.persistence.auth.UserEntity;
import com.mjamsek.tasker.services.UserService;
import org.mindrot.jbcrypt.BCrypt;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@ApplicationScoped
public class UserServiceImpl implements UserService {
    
    @PersistenceContext(unitName = "main-jpa-unit")
    private EntityManager em;
    
    @Override
    public UserEntity getUserByUsername(String username) {
        TypedQuery<UserEntity> query = em.createNamedQuery(UserEntity.FIND_BY_USERNAME, UserEntity.class);
        query.setParameter("username", username);
        try {
            return query.getSingleResult();
        } catch (NoResultException exc) {
            return null;
        }
    }
    
    @Override
    public UserEntity validateLogin(User dto) throws BadLoginException {
        UserEntity user = this.getUserByUsername(dto.getUsername());
        if (user == null) {
            throw new BadLoginException();
        }
        if (!BCrypt.checkpw(dto.getPassword(), user.getPassword())) {
            throw new BadLoginException();
        }
        return user;
    }
    
    @Override
    public UserEntity getUserById(long id) {
        try {
            return em.find(UserEntity.class, id);
        } catch (NoResultException exc) {
            return null;
        }
    }
    
    @Override
    public void changePassword(User dto, long userId) {
        UserEntity user = getUserById(userId);
        if (user == null) {
            throw new BadLoginException();
        }
        user.setPassword(BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt()));
        try {
            em.getTransaction().begin();
            em.merge(user);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
    }
    
    @Override
    public boolean createUser(User dto) {
        UserEntity user = new UserEntity();
        user.setUsername(dto.getUsername());
        user.setPassword(BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt()));
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            return false;
        }
    }
}
