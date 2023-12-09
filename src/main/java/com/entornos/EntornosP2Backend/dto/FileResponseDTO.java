package com.entornos.EntornosP2Backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
public class FileResponseDTO {

    Long id;

    String name;

    String hashName;

    LocalDateTime createdAt;

    String type;

    Long size;

}
