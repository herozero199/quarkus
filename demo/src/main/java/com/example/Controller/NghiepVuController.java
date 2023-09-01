package com.example.Controller;

import com.example.Service.NghiepVuService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/nghiepvu")
public class NghiepVuController {
    @Inject
    NghiepVuService nghiepVuService;

    @Path("/get1")
    @GET
    public Response get1() {
        return Response.status(Response.Status.OK).entity(this.nghiepVuService.get1()).build();
    }

    @Path("/get2")
    @GET
    public Response get2() {return Response.status(Response.Status.OK).entity(this.nghiepVuService.get2()).build();}
}
