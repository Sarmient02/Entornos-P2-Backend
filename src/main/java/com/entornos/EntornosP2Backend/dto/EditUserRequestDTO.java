package com.entornos.EntornosP2Backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditUserRequestDTO {

    Long id;

    String username;

    String full_name;

    String student_code;

    String email;

    String password;

    ArrayList<String> roles;

}