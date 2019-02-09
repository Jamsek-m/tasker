package com.mjamsek.tasker.admin.utils;

import com.mjamsek.tasker.admin.entities.Token;
import com.mjamsek.tasker.admin.entities.TokenAction;
import com.mjamsek.tasker.admin.entities.dto.TokenDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TokenMapper {
    
    public static TokenDTO fromEntity(Token token) {
        TokenDTO dto = new TokenDTO();
        dto.setId(token.getId());
        dto.setName(token.getName());
        dto.setExpired(token.getExpired());
        List<String> allowedActions = token.getAllowedActions().stream()
            .map(TokenAction::getAction)
            .collect(Collectors.toList());
        dto.setAllowedActions(allowedActions);
        return dto;
    }
    
    public static Token fromDTO(TokenDTO dto) {
        Token token = new Token();
        token.setName(dto.getName());
        token.setId(dto.getId());
        token.setExpired(dto.getExpired());
        token.setAllowedActions(new ArrayList<>());
        return token;
    }
    
}
