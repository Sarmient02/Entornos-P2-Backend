
package com.entornos.EntornosP2Backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import jakarta.persistence.*;

import java.io.Serializable;

@Data
@Entity
@Table(name = "tag", schema = "proyectofinal")
public class Tag implements Serializable {
    private static final long serialVersionUID = 96179722279655000L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = Integer.MAX_VALUE)
    private String name;

    @JsonIgnore
    @OneToOne(mappedBy = "idTag")
    private PostTag postTag;

}
