package com.works.property;

import lombok.Data;

@Data
public class PasswordChangeProperties {

    private int id;

    private String password;
    private String repassword;

}
