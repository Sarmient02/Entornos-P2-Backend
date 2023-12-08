package com.entornos.EntornosP2Backend.service.impl;

import com.entornos.EntornosP2Backend.dto.FileData;
import com.entornos.EntornosP2Backend.exception.CustomException;
import com.entornos.EntornosP2Backend.model.File;
import com.entornos.EntornosP2Backend.repository.IFileRepository;
import com.entornos.EntornosP2Backend.service.interfaces.IFileService;
import com.entornos.EntornosP2Backend.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FileServiceImpl implements IFileService {

    private IFileRepository fileRepository;

    private JwtServiceImpl jwtService;

    public String uploadFile(MultipartFile file, String token) throws IOException {
        String jwt = token.substring(7);
        Long userId = Long.valueOf(jwtService.extractUserId(jwt));
        File fileToSave = fileRepository.save(File.builder()
                .name(file.getOriginalFilename())
                .fileType(file.getContentType())
                .content(FileUtils.compressFile(file.getBytes()))
                .size(file.getSize())
                .createdAt(LocalDateTime.now())
                .idUser(userId)
                .build());
        if(fileToSave != null) {
            return fileToSave.getId().toString();
        }
        return null;
    }

    public FileData downloadFile(Long id) {
        File tempFile = fileRepository.findById(id)
                .orElseThrow(() -> new CustomException("File not found"));
        byte[] content = FileUtils.decompressFile(tempFile.getContent());
        return FileData.builder()
                .fileType(tempFile.getFileType())
                .content(content)
                .build();
    }

    @Autowired
    public void setFileRepository(IFileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Autowired
    public void setJwtService(@Qualifier("jwtServiceImpl") JwtServiceImpl jwtService){
        this.jwtService = jwtService;
    }

}
