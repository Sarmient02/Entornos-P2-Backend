package com.entornos.EntornosP2Backend.repository;

import com.entornos.EntornosP2Backend.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISubjectRepository extends JpaRepository<Subject, Long> {

    @Query("SELECT s FROM Subject s WHERE lower(s.name) = lower(?1)")
    Subject findTopByName(String name);

    @Query("SELECT s FROM Subject s WHERE s.careerId = ?1")
    List<Subject> findByCareerId(Long careerId);
}
