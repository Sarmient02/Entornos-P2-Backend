package com.entornos.EntornosP2Backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "subject", schema = "proyectofinal")
public class Subject implements Serializable {
    private static final long serialVersionUID = -6719407749632392L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = Integer.MAX_VALUE)
    private String name;

    @Column(name = "career_id", nullable = false)
    private Long careerId;

    @JsonIgnore
    @OneToMany(mappedBy = "subject", fetch = FetchType.LAZY)
    private List<Post> posts;

    @JsonIgnore
    @OneToOne(mappedBy = "idSubject", fetch = FetchType.LAZY)
    private SubjectCareer subjectCareer;


}
