package com.example.Entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

@Entity
@Table(name="xeploai")
public class XepLoai extends PanacheEntityBase {

    @Id
    @Column(name="xeploai_id")
    public int xeploai_id;

    @Column(name="mota")
    public String mota;
}
