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

    String fullName;

    String studentCode;

    String email;

    String password;

    ArrayList<String> roles;

}