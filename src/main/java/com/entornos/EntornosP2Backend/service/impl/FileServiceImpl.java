package com.entornos.EntornosP2Backend.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.entornos.EntornosP2Backend.dto.FileData;
import com.entornos.EntornosP2Backend.model.File;
import com.entornos.EntornosP2Backend.repository.IFileRepository;
import com.entornos.EntornosP2Backend.service.interfaces.IFileService;
import com.google.common.hash.Hashing;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Service
@Slf4j
public class FileServiceImpl implements IFileService {

    @Value("${application.bucket.name}")
    private String bucketName;

    @Autowired
    private AmazonS3 s3Client;

    private IFileRepository fileRepository;

    @Override
    public File uploadFile(MultipartFile file, Long userId) {
        java.io.File fileObj = convertMultiPartFileToFile(file);
        String fileName = System.currentTimeMillis()+"_"+file.getOriginalFilename();
        String hashName = generateHash(fileName);
        s3Client.putObject(new PutObjectRequest(
                bucketName,
                fileName,
                fileObj
        ));
        fileObj.delete();
        File fileToSave = fileRepository.save(File.builder()
                .name(file.getOriginalFilename())
                .fileType(file.getContentType())
                .fileUrl(fileName)
                .size(file.getSize())
                .createdAt(LocalDateTime.now())
                .idUser(userId)
                .hashName(hashName)
                .build());
        return fileToSave;
    }

    @Override
    public byte[] downloadFile(String fileName) {
        S3Object s3Object = s3Client.getObject(bucketName, fileName);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        try {
            byte[] content = IOUtils.toByteArray(inputStream);
            return content;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public FileData previewFile(String fileName){
        S3Object s3Object = s3Client.getObject(bucketName, fileName);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        try {
            byte[] content = IOUtils.toByteArray(inputStream);
            //return content;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String deleteFile(String fileName) {
        s3Client.deleteObject(bucketName, fileName);
        return fileName + " removed ...";
    }

    private java.io.File convertMultiPartFileToFile(MultipartFile file) {
        java.io.File convertedFile = new java.io.File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            log.error("Error converting multipartFile to file", e);
        }
        return convertedFile;
    }

    public static String generateHash(String originalString) {
        return Hashing.sha256()
                .hashString(originalString, StandardCharsets.UTF_8)
                .toString();
    }

    @Autowired
    public void setFileRepository(IFileRepository fileRepository){
        this.fileRepository = fileRepository;
    }

}
