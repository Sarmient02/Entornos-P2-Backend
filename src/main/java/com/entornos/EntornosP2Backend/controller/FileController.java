package com.entornos.EntornosP2Backend.controller;

import com.entornos.EntornosP2Backend.dto.DownloadResponseDTO;
import com.entornos.EntornosP2Backend.dto.ResponseDTO;
import com.entornos.EntornosP2Backend.model.File;
import com.entornos.EntornosP2Backend.service.interfaces.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/file")
public class FileController {

    private IFileService fileService;

    /*@PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam(value = "file") MultipartFile file) {
        return new ResponseEntity<>(fileService.uploadFile(file), HttpStatus.OK);
    }*/

    @PostMapping("/upload/{idPost}")
    public ResponseEntity<ResponseDTO> uploadFile(
            @RequestParam(value = "file") MultipartFile file,
            @PathVariable Long idPost,
            @RequestHeader("Authorization") String token
    ) {
        return ResponseEntity.ok().body(fileService.uploadFile(file, token, idPost));
    }

    @GetMapping("/download/{fileHash}")
    public ResponseEntity<ByteArrayResource> downloadFile(
            @PathVariable String fileHash
    ) {
        DownloadResponseDTO file = fileService.downloadFile(fileHash);
        ByteArrayResource resource = new ByteArrayResource(file.getData());
        return ResponseEntity
                .ok()
                .contentLength(file.getData().length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + file.getName() + "\"")
                .body(resource);
    }

    @GetMapping("/preview/{fileHash}")
    public ResponseEntity<ByteArrayResource> previewFile(
            @PathVariable String fileHash
    ) {
        DownloadResponseDTO file = fileService.downloadFile(fileHash);
        ByteArrayResource resource = new ByteArrayResource(file.getData());
        return ResponseEntity
                .ok()
                .contentLength(file.getData().length)
                .header("Content-type", file.getType())
                .header("Content-disposition", "inline; filename=\"" + file.getName() + "\"")
                .body(resource);
    }

    @DeleteMapping("/delete/{fileName}")
    public ResponseEntity<String> deleteFile(@PathVariable String fileName) {
        return new ResponseEntity<>(fileService.deleteFile(fileName), HttpStatus.OK);
    }

    @Autowired
    public void setFileService(@Qualifier("fileServiceImpl") IFileService fileService){
        this.fileService = fileService;
    }

}
