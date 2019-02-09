package com.mjamsek.tasker.resources;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.mjamsek.tasker.admin.auth.SecureResource;
import com.mjamsek.tasker.admin.entities.dto.TokenDTO;
import com.mjamsek.tasker.admin.services.TokenService;
import com.mjamsek.tasker.http.HttpHeader;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestScoped
@Path("/tokens")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TokenResource {
    
    @Context
    protected UriInfo uriInfo;
    
    @Inject
    private TokenService tokenService;
    
    @GET
    @Path("/{name}")
    @SecureResource
    public Response getTokenByName(@PathParam("name") String tokenName) {
        return Response.ok(tokenService.getTokenByName(tokenName)).build();
    }
    
    @GET
    @SecureResource
    public Response getTokensByPage() {
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        List<TokenDTO> tokens = tokenService.getTokens(query);
        long tokenCount = tokenService.getTokensCount(query);
        return Response.ok(tokens).header(HttpHeader.X_TOTAL_COUNT, tokenCount).build();
    }
    
    @POST
    @SecureResource
    public Response saveToken(TokenDTO tokenDTO) {
        String generatedKey = tokenService.createToken(tokenDTO);
        Map<String, String> response = new HashMap<>();
        response.put("generatedKey", generatedKey);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }
    
    @DELETE
    @Path("/{id}/expire")
    @SecureResource
    public Response expireToken(@PathParam("id") long tokenId) {
        tokenService.expireToken(tokenId);
        return Response.noContent().build();
    }
    
}
