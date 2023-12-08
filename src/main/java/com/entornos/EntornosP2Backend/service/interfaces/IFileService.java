package com.entornos.EntornosP2Backend.service.interfaces;

import com.entornos.EntornosP2Backend.dto.FileData;
import com.entornos.EntornosP2Backend.model.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface IFileService {

    public File uploadFile(MultipartFile file, Long userId);

    public byte[] downloadFile(String fileName);

    public FileData previewFile(String fileName);

    public String deleteFile(String fileName);

}
