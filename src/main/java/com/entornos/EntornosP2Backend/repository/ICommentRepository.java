package com.entornos.EntornosP2Backend.repository;

import com.entornos.EntornosP2Backend.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c WHERE c.postId = ?1")
    List<Comment> findByPostId(Long postId);
}
