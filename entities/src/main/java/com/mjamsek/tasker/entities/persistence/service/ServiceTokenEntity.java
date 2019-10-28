package com.mjamsek.tasker.entities.persistence.service;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "service_tokens")
@NamedQueries({
    @NamedQuery(name = ServiceTokenEntity.FIND_BY_USER_AND_SERVICE,
        query = "SELECT s FROM ServiceTokenEntity s WHERE s.userId = :userId AND s.service.id = :serviceId"),
    @NamedQuery(name = ServiceTokenEntity.FIND_BY_TOKEN_ID,
        query = "SELECT s FROM ServiceTokenEntity s WHERE s.tokenId = :tokenId")
})
public class ServiceTokenEntity {
    
    public static final String FIND_BY_USER_AND_SERVICE = "ServiceToken.findByUserAndService";
    public static final String FIND_BY_TOKEN_ID = "ServiceToken.findByTokenId";
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    protected String id;
    
    @Column(name = "user_id")
    private String userId;
    
    @Column(name = "token_id", unique = true)
    private String tokenId;
    
    @Column(name = "token")
    private String token;
    
    @ManyToOne
    @JoinColumn(name = "service_id")
    private ServiceEntity service;
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public ServiceEntity getService() {
        return service;
    }
    
    public void setService(ServiceEntity service) {
        this.service = service;
    }
    
    public String getTokenId() {
        return tokenId;
    }
    
    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }
}
