package com.entornos.EntornosP2Backend.model;

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

    @OneToMany(mappedBy = "subject")
    private List<Post> posts;

    @OneToOne(mappedBy = "idSubject")
    private SubjectCareer subjectCareer;


}