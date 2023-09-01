package com.example;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "xep_loai")
public class xep_loai extends PanacheEntityBase {
    @Id
    @Column(name = "id")
    public Long id;

    @Column(name = "mo_ta")
    public String mo_ta;
}
