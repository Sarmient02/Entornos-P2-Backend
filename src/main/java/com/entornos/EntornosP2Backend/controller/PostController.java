package com.entornos.EntornosP2Backend.controller;

import com.entornos.EntornosP2Backend.dto.PostRequestDTO;
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
            @RequestParam(value = "files", required = false, defaultValue = "") List<MultipartFile> files,
            @RequestHeader("Authorization") String token,
            @RequestParam(value = "request") String post
    ) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            PostRequestDTO postRequestDTO = objectMapper.readValue(post, PostRequestDTO.class);
            if(files.get(0).getContentType() == null){
                files = Collections.emptyList();
                System.out.println("nofiles");
            }
            String createdPost = postService.createPost(files, postRequestDTO, token);
            return ResponseEntity.ok().body(createdPost);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid JSON format");
        }
    }

    @Autowired
    public void setPostService(@Qualifier("postServiceImpl") IPostService postService){
        this.postService = postService;
    }

}
