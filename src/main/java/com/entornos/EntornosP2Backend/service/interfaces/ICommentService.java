package com.entornos.EntornosP2Backend.service.interfaces;

import com.entornos.EntornosP2Backend.dto.CommentDTO;
import com.entornos.EntornosP2Backend.model.Comment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ICommentService {

    List<Comment> getAll(Long postId);

    Boolean deleteComment(Long id);

    Comment newComment(CommentDTO newComment);

    Comment editComment(CommentDTO editComment);

    Comment getCommentById(Long id);
}
