package com.works.entities;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Entity
@ApiModel(value = "Fatura Model",description = "1->tedarikçi- 0->Müşteri dikkate alınarak add işlemi yapılcak. \nFatura İşlemleri İçin Kullanılır\npaymenyype :1 -> Nakit\n paymenttype :2 -> BankaKartı \n paymenttype:3 -> Banka Havalesi\n opestatus : true -> Alış\n opestatus : false -> Satış")
public class Bill {
    @Id
    private String bill_id;

    @NotNull(message = "Bill cus_id NotNull")
    private Integer cus_id;

    @NotNull(message = "Bill amount NotNull")
    private Integer amount;

    @NotNull(message = "Bill opestatus NotNull")
    private Boolean opestatus;

    @NotNull(message = "Bill paymenttype NotNull")
    private Integer paymenttype;


    private Date date = new Date();

    @NotNull(message = "Bill note NotNull")
    @NotEmpty(message = "Bill note NotEmpty")
    private String note ;
}

