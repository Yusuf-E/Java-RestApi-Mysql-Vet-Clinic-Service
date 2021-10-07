package com.works.entities;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Data
@ApiModel(value = "Kategori Model",description = "Kategori (Ekleme,Güncelleme,Silme) için Kullanılır.")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "caid", nullable = false)
    private Integer caid;

    @NotNull(message = "Category name NotNull")
    @NotEmpty(message = "Category name NotEmpty")
    @Column(unique = true)
    private String categoryname;


}
