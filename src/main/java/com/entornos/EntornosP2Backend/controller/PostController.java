package com.entornos.EntornosP2Backend.controller;

import com.entornos.EntornosP2Backend.dto.*;
import com.entornos.EntornosP2Backend.model.Career;
import com.entornos.EntornosP2Backend.model.Comment;
import com.entornos.EntornosP2Backend.model.Subject;
import com.entornos.EntornosP2Backend.model.Tag;
import com.entornos.EntornosP2Backend.service.impl.JwtServiceImpl;
import com.entornos.EntornosP2Backend.service.interfaces.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {

    private IPostService postService;
    private ITagService tagService;
    private ICareerService careerService;
    private ICommentService commentService;
    private ISubjectService subjectService;
    private JwtServiceImpl jwtService;

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

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDTO> getPostById(
            @PathVariable Long id
    ) {
        PostResponseDTO post = postService.getPostById(id);
        return ResponseEntity.ok().body(post);
    }

    @PutMapping()
    public ResponseEntity<ResponseDTO> editPost(
            @RequestHeader("Authorization") String token,
            @RequestBody EditPostRequestDTO post
    ) {
        ResponseDTO createdPost = postService.editPost(post, token);
        return ResponseEntity.ok().body(createdPost);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> deletePost(
            @RequestHeader("Authorization") String token,
            @PathVariable Long id
    ) {
        ResponseDTO createdPost = postService.deletePost(id, token);
        return ResponseEntity.ok().body(createdPost);
    }

    @GetMapping("/tags")
    public ResponseEntity<List<Tag>> getAllTags() {
        var allTags = this.tagService.getAll();
        if (allTags.isEmpty()) return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Collections.emptyList());
        return ResponseEntity.ok().body(allTags);
    }

    @PostMapping("/tags-new")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Tag> newTag(@RequestBody TagDTO tag) {
        var newTag = this.tagService.newTag(tag);
        if (newTag == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        return ResponseEntity.ok().body(newTag);
    }

    @PostMapping("/tags-edit")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Tag> editTag(@RequestBody TagDTO tag) {
        var editTag = this.tagService.editTag(tag);
        if (editTag == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        return ResponseEntity.ok().body(editTag);
    }

    @DeleteMapping("/tags-delete")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Boolean> deleteTag(@RequestParam Long tagId) {
        var deleteTag = this.tagService.deleteTag(tagId);
        if (!deleteTag) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        return ResponseEntity.ok().body(true);
    }

    @GetMapping("/careers")
    @PreAuthorize("hasAuthority('admin') || hasAuthority('user') || hasAuthority('moderator')")
    public ResponseEntity<List<Career>> getCareers() {
        var careers = this.careerService.getAll();
        if (careers.isEmpty()) return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Collections.emptyList());
        return ResponseEntity.ok().body(careers);
    }

    @GetMapping("/careers/{id}")
    @PreAuthorize("hasAuthority('admin') || hasAuthority('user') || hasAuthority('moderator')")
    public ResponseEntity<Career> getCareerById(
            @PathVariable Long id
    ) {
        var career = this.careerService.getCareerById(id);
        if (career == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        return ResponseEntity.ok().body(career);
    }

    @PostMapping("/careers-new")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Career> newCareer(@RequestBody CareerDTO career) {
        var newCareer = this.careerService.newCareer(career);
        if (newCareer == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        return ResponseEntity.ok().body(newCareer);
    }

    @PostMapping("/careers-edit")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Career> editCareer(@RequestBody CareerDTO career) {
        var editCareer = this.careerService.updateCareer(career);
        if (editCareer == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        return ResponseEntity.ok().body(editCareer);
    }

    @DeleteMapping("/careers-delete")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Boolean> deleteCareer(@RequestParam Long careerId) {
        var deleteCareer = this.careerService.deleteCareer(careerId);
        if (!deleteCareer) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        return ResponseEntity.ok().body(true);
    }

    @GetMapping("/comments")
    @PreAuthorize("hasAuthority('admin') || hasAuthority('user')")
    public ResponseEntity<List<CommentResponseDTO>> getComments(
            @RequestParam Long postId
    ) {
        List<CommentResponseDTO> allComments = commentService.getAll(postId);
        return ResponseEntity.ok().body(allComments);
    }

    @PostMapping("/comments-new")
    @PreAuthorize("hasAuthority('admin') || hasAuthority('user')")
    public ResponseEntity<CommentResponseDTO> newComment(
            @RequestBody CommentDTO comment, @RequestHeader("Authorization") String token
    ) {
        var user = Long.valueOf(this.jwtService.extractUserId(token.substring(7)));
        if (!user.equals(comment.getIdUser())) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        CommentResponseDTO newComment = commentService.newComment(comment);
        return ResponseEntity.ok().body(newComment);
    }

    @PostMapping("/comments-edit")
    @PreAuthorize("hasAuthority('admin') || hasAuthority('user')")
    public ResponseEntity<Comment> editComment(
            @RequestBody CommentDTO comment, @RequestHeader("Authorization") String token
    ) {
        var user = Long.valueOf(this.jwtService.extractUserId(token.substring(7)));
        if (!user.equals(comment.getIdUser())) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        Comment editComment = commentService.editComment(comment);
        return ResponseEntity.ok().body(editComment);
    }

    @DeleteMapping("/comments-delete")
    @PreAuthorize("hasAuthority('admin') || hasAuthority('user')")
    public ResponseEntity<Boolean> deleteComment(
            @RequestParam Long commentId, @RequestHeader("Authorization") String token
    ) {
        var user = Long.valueOf(this.jwtService.extractUserId(token.substring(7)));
        CommentResponseDTO comment = commentService.getCommentById(commentId);
        if (!user.equals(comment.getUser().getId())) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        Boolean deleteComment = commentService.deleteComment(commentId);
        return ResponseEntity.ok().body(deleteComment);
    }

    @GetMapping("/comments/{id}")
    @PreAuthorize("hasAuthority('admin') || hasAuthority('user')")
    public ResponseEntity<CommentResponseDTO> getCommentById(
            @PathVariable Long id
    ) {
        CommentResponseDTO comment = commentService.getCommentById(id);
        return ResponseEntity.ok().body(comment);
    }

    @GetMapping("/subjects")
    @PreAuthorize("hasAuthority('admin') || hasAuthority('user')")
    public ResponseEntity<List<Subject>> getSubjects(
            @RequestParam Long careerId
    ) {
        List<Subject> allSubjects = subjectService.getAllByCareer(careerId);
        return ResponseEntity.ok().body(allSubjects);
    }

    @GetMapping("/subjects/all")
    @PreAuthorize("hasAuthority('admin') || hasAuthority('user')")
    public ResponseEntity<List<Subject>> getSubjects(
    ) {
        List<Subject> allSubjects = subjectService.getAllSubjects();
        return ResponseEntity.ok().body(allSubjects);
    }

    @GetMapping("/subjects/{id}")
    @PreAuthorize("hasAuthority('admin') || hasAuthority('user')")
    public ResponseEntity<Subject> getSubjectById(
            @PathVariable Long id
    ) {
        Subject subject = subjectService.getSubjectById(id);
        return ResponseEntity.ok().body(subject);
    }

    @PostMapping("/subjects-new")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Subject> newSubject(
            @RequestBody SubjectDTO subject
    ) {
        Subject newSubject = subjectService.newSubject(subject);
        return ResponseEntity.ok().body(newSubject);
    }

    @PostMapping("/subjects-edit")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Subject> editSubject(
            @RequestBody SubjectDTO subject
    ) {
        Subject editSubject = subjectService.editSubject(subject);
        return ResponseEntity.ok().body(editSubject);
    }

    @DeleteMapping("/subjects-delete")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Boolean> deleteSubject(
            @RequestParam Long subjectId
    ) {
        Boolean deleteSubject = subjectService.deleteSubject(subjectId);
        return ResponseEntity.ok().body(deleteSubject);
    }

    @Autowired
    public void setPostService(@Qualifier("postServiceImpl") IPostService postService){
        this.postService = postService;
    }

    @Autowired
    public void setTagService(@Qualifier("tagServiceImpl") ITagService tagService){
        this.tagService = tagService;
    }

    @Autowired
    public void setCareerService(@Qualifier("careerServiceImpl") ICareerService careerService){
        this.careerService = careerService;
    }

    @Autowired
    public void setCommentService(@Qualifier("commentServiceImpl") ICommentService commentService){
        this.commentService = commentService;
    }

    @Autowired
    public void setSubjectService(@Qualifier("subjectServiceImpl") ISubjectService subjectService){
        this.subjectService = subjectService;
    }

    @Autowired
    public void setJwtService(@Qualifier("jwtServiceImpl") JwtServiceImpl jwtService){
        this.jwtService = jwtService;
    }

}
