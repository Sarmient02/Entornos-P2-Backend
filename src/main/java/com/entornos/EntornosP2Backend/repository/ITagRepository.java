package com.entornos.EntornosP2Backend.repository;

import com.entornos.EntornosP2Backend.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ITagRepository extends JpaRepository<Tag, Long> {

    @Query("SELECT t FROM Tag t WHERE LOWER(t.name) = LOWER(:name)")
    Tag findTopByName(String name);

}