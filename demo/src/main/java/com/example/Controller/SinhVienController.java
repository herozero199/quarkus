package com.example.Controller;

import com.example.Entity.SinhVien;
import com.example.Service.SinhVienService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.hibernate.exception.JDBCConnectionException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Path("/sinhvien")
public class SinhVienController {
    private final SinhVienService SinhVienService;
    private final Validator validator;

    @Inject
    public SinhVienController (Validator validator, SinhVienService SinhVienService) {
        this.SinhVienService = SinhVienService;
        this.validator = validator;
    }

    @Path("/getAll")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        try {
            return Response.status(Response.Status.OK).entity(this.SinhVienService.getSinhVien()).build();
        } catch(JDBCConnectionException e) {
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity("Connection failed").build();
        }
    }

    @Transactional
    @Path("/add")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(List<SinhVien> s) {
        String message = "";
        int error = 0;
        for(SinhVien i : s) {
            Set<ConstraintViolation<SinhVien>> validation = validator.validate(i);
            if (validation == null || validation.isEmpty()) {
                    message = message.concat(this.SinhVienService.addSinhVien(i)+"\n"+ i+"\n");
            } else {
                error = 1;
                String violations = validation.stream().map(violation -> violation.getMessage())
                        .collect(Collectors.joining("\n"));
                message = message.concat(violations+"\n"+i.toString()+"\n");
            }
        }
        if(error == 0)
            return Response.status(Response.Status.OK).entity(message).build();
        else
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
    }

    @Path("/update/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(Long id) {
        if(id > 0)
            return Response.status(Response.Status.OK).entity(SinhVienService.updateSinhVien(id)).build();
        else
            return Response.status(Response.Status.BAD_REQUEST).entity("Student id is greate than 0").build();
    }

    @Path("/delete/{id}")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(Long id) {
        if(id > 0)
            return Response.status(Response.Status.OK).entity(this.SinhVienService.deleteSinhVien(id)).build();
        else
            return Response.status(Response.Status.BAD_REQUEST).entity("Student id is greater than 0").build();
    }

}
