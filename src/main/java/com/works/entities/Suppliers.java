package com.works.entities;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Entity
@Data
@ApiModel(value = "Tedarikçi Model",description = "Tedarikçi (Ekleme,Güncelleme,Silme) için Kullanılır.")
public class Suppliers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sid", nullable = false)
    private Integer sid;


    @NotNull(message = "Supplier name NotNull")
    @NotEmpty(message = "Supplier name NotEmpty")
    @Column(unique = true)
    private String sname;

    @NotNull(message = "Supplier email NotNull")
    @NotEmpty(message = "Supplier email NotEmpty")
    @Column(unique = true)
    private String semail;

    @NotNull(message = "Supplier phone NotNull")
    @NotEmpty(message = "Supplier phone NotEmpty")
    @Column(unique = true)
    private String sphone;

    @NotNull(message = "Warehouse status NotNull")
    @NotEmpty(message = "Warehouse status NotEmpty")
    private String sstatus;

}
