package com.mjamsek.tasker.services.impl;

import com.kumuluz.ee.rest.beans.QueryFilter;
import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.enums.FilterOperation;
import com.kumuluz.ee.rest.utils.JPAUtils;
import com.mjamsek.tasker.entities.persistence.DomainEntity;
import com.mjamsek.tasker.entities.persistence.ServerEntity;
import com.mjamsek.tasker.entities.persistence.service.*;
import com.mjamsek.tasker.lib.v1.Statistics;
import com.mjamsek.tasker.services.StatisticsService;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ApplicationScoped
public class StatisticsServiceImpl implements StatisticsService {
    
    @PersistenceContext(unitName = "main-jpa-unit")
    private EntityManager em;
    
    @Override
    public Statistics getStatistics() {
        Statistics statistics = new Statistics();
    
        QueryParameters queryWithActive = new QueryParameters();
        queryWithActive.getFilters().add(new QueryFilter("active", FilterOperation.EQ, "true"));
        
        long totalServices = JPAUtils.queryEntitiesCount(em, ServiceEntity.class, queryWithActive);
        statistics.setTotalServices(totalServices);
        
        long totalClientApps = JPAUtils.queryEntitiesCount(em, ClientAppServiceEntity.class, queryWithActive);
        statistics.setTotalClientApps(totalClientApps);
        
        long totalWebApps = JPAUtils.queryEntitiesCount(em, WebAppServiceEntity.class, queryWithActive);
        statistics.setTotalWebApps(totalWebApps);
        
        long totalApiServices = JPAUtils.queryEntitiesCount(em, ApiServiceEntity.class, queryWithActive);
        statistics.setTotalApiServices(totalApiServices);
        
        QueryParameters query = new QueryParameters();
        query.getFilters().add(new QueryFilter("deployment", FilterOperation.ISNOTNULL));
        long totalDeployedServices = JPAUtils.queryEntitiesCount(em, ServiceEntity.class, query);
        statistics.setTotalDeployedServices(totalDeployedServices);
        
        long totalDockerEndpoints = JPAUtils.queryEntitiesCount(em, DockerEndpointEntity.class, new QueryParameters());
        statistics.setTotalDockerEndpoints(totalDockerEndpoints);
        
        long totalDomains = JPAUtils.queryEntitiesCount(em, DomainEntity.class, new QueryParameters());
        statistics.setTotalDomains(totalDomains);
        
        long totalServers = JPAUtils.queryEntitiesCount(em, ServerEntity.class, new QueryParameters());
        statistics.setTotalServers(totalServers);
        
        return statistics;
    }
}
