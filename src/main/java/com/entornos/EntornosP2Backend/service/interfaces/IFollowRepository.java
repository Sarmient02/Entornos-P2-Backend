package com.entornos.EntornosP2Backend.service.interfaces;

import com.entornos.EntornosP2Backend.model.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IFollowRepository extends JpaRepository<Follow, Long> {

    @Query("SELECT f FROM Follow f WHERE f.followedId = ?1 AND f.followerId = ?2")
    Follow findByFollowedIdAndFollowerId(Long followedId, Long followerId);
}
