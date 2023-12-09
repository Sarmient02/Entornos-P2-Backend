package com.entornos.EntornosP2Backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditPostRequestDTO {

    Long id;

    String title;

    String description;

    Long subjectId;

    List<String> files;

}
