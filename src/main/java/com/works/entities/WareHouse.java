package com.works.entities;


import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Entity
@ApiModel(value = "Depo Model",description = "Depo (Ekleme,Güncelleme,Silme) için Kullanılır.")
public class WareHouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer wid;


    @NotNull(message = "Warehouse name NotNull")
    @NotEmpty(message = "Warehouse name NotEmpty")
    @Column(unique = true)
    private String wname;
}
