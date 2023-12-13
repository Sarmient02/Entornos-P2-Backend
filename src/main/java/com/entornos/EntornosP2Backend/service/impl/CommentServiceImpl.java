package com.entornos.EntornosP2Backend.service.impl;

import com.entornos.EntornosP2Backend.dto.CommentDTO;
import com.entornos.EntornosP2Backend.dto.CommentResponseDTO;
import com.entornos.EntornosP2Backend.dto.UserPostData;
import com.entornos.EntornosP2Backend.exception.CustomException;
import com.entornos.EntornosP2Backend.model.Comment;
import com.entornos.EntornosP2Backend.model.User;
import com.entornos.EntornosP2Backend.repository.ICommentRepository;
import com.entornos.EntornosP2Backend.repository.IPostRepository;
import com.entornos.EntornosP2Backend.repository.IUserRepository;
import com.entornos.EntornosP2Backend.service.interfaces.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletionException;

@Service
public class CommentServiceImpl implements ICommentService {

    private ICommentRepository commentRepository;
    private IUserRepository userRepository;
    private IPostRepository postRepository;

    @Override
    public List<CommentResponseDTO> getAll(Long postId) {
        List<Comment> comentario = commentRepository.findByPostId(postId);
        return comentario.stream().map(comment -> {
            User user = comment.getUser();
            UserPostData userData = new UserPostData();
            userData.setId(user.getId());
            userData.setFullName(user.getFullName());
            userData.setUsername(user.getUsername());
            return CommentResponseDTO.builder()
                    .id(comment.getId())
                    .body(comment.getBody())
                    .createdAt(comment.getCreatedAt())
                    .postId(comment.getPostId())
                    .user(userData)
                    .score(comment.getScore())
                    .build();
        }).toList();
    }

    @Override
    public Boolean deleteComment(Long id) {

        var exists = this.commentRepository.findById(id);
        if (exists.isEmpty()) {
            return false;
        }

        this.commentRepository.deleteById(id);

        return true;
    }

    @Override
    public CommentResponseDTO newComment(CommentDTO newComment) {

        var userExists = this.userRepository.findById(newComment.getIdUser());
        var postExists = this.postRepository.findById(newComment.getPostId());
        if (userExists.isEmpty() || postExists.isEmpty()) {
            return null;
        }
        var comment = new Comment();
        comment.setPostId(newComment.getPostId());
        comment.setIdUser(newComment.getIdUser());
        comment.setBody(newComment.getBody());
        comment.setCreatedAt(LocalDateTime.now());
        comment.setScore(newComment.getScore());
        Comment comentario = commentRepository.save(comment);
        User user = userRepository.findById(newComment.getIdUser()).orElseThrow(
                () -> new CustomException("Usuario no encontrado")
        );
        UserPostData userData = new UserPostData();
        userData.setId(user.getId());
        userData.setFullName(user.getFullName());
        userData.setUsername(user.getUsername());
        return CommentResponseDTO.builder()
                .id(comentario.getId())
                .body(comentario.getBody())
                .createdAt(comentario.getCreatedAt())
                .postId(comentario.getPostId())
                .user(userData)
                .score(comentario.getScore())
                .build();
    }

    @Override
    public Comment editComment(CommentDTO editComment) {

        var exists = this.commentRepository.findById(editComment.getId());
        if (exists.isEmpty()) {
            return null;
        }
        var e = exists.get();
        e.setBody(editComment.getBody());

        return this.commentRepository.save(e);
    }

    @Override
    public CommentResponseDTO getCommentById(Long id) {

        var exists = this.commentRepository.findById(id).orElseThrow(
                () -> new CustomException("Comentario no encontrado")
        );
        User user = exists.getUser();
        UserPostData userData = new UserPostData();
        userData.setId(user.getId());
        userData.setFullName(user.getFullName());
        userData.setUsername(user.getUsername());
        return CommentResponseDTO.builder()
                .id(exists.getId())
                .body(exists.getBody())
                .createdAt(exists.getCreatedAt())
                .postId(exists.getPostId())
                .user(userData)
                .score(exists.getScore())
                .build();
    }

    @Autowired
    public void setCommentRepository(ICommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Autowired
    public void setUserRepository(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setPostRepository(IPostRepository postRepository) {
        this.postRepository = postRepository;
    }
}
