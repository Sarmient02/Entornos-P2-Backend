package com.entornos.EntornosP2Backend.service.interfaces;

import com.entornos.EntornosP2Backend.dto.EditPostRequestDTO;
import com.entornos.EntornosP2Backend.dto.PostRequestDTO;
import com.entornos.EntornosP2Backend.dto.PostResponseDTO;
import com.entornos.EntornosP2Backend.dto.ResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface IPostService {

    String createPost(PostRequestDTO newPost, String token);

    List<PostResponseDTO> getAllPosts();

    PostResponseDTO getPostById(Long id);

    ResponseDTO editPost(EditPostRequestDTO editedPost, String token);

    ResponseDTO deletePost(Long id, String token);

}
