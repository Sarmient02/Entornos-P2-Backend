package com.entornos.EntornosP2Backend.repository;

import com.entornos.EntornosP2Backend.model.Career;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ICareerRepository extends JpaRepository<Career, Long> {

    @Query("select c from Career c where lower(c.name) = lower(?1) or lower(c.careerCode) = lower(?2)")
    Career findTopByNameOrCode(String name, String careerCode);

    @Query("select c from Career c where lower(c.careerCode) = lower(?1) and c.id <> ?2")
    Career findByCodeAndIdNot(String careerCode, Long id);
}
