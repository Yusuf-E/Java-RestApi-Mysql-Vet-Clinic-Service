package com.works.entities;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Entity
@ApiModel(value = "Ürün Model",description = " Ürün (Ekleme,Güncelleme,Silme) için Kullanılır.\n" +
        "productunit = 1 - Adet ,productunit = 2 - Kutu, productunit = 3 - Şişe, productunit = 5 - Diğer  \n" +
        "producttype = 1 - Çiftlik Aşı ,producttype = 2 - Damla, producttype = 3 - Enjeksiyon,producttype = 4 - Hap, producttype = 5 - Diğer \n" +
        "producttax = 0 - Dahil,producttax = 1 - %1 ,producttax = 2 - %8, producttax = 3 - %18")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "proid", nullable = false)
    private Integer proid;

    @Column(unique = true)
    private String productname;

    private Integer productunit;

    private Integer producttype;

    @NotNull(message = "Product productbarcode NotNull")
    @NotEmpty(message = "Product productbarcode NotEmpty")
    @Column(unique = true)
    private String productbarcode;

    @NotNull(message = "Product productcode NotNull")
    @NotEmpty(message = "Product productcode NotEmpty")
    @Column(unique = true)
    private String productcode;

    private Integer producttax;

    private Integer buyprice;

    private Integer sellprice;

    private Integer criticalquantity;


    @NotNull(message = "Product productstatus NotNull")
    @NotEmpty(message = "Product productstatus NotEmpty")
    private String productstatus;

    @NotNull(message = "Product pspki NotNull")
    @NotEmpty(message = "Product pspki NotEmpty")
    private String pspki;

    @NotNull(message = "Product pbpki NotNull")
    @NotEmpty(message = "Product pbpki NotEmpty")
    private String pbpki;

    @OneToOne
    private Suppliers productsuppliers;

    @OneToOne
    private Category productcategory;

    public Integer getProid() {
        return proid;
    }

    public void setProid(Integer proid) {
        this.proid = proid;
    }

}
