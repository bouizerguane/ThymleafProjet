package com.example.thymeleafprojet.repositories;

import com.example.thymeleafprojet.entities.Cours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoursRepository extends JpaRepository<Cours, Long> {

    List<Cours> findByNiveau(String niveau);

    @Query("SELECT DISTINCT c.niveau FROM Cours c")
    List<String> findDistinctNiveaux();
}