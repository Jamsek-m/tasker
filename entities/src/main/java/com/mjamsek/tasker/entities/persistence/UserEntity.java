package com.mjamsek.tasker.entities.persistence;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "users", indexes = @Index(name = "USERNAME_UNIQUE_INDEX", columnList = "username"))
@NamedQueries({
    @NamedQuery(name = UserEntity.FIND_BY_USERNAME, query = "SELECT u FROM UserEntity u WHERE u.username = :username")
})
public class UserEntity {
    
    public static final String FIND_BY_USERNAME = "UserEntity.findByUsername";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(unique = true, nullable = false)
    private String username;
    
    @JsonIgnore
    @Column(nullable = false)
    private String password;
    
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
}
