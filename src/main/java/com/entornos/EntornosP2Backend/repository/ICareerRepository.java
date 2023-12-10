package com.entornos.EntornosP2Backend.repository;

import com.entornos.EntornosP2Backend.model.Career;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ICareerRepository extends JpaRepository<Career, Long> {

    @Query("select c from Career c where lower(c.name) = lower(?1)")
    Career findTopByName(String name);
}
