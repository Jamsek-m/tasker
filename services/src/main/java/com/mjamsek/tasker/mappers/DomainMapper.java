package com.mjamsek.tasker.mappers;

import com.mjamsek.tasker.entities.persistence.DomainEntity;
import com.mjamsek.tasker.lib.v1.Domain;

public class DomainMapper {
    
    public static Domain fromEntity(DomainEntity entity) {
        if (entity == null) return null;
        
        Domain domain = new Domain();
        domain.setId(entity.getId());
        domain.setDomain(entity.getDomain());
        domain.setSslEnabled(entity.getSslEnabled());
        return domain;
    }
    
    public static DomainEntity toEntity(Domain domain) {
        if (domain == null) return null;
        
        DomainEntity entity = new DomainEntity();
        entity.setId(domain.getId());
        entity.setDomain(domain.getDomain());
        if (domain.getSslEnabled() == null) {
            entity.setSslEnabled(false);
        } else {
            entity.setSslEnabled(domain.getSslEnabled());
        }
        return entity;
    }
}
