package com.entornos.EntornosP2Backend.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class TagDTO implements Serializable {

    private static final long serialVersionUID = 3034013178350484357L;

    private Long id;
    private String name;
}
