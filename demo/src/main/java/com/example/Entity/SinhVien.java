package com.example.Entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.sql.Date;

@Entity
@Table(name="sinhvien")
public class SinhVien extends PanacheEntityBase {

    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_seq")
//    @SequenceGenerator(name = "student_seq", sequenceName = "student_seq")
    @NotNull(message = "Student id cannot be blank")
    @Column(name="sinhvien_id")
    public Long sinhvien_id;

    @NotBlank(message = "Name cannot be blank")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z ]*$", message = "Name can contain characters only")
    @Column(name="ten")
    public String ten;

    @Column(name="ngaysinh", nullable = false)
    @NotNull(message = "Date of birth cannot be blank")
    public Date ngaysinh;

    @Column(name="diem", nullable = false)
    @NotNull(message = "Grade cannot be blank")
    @Min(value = 0, message = "Grade is greater than 0")
    @Max(value = 10, message = "Grade is smaller or equal to 10")
    public Float diem;

    @ManyToOne
    @JoinColumn(name="xeploai_id", nullable = false)
    public XepLoai xeploai;

    public SinhVien(Long sinhvien_id, String ten, Date ngaysinh, Float diem) {
        this.sinhvien_id = sinhvien_id;
        this.ten = ten;
        this.ngaysinh = ngaysinh;
        this.diem = diem;
    }

    public SinhVien() {}

    @Override
    public String toString() {
        return "SinhVien{" +
                "sinhvien_id=" + sinhvien_id +
                ", ten='" + ten + '\'' +
                ", ngaysinh=" + ngaysinh +
                ", diem=" + diem +
                ", xeploai=" + xeploai +
                '}';
    }

}