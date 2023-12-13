package com.entornos.EntornosP2Backend.repository;

import com.entornos.EntornosP2Backend.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPostRepository extends JpaRepository<Post, Long> {

    boolean existsByIdAndUserId(Long id, Long userId);

}
