package com.entornos.EntornosP2Backend.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
public class CommentResponseDTO implements Serializable {

    private static final long serialVersionUID = -6648546971746976208L;
    private Long id;
    private String body;
    private LocalDateTime createdAt;
    private Long postId;
    private UserPostData user;
    private Long score;
}
