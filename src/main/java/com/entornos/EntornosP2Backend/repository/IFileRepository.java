package com.entornos.EntornosP2Backend.repository;

import com.entornos.EntornosP2Backend.model.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface IFileRepository extends JpaRepository<File, Long> {

    Optional<File> findById(Long id);

    Optional<File> findByHashName(String hashName);

    @Transactional
    void deleteByFileUrl(String fileUrl);

}