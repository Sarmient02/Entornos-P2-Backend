package com.entornos.EntornosP2Backend.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SubjectDTO implements Serializable {

    private static final long serialVersionUID = -5330846992801808224L;
    private Long id;
    private String name;
    private Long careerId;
}
