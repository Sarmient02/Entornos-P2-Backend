package com.entornos.EntornosP2Backend.service.impl;

import com.entornos.EntornosP2Backend.dto.EditPostRequestDTO;
import com.entornos.EntornosP2Backend.dto.PostRequestDTO;
import com.entornos.EntornosP2Backend.dto.PostResponseDTO;
import com.entornos.EntornosP2Backend.dto.ResponseDTO;
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
import java.util.Objects;

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

    @Transactional
    public ResponseDTO editPost(EditPostRequestDTO editedPost, String token) {
        String jwt = token.substring(7);
        Long userId = Long.valueOf(jwtService.extractUserId(jwt));
        if (editedPost.getTitle().isEmpty() || editedPost.getDescription().isEmpty() || editedPost.getSubjectId() == null) {
            throw new CustomException("Title, description and subject are required");
        }
        Post oldPost = postRepository.findById(editedPost.getId()).orElseThrow(
                () -> new CustomException("Post not found")
        );
        if (!Objects.equals(oldPost.getUserId(), userId)) {
            throw new CustomException("You can't edit this post");
        }
        List<File> files = new ArrayList<>();
        List<File> oldFiles = oldPost.getFile();
        if (editedPost.getFiles() != null) {
            for (File file : oldFiles) {
                if (!editedPost.getFiles().contains(file.getHashName())) {
                    fileService.deleteFile(file.getFileUrl());
                }
            }
            for (String file : editedPost.getFiles()) {
                files.add(fileRepository.findByHashName(file).orElseThrow(
                        () -> new CustomException("File not found")
                ));
            }
        }
        Post postToSave = Post.builder()
                .id(editedPost.getId())
                .title(editedPost.getTitle())
                .description(editedPost.getDescription())
                .userId(userId)
                .subjectId(editedPost.getSubjectId())
                .createdAt(LocalDateTime.now())
                .file(files)
                .build();
        Post savedPost = postRepository.save(postToSave);
        ResponseDTO response = new ResponseDTO();
        response.setMessage("Post editado correctamente");
        return response;
    }

    @Override
    public List<PostResponseDTO> getAllPosts(){
        List<Post> allPosts = postRepository.findAll();
        return IPostMapper.INSTANCE.postsToPostResponseDTOList(allPosts);
    }

    @Override
    public PostResponseDTO getPostById(Long id){
        Post post = postRepository.findById(id).orElseThrow(
                () -> new CustomException("Post not found")
        );
        return IPostMapper.INSTANCE.postToPostResponseDTO(post);
    }

    @Override
    @Transactional
    public ResponseDTO deletePost(Long id, String token){
        String jwt = token.substring(7);
        Long userId = Long.valueOf(jwtService.extractUserId(jwt));
        Post postToDelete = postRepository.findById(id).orElseThrow(
                () -> new CustomException("Post not found")
        );
        if (!Objects.equals(postToDelete.getUserId(), userId)) {
            throw new CustomException("You can't delete this post");
        }
        List<File> files = postToDelete.getFile();
        if (files != null || !files.isEmpty()){
            for (File file : files) {
                fileService.deleteFile(file.getFileUrl());
            }
        }
        postRepository.deleteById(id);
        ResponseDTO response = new ResponseDTO();
        response.setMessage("Post eliminado correctamente");
        return response;
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
