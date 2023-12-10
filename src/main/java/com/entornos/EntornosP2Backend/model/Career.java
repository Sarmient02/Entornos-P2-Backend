package com.entornos.EntornosP2Backend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
@Table(name = "career", schema = "proyectofinal")
public class Career implements Serializable {
    private static final long serialVersionUID = -6225764162301638186L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = Integer.MAX_VALUE)
    private String name;

    @Column(name = "career_code", nullable = false, length = Integer.MAX_VALUE)
    private String careerCode;

    @OneToOne(mappedBy = "idCareer")
    private SubjectCareer subjectCareer;


}
