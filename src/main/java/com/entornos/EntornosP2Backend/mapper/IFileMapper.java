package com.entornos.EntornosP2Backend.mapper;

import com.entornos.EntornosP2Backend.dto.FileResponseDTO;
import com.entornos.EntornosP2Backend.model.File;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface IFileMapper {

    IFileMapper INSTANCE = Mappers.getMapper(IFileMapper.class);

    @Mapping(source = "fileType", target = "type")
    @Mapping(source = "size", target = "size")
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "hashName", target = "hashName")
    @Mapping(source = "createdAt", target = "createdAt")
    FileResponseDTO fileToFileResponseDTO(File file);

    List<FileResponseDTO> filesToFileResponseDTOList(List<File> files);

}
