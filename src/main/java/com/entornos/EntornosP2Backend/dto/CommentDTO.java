package com.entornos.EntornosP2Backend.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CommentDTO implements Serializable {

    private static final long serialVersionUID = -6648546971746976208L;
    private Long id;
    private String body;
    private Long postId;
    private Long idUser;
    private Long score;
}
