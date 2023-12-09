package com.entornos.EntornosP2Backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostResponseDTO {

    Long id;

    String title;

    String description;

    SubjectResponseDTO subject;

    LocalDateTime createdAt;

    List<FileResponseDTO> files;

    UserPostData user;
}
