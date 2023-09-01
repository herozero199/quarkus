package com.example;

import com.example.Entity.SinhVien;
import com.example.Service.SinhVienService;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@QuarkusTest
public class SinhVienServiceTest {

    long id = 0;
    @Inject
    SinhVienService sinhVienService;
    List<SinhVien> sinhviens = new ArrayList<>();

    @Test
    public void testAddSinhVien_ConstraintViolation() {
        id++;

        sinhVienService.addSinhVien(new SinhVien(this.id, "Dat", Date.valueOf("2002-09-19"), 5F));
//        Assertions.assertEquals(results, "Success");

        String results = sinhVienService.addSinhVien(new SinhVien(this.id, "Dat", Date.valueOf("2002-09-19"), 5F));
        Assertions.assertEquals(results, "Student id must be unique");
//        Assertions.assertThrows(SQLIntegrityConstraintViolationException.class,
//                () -> {sinhVienService.addSinhVien(new SinhVien(this.id, "Dat", Date.valueOf("2002-09-19"), 5F)); });
    }
}
