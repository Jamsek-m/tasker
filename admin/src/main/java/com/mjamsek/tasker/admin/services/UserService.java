package com.mjamsek.tasker.admin.services;

import com.mjamsek.tasker.admin.entities.User;
import com.mjamsek.tasker.admin.entities.dto.UserDTO;
import com.mjamsek.tasker.admin.entities.exceptions.BadLoginException;
import org.mindrot.jbcrypt.BCrypt;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@ApplicationScoped
public class UserService {
    
    @PersistenceContext(unitName = "main-jpa-unit")
    private EntityManager em;
    
    
    public User getUserByUsername(String username) {
        TypedQuery<User> query = em.createNamedQuery("User.findByUsername", User.class);
        query.setParameter("username", username);
        try {
            return query.getSingleResult();
        } catch (NoResultException exc) {
            return null;
        }
    }
    
    public User validateLogin(UserDTO dto) {
        User user = this.getUserByUsername(dto.getUsername());
        if (user == null) {
            throw new BadLoginException();
        }
        if (!BCrypt.checkpw(dto.getPassword(), user.getPassword())) {
            throw new BadLoginException();
        }
        return user;
    }
    
    public User getUserById(long id) {
        try {
            return em.find(User.class, id);
        } catch (NoResultException exc) {
            return null;
        }
    }
    
    public void changePassword(String newPassword, long userId) {
        User user = getUserById(userId);
        if (user == null) {
            throw new BadLoginException();
        }
        user.setPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt()));
        try {
            em.getTransaction().begin();
            em.merge(user);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
    }
    
    public boolean createUser(UserDTO dto) {
        User user = new User();
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
