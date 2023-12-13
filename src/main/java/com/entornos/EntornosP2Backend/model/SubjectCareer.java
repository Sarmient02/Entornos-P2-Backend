package com.entornos.EntornosP2Backend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "subject_careers", schema = "proyectofinal")
public class SubjectCareer implements Serializable {
    private static final long serialVersionUID = -1455252141965127122L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_subject", nullable = false)
    private Subject idSubject;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_career", nullable = false)
    private Career idCareer;


}
