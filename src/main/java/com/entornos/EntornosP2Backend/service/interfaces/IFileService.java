package com.entornos.EntornosP2Backend.service.interfaces;

import com.entornos.EntornosP2Backend.dto.FileData;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface IFileService {

    public String uploadFile(MultipartFile file, String token) throws IOException;

    public FileData downloadFile(Long id);

}
