package com.entornos.EntornosP2Backend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "role", schema = "proyectofinal")
public class Role implements Serializable {
    private static final long serialVersionUID = 3886490734477594296L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    /*@OneToMany(mappedBy = "role")
    private List<UserRoles> userRoles;*/

}
