package com.example;

import io.quarkus.panache.common.Parameters;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.hibernate.annotations.NamedNativeQuery;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Path("/student")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StudentRepository {

    @Path("/list")
    @GET
    public List<Student> list() {
        return Student.list("grade > ?1", 5.0);
    }

    @GET
    @Path("/{id}")
    public Student get(Long id) {
        return Student.findById(id);
    }

    @Path("/insert")
    @POST
    @Transactional
    public Response create(Student s) {
        s.persist();
        return Response.created(URI.create("/student/" + s.id)).build();
    }

    @Path("/insert_all")
    @POST
    @Transactional
    public List<Student> create_all(List<Student> s) {
        ArrayList<Student> a = new ArrayList<>();
        for(Student i : s) {
            i.persist();
            a.add(this.get(i.id));
        }
        return a.stream().toList();
    }

    @PUT
    @Path("/update_xep_loai")
    @Transactional
    public List<Double> update(List<Double> diem) {
        Student.updateXepLoai(diem);
        return diem;
    }

    @GET
    @Path("/query1")
    @Transactional
    public List<Student> query1() {
        return Student.query1();
    }

    @GET
    @Path("/query2")
    @Transactional
    public List<Student> query2() {
        return Student.query2();
    }
}
