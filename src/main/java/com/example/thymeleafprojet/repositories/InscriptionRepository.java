package com.example.thymeleafprojet.repositories;

import com.example.thymeleafprojet.entities.Inscription;
import com.example.thymeleafprojet.entities.InscriptionPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface InscriptionRepository extends JpaRepository<Inscription, InscriptionPK> {


    @Query("SELECT i FROM Inscription i WHERE i.cours.id = :coursId")
    List<Inscription> findByCoursId(@Param("coursId") Long coursId);

    List<Inscription> findByStatut(String statut);

    @Query("SELECT i FROM Inscription i WHERE i.inscriptionPK.dateInscription BETWEEN :startDate AND :endDate")
    List<Inscription> findByDateInscriptionBetween(@Param("startDate") LocalDate startDate,
                                                   @Param("endDate") LocalDate endDate);

    @Query("SELECT i FROM Inscription i WHERE i.statut = :statut AND i.inscriptionPK.dateInscription BETWEEN :startDate AND :endDate")
    List<Inscription> findByStatutAndDateInscriptionBetween(@Param("statut") String statut,
                                                            @Param("startDate") LocalDate startDate,
                                                            @Param("endDate") LocalDate endDate);

    @Query("SELECT DISTINCT i.statut FROM Inscription i")
    List<String> findDistinctStatuts();

    @Query("SELECT i FROM Inscription i WHERE i.etudiant.id = :etudiantId AND i.cours.id = :coursId")
    Optional<Inscription> findByEtudiantAndCours(@Param("etudiantId") Long etudiantId,
                                                 @Param("coursId") Long coursId);
}