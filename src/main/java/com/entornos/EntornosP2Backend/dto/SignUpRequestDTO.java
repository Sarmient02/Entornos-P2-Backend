package com.entornos.EntornosP2Backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequestDTO {

    String username;

    String fullName;

    String studentCode;

    String email;

    String password;

}
