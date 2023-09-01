package com.example;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.panache.common.Parameters;
import jakarta.persistence.*;

import java.sql.Date;
import java.util.List;

@Table(name="student1")
@Entity
//@org.hibernate.annotations.NamedNativeQuery(name="xep_loai", query = "update student1 set xep_loai = case when grade < :trung_binh then 3 when grade < :kha then 2 when grade < :gioi then 1 end where 1=1")
@NamedQueries({
        @NamedQuery(name = "Student.updateXepLoai", query = "update Student " +
                "                                            set xl = case when grade < :trung_binh then 3 " +
                "                                                          when grade < :kha then 2 " +
                "                                                          when grade <= :gioi then 1 end "),
        // chọn ra những sinh viên có điểm trung bình lớn hơn điểm trung bình của tất cả các sinh viên có cùng tháng sinh
        @NamedQuery(name = "Student.query1", query = "select average_each_month.month as month, s.name, s.grade \n" +
                "                                     from Student s,\n" +
                "                                          (\n" +
                "                                               select distinct avg(grade) over (partition by to_char(dob, 'MM')) as average, to_char(dob, 'MM') as month\n" +
                "                                               from Student  \n" +
                "                                          ) average_each_month\n" +
                "                                     where grade > average_each_month.average and to_char(dob, 'MM') = average_each_month.month\n" +
                "                                     order by month asc"),
        @NamedQuery(name = "Student.query2", query = "select xl.mo_ta, count(*) as so_luong\n" +
                                                    "from Student s \n" +
                                                    "right join xep_loai xl\n" +
                                                    "on xl.id = s.xl\n" +
                                                    "group by xl.id, xl.mo_ta")
})
public class Student extends PanacheEntityBase {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="student1_seq")
    @SequenceGenerator(name="student1_seq", sequenceName = "student1_seq")
    public Long id;

    @Column(name="name")
    public String name;

    @Column(name="dob")
    public Date dob;

    @Column(name="grade")
    public Float grade;

    @Column(name="xep_loai")
    public int xl;

    public static long updateXepLoai(List<Double> diem) {
        return Student.update("#Student.updateXepLoai", Parameters.with("trung_binh", diem.get(0)).and("kha", diem.get(1)).and("gioi", diem.get(2)));
    }

    // chọn ra những sinh viên có điểm trung bình lớn hơn điểm trung bình của tất cả các sinh viên có cùng tháng sinh
    public static List<Student> query1() {
        return Student.list("#Student.query1");
    }

    public static List<Student> query2() {
        return Student.list("#Student.query2");
    }

    public String toString() {
        return this.id + " " + this.name + " " + this.dob + " " + this.grade;
    }
}
