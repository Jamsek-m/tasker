package com.mjamsek.tasker.resources;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.mjamsek.auth.keycloak.annotations.AuthenticatedAllowed;
import com.mjamsek.auth.keycloak.annotations.ClientRolesAllowed;
import com.mjamsek.auth.keycloak.annotations.SecureResource;
import com.mjamsek.tasker.lib.v1.Domain;
import com.mjamsek.tasker.lib.v1.common.Auth;
import com.mjamsek.tasker.lib.v1.common.HttpHeader;
import com.mjamsek.tasker.services.DomainService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path("/domains")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@SecureResource
public class DomainResource {
    
    @Inject
    private DomainService domainService;
    
    @Context
    private UriInfo uriInfo;
    
    @GET
    @AuthenticatedAllowed
    public Response getDomains() {
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        List<Domain> domains = domainService.getDomains(query);
        long domainsCount = domainService.getDomainsCount(query);
        return Response.ok(domains).header(HttpHeader.X_TOTAL_COUNT, domainsCount).build();
    }
    
    @GET
    @Path("/{domainId}")
    @AuthenticatedAllowed
    public Response getDomain(@PathParam("domainId") String domainId) {
        return Response.ok(domainService.getDomain(domainId)).build();
    }
    
    @POST
    @ClientRolesAllowed(client = Auth.CLIENT_ID, roles = {Auth.DEVELOPER_ROLE})
    public Response createDomain(Domain domain) {
        return Response.status(Response.Status.CREATED).entity(domainService.createDomain(domain)).build();
    }
    
    @PATCH
    @Path("/{domainId}")
    @ClientRolesAllowed(client = Auth.CLIENT_ID, roles = {Auth.DEVELOPER_ROLE})
    public Response updateDomain(@PathParam("domainId") String domainId, Domain domain) {
        return Response.ok(domainService.updateDomain(domain, domainId)).build();
    }
    
    @DELETE
    @Path("/{domainId}")
    @ClientRolesAllowed(client = Auth.CLIENT_ID, roles = {Auth.DEVELOPER_ROLE})
    public Response deleteDomain(@PathParam("domainId") String domainId) {
        domainService.deleteDomain(domainId);
        return Response.noContent().build();
    }
    
}
