package com.mjamsek.tasker.mappers;

import com.mjamsek.tasker.entities.persistence.service.*;
import com.mjamsek.tasker.lib.v1.*;
import com.mjamsek.tasker.lib.v1.Service;
import com.mjamsek.tasker.lib.v1.ServiceDeployment;
import com.mjamsek.tasker.lib.v1.enums.ServiceType;

public class ServiceMapper {
    
    public static Service fromEntity(ServiceEntity entity) {
        
        if (entity == null) {
            return null;
        }
        
        Service service;
        
        if (entity instanceof ApiServiceEntity) {
            service = new ApiService();
            service.setType(ServiceType.API_SERVICE);
            ApiServiceEntity apiServiceEntity = (ApiServiceEntity) entity;
            ((ApiService) service).setBaseUrl(apiServiceEntity.getBaseUrl());
            ((ApiService) service).setHealthcheckUrl(apiServiceEntity.getHealthcheckUrl());
            ((ApiService) service).setMajorVersion(apiServiceEntity.getMajorVersion());
        } else if (entity instanceof WebAppServiceEntity) {
            service = new WebApp();
            service.setType(ServiceType.WEB_APP);
            WebAppServiceEntity webAppServiceEntity = (WebAppServiceEntity) entity;
            ((WebApp) service).setApplicationUrl(webAppServiceEntity.getApplicationUrl());
            ((WebApp) service).setBaseUrl(webAppServiceEntity.getBaseUrl());
            ((WebApp) service).setHealthcheckUrl(webAppServiceEntity.getHealthcheckUrl());
            ((WebApp) service).setMajorVersion(webAppServiceEntity.getMajorVersion());
        } else if (entity instanceof ClientAppServiceEntity) {
            service = new ClientApp();
            service.setType(ServiceType.CLIENT_APP);
            ClientAppServiceEntity clientAppServiceEntity = (ClientAppServiceEntity) entity;
            ((ClientApp) service).setApplicationUrl(clientAppServiceEntity.getApplicationUrl());
        } else {
            return null;
        }
        
        service.setId(entity.getId());
        service.setActive(entity.getActive());
        service.setName(entity.getName());
        service.setDescription(entity.getDescription());
        service.setVersion(entity.getVersion());
        
        if (entity.getDeployment() != null) {
            ServiceDeployment deployment = ServiceMapper.deploymentFromEntity(entity.getDeployment());
            service.setDeployment(deployment);
        }
        
        return service;
    }
    
    public static ServiceEntity toEntity(Service service) {
        ServiceEntity entity;
        
        switch (service.getType()) {
            case API_SERVICE:
                entity = new ApiServiceEntity();
                ApiService apiService = (ApiService) service;
                ((ApiServiceEntity) entity).setBaseUrl(apiService.getBaseUrl());
                ((ApiServiceEntity) entity).setHealthcheckUrl(apiService.getHealthcheckUrl());
                ((ApiServiceEntity) entity).setMajorVersion(apiService.getMajorVersion());
                break;
            case WEB_APP:
                entity = new WebAppServiceEntity();
                WebApp webApp = (WebApp) service;
                ((WebAppServiceEntity) entity).setApplicationUrl(webApp.getApplicationUrl());
                ((WebAppServiceEntity) entity).setBaseUrl(webApp.getBaseUrl());
                ((WebAppServiceEntity) entity).setHealthcheckUrl(webApp.getHealthcheckUrl());
                ((WebAppServiceEntity) entity).setMajorVersion(webApp.getMajorVersion());
                break;
            case CLIENT_APP:
                entity = new ClientAppServiceEntity();
                ClientApp clientApp = (ClientApp) service;
                ((ClientAppServiceEntity) entity).setApplicationUrl(clientApp.getApplicationUrl());
                break;
            default:
                return null;
        }
        
        entity.setId(service.getId());
        entity.setActive(service.getActive());
        entity.setName(service.getName());
        entity.setDescription(service.getDescription());
        entity.setVersion(service.getVersion());
        if (service.getDeployment() != null) {
            ServiceDeploymentEntity serviceDeploymentEntity = ServiceMapper.deploymentToEntity(service.getDeployment());
            entity.setDeployment(serviceDeploymentEntity);
        }
        
        return entity;
    }
    
    public static ServiceDeployment deploymentFromEntity(ServiceDeploymentEntity entity) {
        ServiceDeployment deployment = new ServiceDeployment();
        deployment.setContainerId(entity.getContainerId());
        deployment.setId(entity.getId());
        deployment.setContainerName(entity.getContainerName());
        if (entity.getDockerEndpoint() != null) {
            deployment.setDockerEndpoint(DockerEndpointMapper.fromEntity(entity.getDockerEndpoint()));
        }
        return deployment;
    }
    
    public static ServiceDeploymentEntity deploymentToEntity(ServiceDeployment deployment) {
        ServiceDeploymentEntity entity = new ServiceDeploymentEntity();
        entity.setId(deployment.getId());
        entity.setContainerId(deployment.getContainerId());
        entity.setContainerName(deployment.getContainerName());
        if (deployment.getDockerEndpoint() != null) {
            DockerEndpointEntity dockerEndpointEntity = DockerEndpointMapper.toEntity(deployment.getDockerEndpoint());
            entity.setDockerEndpoint(dockerEndpointEntity);
        }
        return entity;
    }
}
