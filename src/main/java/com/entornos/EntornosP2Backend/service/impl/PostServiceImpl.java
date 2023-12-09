package com.entornos.EntornosP2Backend.service.impl;

import com.entornos.EntornosP2Backend.dto.PostRequestDTO;
import com.entornos.EntornosP2Backend.dto.PostResponseDTO;
import com.entornos.EntornosP2Backend.exception.CustomException;
import com.entornos.EntornosP2Backend.mapper.IPostMapper;
import com.entornos.EntornosP2Backend.model.File;
import com.entornos.EntornosP2Backend.model.Post;
import com.entornos.EntornosP2Backend.repository.IFileRepository;
import com.entornos.EntornosP2Backend.repository.IPostRepository;
import com.entornos.EntornosP2Backend.service.interfaces.IFileService;
import com.entornos.EntornosP2Backend.service.interfaces.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImpl implements IPostService {

    private JwtServiceImpl jwtService;

    private IFileService fileService;

    private IPostRepository postRepository;

    private IFileRepository fileRepository;


    @Transactional
    public String createPost(PostRequestDTO newPost, String token) {
        String jwt = token.substring(7);
        Long userId = Long.valueOf(jwtService.extractUserId(jwt));
        if (newPost.getTitle().isEmpty() || newPost.getDescription().isEmpty() || newPost.getSubjectId() == null) {
            throw new CustomException("Title, description and subject are required");
        }
        Post postToSave = Post.builder()
                .title(newPost.getTitle())
                .description(newPost.getDescription())
                .userId(userId)
                .subjectId(newPost.getSubjectId())
                .createdAt(LocalDateTime.now())
                .build();
        Post savedPost = postRepository.save(postToSave);
        return savedPost.getId().toString();
    }

    @Override
    public List<PostResponseDTO> getAllPosts(){
        System.out.println("hola");
        List<Post> allPosts = postRepository.findAll();
        System.out.println("si");
        return IPostMapper.INSTANCE.postsToPostResponseDTOList(allPosts);
    }

    @Autowired
    public void setJwtService(@Qualifier("jwtServiceImpl") JwtServiceImpl jwtService){
        this.jwtService = jwtService;
    }

    @Autowired
    public void setFileService(@Qualifier("fileServiceImpl") IFileService fileService){
        this.fileService = fileService;
    }

    @Autowired
    public void setPostRepository(IPostRepository postRepository){
        this.postRepository = postRepository;
    }

    @Autowired
    public void setFileRepository(IFileRepository fileRepository){
        this.fileRepository = fileRepository;
    }

}