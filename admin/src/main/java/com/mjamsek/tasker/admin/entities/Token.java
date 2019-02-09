package com.mjamsek.tasker.admin.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tokens", indexes = @Index(name = "NAME_UNIQUE_INDEX", columnList = "name", unique = true))
@NamedQueries({
    @NamedQuery(name = "Token.findByName", query = "SELECT t FROM Token t WHERE t.name = :name")
})
public class Token {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(unique = true)
    private String name;
    
    @Column()
    private Date expired;
    
    @JsonIgnore
    @Column(name = "secret_key")
    private String secretKey;
    
    @OneToMany(mappedBy = "token", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<TokenAction> allowedActions;
    
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getSecretKey() {
        return secretKey;
    }
    
    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
    
    public List<TokenAction> getAllowedActions() {
        return allowedActions;
    }
    
    public void setAllowedActions(List<TokenAction> allowedActions) {
        this.allowedActions = allowedActions;
    }
    
    public Date getExpired() {
        return expired;
    }
    
    public void setExpired(Date expired) {
        this.expired = expired;
    }
}
