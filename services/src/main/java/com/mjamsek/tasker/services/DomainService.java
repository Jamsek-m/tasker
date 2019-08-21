package com.mjamsek.tasker.services;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.mjamsek.tasker.lib.v1.Domain;
import com.mjamsek.tasker.lib.v1.exceptions.EntityNotFoundException;
import com.mjamsek.tasker.lib.v1.exceptions.PersistenceException;

import java.util.List;

public interface DomainService {
    
    List<Domain> getDomains(QueryParameters queryParameters);
    
    long getDomainsCount(QueryParameters queryParameters);
    
    Domain getDomain(String domainId) throws EntityNotFoundException;
    
    Domain createDomain(Domain domain) throws PersistenceException;
    
    Domain updateDomain(Domain domain, String domainId) throws PersistenceException;
    
    void deleteDomain(String domainId) throws PersistenceException;
}
