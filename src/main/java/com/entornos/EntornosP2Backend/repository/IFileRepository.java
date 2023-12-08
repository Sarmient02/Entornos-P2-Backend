package com.entornos.EntornosP2Backend.repository;

import com.entornos.EntornosP2Backend.model.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IFileRepository extends JpaRepository<File, Long> {

    Optional<File> findById(Long id);

}
