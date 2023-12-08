package com.entornos.EntornosP2Backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class FileData {
    private byte[] content;
    private String fileType;
}
