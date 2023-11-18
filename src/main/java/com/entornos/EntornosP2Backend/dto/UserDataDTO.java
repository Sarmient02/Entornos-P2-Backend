package com.entornos.EntornosP2Backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserDataDTO {

    private Long id;

    private String username;

    private String full_name;

    private String student_code;

    private String email;

    private List<String> roles;

}