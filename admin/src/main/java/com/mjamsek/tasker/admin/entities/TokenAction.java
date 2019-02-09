package com.mjamsek.tasker.admin.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "token_actions")
@NamedQueries({
    @NamedQuery(name = "TokenAction.findByList", query = "SELECT ta FROM TokenAction ta WHERE ta.action IN :actions")
})
public class TokenAction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "token_id")
    private Token token;
    
    private String action;
    
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public Token getToken() {
        return token;
    }
    
    public void setToken(Token token) {
        this.token = token;
    }
    
    public String getAction() {
        return action;
    }
    
    public void setAction(String action) {
        this.action = action;
    }
}
