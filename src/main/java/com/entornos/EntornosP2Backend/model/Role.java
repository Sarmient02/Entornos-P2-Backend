package com.entornos.EntornosP2Backend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "role")
public class Role implements Serializable {
    private static final long serialVersionUID = 3886490734477594296L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
}
