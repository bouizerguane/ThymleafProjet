package com.example.thymeleafprojet.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
public class Inscription implements Serializable {

    @EmbeddedId
    private InscriptionPK inscriptionPK;

    private String statut;
    private Double noteFinal;

    @ManyToOne
    @MapsId("etudiantId")
    @JoinColumn(name = "etudiant_id")
    private Etudiant etudiant;

    @ManyToOne
    @MapsId("coursId")
    @JoinColumn(name = "cours_id")
    private Cours cours;

    public Inscription() {
        this.inscriptionPK = new InscriptionPK();
    }

    public Inscription(Etudiant etudiant, Cours cours, String statut) {
        this.inscriptionPK = new InscriptionPK();
        this.inscriptionPK.setEtudiantId(etudiant.getId());
        this.inscriptionPK.setCoursId(cours.getId());
        this.inscriptionPK.setDateInscription(LocalDate.now());
        this.etudiant = etudiant;
        this.cours = cours;
        this.statut = statut;
    }

    public LocalDate getDateInscription() {
        return inscriptionPK != null ? inscriptionPK.getDateInscription() : null;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public Double getNoteFinal() {
        return noteFinal;
    }

    public void setNoteFinal(Double noteFinal) {
        this.noteFinal = noteFinal;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public Cours getCours() {
        return cours;
    }

    public void setCours(Cours cours) {
        this.cours = cours;
    }

    public InscriptionPK getInscriptionPK() {
        return inscriptionPK;
    }

    public void setInscriptionPK(InscriptionPK inscriptionPK) {
        this.inscriptionPK = inscriptionPK;
    }

}
