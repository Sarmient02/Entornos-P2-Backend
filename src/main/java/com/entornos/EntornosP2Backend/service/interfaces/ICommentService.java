package com.entornos.EntornosP2Backend.service.interfaces;

import com.entornos.EntornosP2Backend.dto.CommentDTO;
import com.entornos.EntornosP2Backend.dto.CommentResponseDTO;
import com.entornos.EntornosP2Backend.model.Comment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ICommentService {

    List<CommentResponseDTO> getAll(Long postId);

    Boolean deleteComment(Long id);

    CommentResponseDTO newComment(CommentDTO newComment);

    Comment editComment(CommentDTO editComment);

    CommentResponseDTO getCommentById(Long id);
}
