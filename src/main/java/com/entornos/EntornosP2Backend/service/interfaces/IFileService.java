package com.entornos.EntornosP2Backend.service.interfaces;

import com.entornos.EntornosP2Backend.dto.DownloadResponseDTO;
import com.entornos.EntornosP2Backend.dto.FileData;
import com.entornos.EntornosP2Backend.dto.ResponseDTO;
import com.entornos.EntornosP2Backend.model.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface IFileService {

    public ResponseDTO uploadFile(MultipartFile file, String token, Long postId);

    public DownloadResponseDTO downloadFile(String fileName);

    public FileData previewFile(String fileName);

    public String deleteFile(String fileName);

}
