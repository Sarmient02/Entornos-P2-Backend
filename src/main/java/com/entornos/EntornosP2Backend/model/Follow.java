package com.entornos.EntornosP2Backend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

@Data
@Entity
@Table(name = "follow", schema = "proyectofinal")
public class Follow implements Serializable {
    private static final long serialVersionUID = 8397915488756440787L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "following_user_id")
    private Long followerId;

    @Column(name = "followed_user_id")
    private Long followedId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "following_user_id", insertable = false, updatable = false)
    private User followingUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "followed_user_id", insertable = false, updatable = false)
    private User followedUser;

    @Column(name = "created_at")
    private Date createdAt;

}
