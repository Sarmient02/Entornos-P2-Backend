package com.entornos.EntornosP2Backend.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CareerDTO implements Serializable {

    private static final long serialVersionUID = -9141170951470537112L;

    private Long id;
    private String name;
    private String careerCode;
}
