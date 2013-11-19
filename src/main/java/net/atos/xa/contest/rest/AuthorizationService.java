/*
****************************************************
* Atos Worldline -- Global Platforms and Solutions

* Atos Worldline is an Atos company
* All rights reserved
*
*****************************************************
*/
package net.atos.xa.contest.rest;

import net.atos.xa.contest.business.AuthorizationManager;
import net.atos.xa.contest.dto.AuthorizationRequest;
import net.atos.xa.contest.dto.AuthorizationResponse;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/rest")
public class AuthorizationService {

    @Inject
    AuthorizationManager authorizationManager;

    @GET
    @Path("/ping")
    @Produces(MediaType.TEXT_PLAIN)
    public String ping(){
        return "pong";
    }

    @GET
    @Path("/reset")
    @Produces(MediaType.TEXT_PLAIN)
    public String reset(){
        authorizationManager.reset();
        return "ok";
    }

    @POST
    @Path("/authorized")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public AuthorizationResponse authorized(AuthorizationRequest authorizationRequest){
        System.out.println("Server side request = "+authorizationRequest);
        AuthorizationResponse authorizationResponse = authorizationManager.authorized(authorizationRequest);
        System.out.println("Server side response = "+authorizationResponse);
        return authorizationResponse;
    }

}
