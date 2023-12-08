package com.entornos.EntornosP2Backend.controller;

import com.entornos.EntornosP2Backend.dto.FileData;
import com.entornos.EntornosP2Backend.service.interfaces.IFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {

    private IFileService fileService;

    @PreAuthorize("hasAuthority('admin') || hasAuthority('user') || hasAuthority('moderator')")
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(
            @RequestHeader("Authorization") String token,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        String uploadImage = fileService.uploadFile(file, token);
        return ResponseEntity.status(201).body(uploadImage);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<?> downloadFile(
            @PathVariable Long id
    ) {
        FileData downloadFile = fileService.downloadFile(id);
        return ResponseEntity.ok().contentType(MediaType.valueOf(downloadFile.getFileType())).body(downloadFile.getContent());
    }

    @Autowired
    public void setFileService(@Qualifier("fileServiceImpl") IFileService fileService) {
        this.fileService = fileService;
    }

}
