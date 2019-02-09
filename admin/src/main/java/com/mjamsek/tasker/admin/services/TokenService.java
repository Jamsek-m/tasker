package com.mjamsek.tasker.admin.services;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import com.mjamsek.tasker.admin.entities.LogSeverity;
import com.mjamsek.tasker.admin.entities.Token;
import com.mjamsek.tasker.admin.entities.TokenAction;
import com.mjamsek.tasker.admin.entities.dto.TokenDTO;
import com.mjamsek.tasker.admin.entities.exceptions.AdminException;
import com.mjamsek.tasker.admin.entities.exceptions.TokenExistsException;
import com.mjamsek.tasker.admin.utils.RandomStringGenerator;
import com.mjamsek.tasker.admin.utils.TokenMapper;
import org.mindrot.jbcrypt.BCrypt;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.*;
import javax.ws.rs.NotFoundException;
import java.security.SecureRandom;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class TokenService {
    
    @PersistenceContext(unitName = "main-jpa-unit")
    private EntityManager em;
    
    @Inject
    private LogService logService;
    
    public Token getTokenById(long tokenId) {
        return em.find(Token.class, tokenId);
    }
    
    public Token getTokenByName(String name) {
        try {
            TypedQuery<Token> query = em.createNamedQuery("Token.findByName", Token.class);
            query.setParameter("name", name);
            return query.getSingleResult();
        } catch (NonUniqueResultException | NoResultException exc) {
            return null;
        }
    }
    
    public List<TokenDTO> getTokens(QueryParameters query) {
        List<Token> tokens = JPAUtils.queryEntities(em, Token.class, query);
        return tokens.stream().map(TokenMapper::fromEntity).collect(Collectors.toList());
    }
    
    public long getTokensCount(QueryParameters query) {
        return JPAUtils.queryEntitiesCount(em, Token.class, query);
    }
    
    public void expireToken(long tokenId) {
        Token token = this.getTokenById(tokenId);
        if (token != null) {
            token.setExpired(new Date());
            em.getTransaction().begin();
            em.merge(token);
            em.getTransaction().commit();
            logService.log(LogSeverity.INFO, "Token with name '" + token.getName() + "' was expired");
        } else {
            logService.log(LogSeverity.ERROR, "Error expiring token. Token with id: "+ tokenId + " does not exists");
            throw new NotFoundException();
        }
    }
    
    public String createToken(TokenDTO tokenDTO) {
        if (this.getTokenByName(tokenDTO.getName()) != null) {
            throw new TokenExistsException("Token with name '" + tokenDTO.getName() + "' already exists!");
        }
        
        try {
            em.getTransaction().begin();
            Token token = TokenMapper.fromDTO(tokenDTO);
            String generatedKey = this.generateSecretKey();
            token.setSecretKey(BCrypt.hashpw(generatedKey, BCrypt.gensalt()));
            List<TokenAction> allowedActions = this.createActions(tokenDTO.getAllowedActions(), token);
            token.setAllowedActions(allowedActions);
            em.persist(token);
            em.getTransaction().commit();
            logService.log(LogSeverity.INFO, "Token with name '" + token.getName() + "' was created.");
            return generatedKey;
        } catch (Exception exc) {
            exc.printStackTrace();
            logService.log(LogSeverity.ERROR, "Error creating new token");
            em.getTransaction().rollback();
            throw new AdminException("Error creating token: " + exc.getMessage());
        }
    }
    
    public boolean validateToken(String secretKey, Token token, String actionName) {
        if (token.getExpired() != null) {
            logService.log(LogSeverity.WARNING, "Using expired token (token id: " + token.getId() +", token name: " + token.getName() +")");
            return false;
        }
        
        boolean actionIsAllowed = token.getAllowedActions().stream().anyMatch(action -> action.getAction().equals(actionName));
        
        return actionIsAllowed && BCrypt.checkpw(secretKey, token.getSecretKey());
    }
    
    public boolean validateToken(String secretKey, String tokenName, String actionName) {
        Token token = this.getTokenByName(tokenName);
        if (token == null) {
            return false;
        }
        return this.validateToken(secretKey, token, actionName);
    }
    
    private List<TokenAction> createActions(List<String> allowedActions, Token token) {
        return allowedActions.stream().map(action -> {
            TokenAction tokenAction = new TokenAction();
            tokenAction.setToken(token);
            tokenAction.setAction(action);
            return tokenAction;
        }).collect(Collectors.toList());
    }
    
    private String generateSecretKey() {
        return RandomStringGenerator.generate(40);
    }
    
}
