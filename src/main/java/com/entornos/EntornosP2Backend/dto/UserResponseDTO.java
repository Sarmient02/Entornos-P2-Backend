package com.entornos.EntornosP2Backend.dto;

import lombok.Data;

@Data
public class UserResponseDTO {


    private Integer id;

    private String username;

    private String email;

    private String role;

}