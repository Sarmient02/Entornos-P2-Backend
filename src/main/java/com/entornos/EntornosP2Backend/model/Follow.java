package com.entornos.EntornosP2Backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "following_user_id", insertable = false, updatable = false)
    private User followingUser;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "followed_user_id", insertable = false, updatable = false)
    private User followedUser;

    @Column(name = "created_at")
    private Date createdAt;

}
