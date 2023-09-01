package com.example.Service;

import io.quarkus.hibernate.orm.PersistenceUnit;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class NghiepVuService {

    @Inject
    @PersistenceUnit("oracle")
    EntityManager entityManager;

    // Tính tổng số sinh viên với mỗi xếp loại
    @Transactional
    public List get1() {
        Query q = entityManager.createQuery("""
                                                    select xl.mota as xep_loai, count(sv.sinhvien_id) as so_luong
                                                    from SinhVien as sv right join sv.xeploai as xl
                                                    group by xl.mota \s
                                                  """
        );
        entityManager.close();
        return q.getResultList();
    }

    // Tìm các sinh viên có điểm lớn hơn điểm trung bình của các sinh viên có cùng tháng sinh
    @Transactional
    public List get2() {
        Query q = entityManager.createQuery("""
                                                      select  extract(month from sv.ngaysinh) as thang, 
                                                              sum((case when sv.xeploai=xl and xl.mota='Gioi' then 1 else 0 end)) as gioi,
                                                              sum((case when sv.xeploai=xl and xl.mota='Kha' then 1 else 0 end)) as kha,
                                                              sum((case when sv.xeploai=xl and xl.mota='Trung binh' then 1 else 0 end)) as trung_binh
                                                      from SinhVien as sv, XepLoai as xl
                                                      group by thang 
                                                      order by thang desc
                                                    """
        );
        entityManager.close();
        return q.getResultList();
    }
}
