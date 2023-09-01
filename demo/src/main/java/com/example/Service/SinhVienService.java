package com.example.Service;

import com.example.Entity.SinhVien;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.JDBCConnectionException;
import org.hibernate.validator.engine.HibernateConstraintViolation;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@ApplicationScoped
public class SinhVienService {

    public SinhVienService () { }

    public List<SinhVien> getSinhVien () throws JDBCConnectionException  {

        return SinhVien.listAll();
    }
    @Transactional
    public String addSinhVien(SinhVien s) {
        try {
            s.persistAndFlush();
        } catch(PersistenceException e) {
            if(e.getClass().equals((ConstraintViolationException.class)))
                return "Student id must be unique";
            return e.getMessage()+"\n"+e.getClass();
        }
        return "Success";
    }

    @Transactional
    public String updateSinhVien(Long id) {
        String query = """
                 xeploai.xeploai_id = (
                             case when diem < 5 then 3
                                  when diem < 8 then 2
                                  else 1 end
                            )
                 where sinhvien_id = ?1
            """;
        try {
            int updated = SinhVien.update(query, id);
            if (updated == 0)
                return "No students with id " + id + " exist!";
        } catch(HibernateException e) {
            return e.getMessage();
        }
        return "Success";
    }



    @Transactional
    public String deleteSinhVien(Long id) {
        String query = """
                where id = ?1
        """;
        try {
            long deleted = SinhVien.delete(query, id);
            if (deleted == 0)
                return "No students with id " + id + " exist!";
        } catch(HibernateException e) {
            return e.getMessage();
        }
        return "Success";
    }
}
