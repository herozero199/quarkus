package com.example;

import com.example.Controller.SinhVienController;
import com.example.Entity.SinhVien;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class SinhVienControllerTest {
    @Inject
    SinhVienController SinhVienController;
    List<SinhVien> sinhviens = new ArrayList<>();

    @Test
    public void testGetAllSinhVien() {
        given().when().get("sinhvien/getAll")
                .then().statusCode(200);
    }

    @Test
    public void testAddSinhVien_Success() {
        sinhviens.add(new SinhVien(4L, "dat", Date.valueOf("2002-09-19"), 3.5F));
        Response r = SinhVienController.add(sinhviens);
        Assertions.assertEquals(r.getStatus(), 200);
        Assertions.assertEquals("Success\n"+sinhviens.get(0)+"\n", r.getEntity());
    }

    @Test
    public void testAddSinhVien_InvalidName () {
        sinhviens.add(new SinhVien(1L, "123 @#", Date.valueOf("2002-09-19"), 3.5F));
        Response r = SinhVienController.add(sinhviens);
        Assertions.assertEquals(r.getStatus(), 400);
        Assertions.assertEquals("Name can contain characters only\n"+sinhviens.get(0).toString()+"\n", r.getEntity());
    }

    @Test
    public void testAddSinhVien_InvalidGrade () {
        sinhviens.add(new SinhVien(1L, "dat", Date.valueOf("2002-09-19"), -1F));
        sinhviens.add(new SinhVien(1L, "dat", Date.valueOf("2002-09-19"), 10.5F));

        Response r = SinhVienController.add(sinhviens);
        Assertions.assertEquals(r.getStatus(), 400);
        Assertions.assertEquals("Grade is greater than 0\n"+sinhviens.get(0)+"\n"+
                                         "Grade is smaller or equal to 10\n"+sinhviens.get(1)+"\n", r.getEntity());
    }

    @Test
    public void testAddSinhVien_Null () {
        sinhviens.add(new SinhVien(null, "dat", Date.valueOf("2002-01-24"), 3F));
        sinhviens.add(new SinhVien(1L, null, Date.valueOf("2002-09-19"), 3.5F));
        sinhviens.add(new SinhVien(1L, "dat", null, 3.5F));
        sinhviens.add(new SinhVien(1L, "dat", Date.valueOf("2002-09-19"), null));

        Response r = SinhVienController.add(sinhviens);
        Assertions.assertEquals(r.getStatus(), 400);
        Assertions.assertEquals("Student id cannot be blank\n"+sinhviens.get(0)+"\n"+
                                         "Name cannot be blank\n"+sinhviens.get(1)+"\n"+
                                         "Date of birth cannot be blank\n"+sinhviens.get(2)+"\n"+
                                         "Grade cannot be blank\n"+sinhviens.get(3)+"\n", r.getEntity());
    }

    @Test
    public void testUpdateSinhVien_Success() {
        given().when().put("sinhvien/update/1")
                .then().statusCode(200).equals("Success");
    }

    @Test
    public void testUpdateSinhVien_NotFound() {
        given().when().put("sinhvien/update/-1")
                .then().statusCode(400).equals("Student id is greate than 0");

        given().when().put("sinhvien/update/1.5")
                .then().statusCode(404);

        given().when().put("sinhvien/update/dat")
                .then().statusCode(404);
    }

    @Test
    public void testDeleteSinhVien_Success() {
        given().when().delete("sinhvien/delete/1")
                .then().statusCode(200);
    }

    @Test
    public void testDeleteSinhVien_NotFound() {
        given().when().delete("sinhvien/delete/-1")
                .then().statusCode(400).equals("Student id is greate than 0");

        given().when().delete("sinhvien/delete/1.5")
                .then().statusCode(404);

        given().when().delete("sinhvien/delte/dat")
                .then().statusCode(404);
    }
}
