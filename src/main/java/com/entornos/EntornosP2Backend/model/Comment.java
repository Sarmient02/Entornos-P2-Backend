package com.entornos.EntornosP2Backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Table(name = "comment", schema = "proyectofinal")
public class Comment implements Serializable {
    private static final long serialVersionUID = -6648546971746976208L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "body", length = Integer.MAX_VALUE)
    private String body;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "id_post")
    private Long postId;

    @Column(name = "id_user")
    private Long idUser;

    @Column(name = "score")
    private Long score;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_post", nullable = false, insertable = false, updatable = false)
    private Post post;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_user", insertable = false, updatable = false)
    private User user;

}
