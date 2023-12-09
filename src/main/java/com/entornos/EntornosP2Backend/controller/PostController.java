package com.entornos.EntornosP2Backend.controller;

import com.entornos.EntornosP2Backend.dto.EditPostRequestDTO;
import com.entornos.EntornosP2Backend.dto.PostRequestDTO;
import com.entornos.EntornosP2Backend.dto.PostResponseDTO;
import com.entornos.EntornosP2Backend.dto.ResponseDTO;
import com.entornos.EntornosP2Backend.service.interfaces.IPostService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {

    private IPostService postService;

    @PostMapping("/new")
    public ResponseEntity<String> createPost(
            @RequestHeader("Authorization") String token,
            @RequestBody PostRequestDTO post
    ) {
        String createdPost = postService.createPost(post, token);
        return ResponseEntity.ok().body(createdPost);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PostResponseDTO>> getPosts(
    ) {
        List<PostResponseDTO> allPosts = postService.getAllPosts();
        return ResponseEntity.ok().body(allPosts);
    }

    @PutMapping()
    public ResponseEntity<ResponseDTO> editPost(
            @RequestHeader("Authorization") String token,
            @RequestBody EditPostRequestDTO post
    ) {
        ResponseDTO createdPost = postService.editPost(post, token);
        return ResponseEntity.ok().body(createdPost);
    }

    @Autowired
    public void setPostService(@Qualifier("postServiceImpl") IPostService postService){
        this.postService = postService;
    }

}
