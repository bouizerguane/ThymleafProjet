package com.example.thymeleafprojet.controllers;

import com.example.thymeleafprojet.entities.*;
import com.example.thymeleafprojet.repositories.CoursRepository;
import com.example.thymeleafprojet.repositories.EtudiantRepository;
import com.example.thymeleafprojet.repositories.InscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/inscriptions")
public class InscriptionController {

    @Autowired
    private InscriptionRepository inscriptionRepository;

    @Autowired
    private EtudiantRepository etudiantRepository;

    @Autowired
    private CoursRepository coursRepository;

    @GetMapping
    public String listInscriptions(Model model,
                                   @RequestParam(required = false) String statut,
                                   @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                   @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        List<Inscription> inscriptions;

        if (statut != null && startDate != null && endDate != null) {
            // Filtrage combiné
            inscriptions = inscriptionRepository.findByStatutAndDateInscriptionBetween(statut, startDate, endDate);
        } else if (statut != null) {
            // Filtrage par statut seulement
            inscriptions = inscriptionRepository.findByStatut(statut);
        } else if (startDate != null && endDate != null) {
            // Filtrage par période seulement
            inscriptions = inscriptionRepository.findByDateInscriptionBetween(startDate, endDate);
        } else {
            // Toutes les inscriptions
            inscriptions = inscriptionRepository.findAll();
        }

        model.addAttribute("title", "Inscriptions");
        model.addAttribute("inscriptions", inscriptions);
        model.addAttribute("statuts", inscriptionRepository.findDistinctStatuts());
        model.addAttribute("statutSelectionne", statut);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("content", "inscriptions/list");
        return "layout";
    }

    @GetMapping("/new")
    public String newInscriptionForm(Model model) {
        model.addAttribute("title", "Nouvelle Inscription");
        model.addAttribute("inscription", new Inscription());
        model.addAttribute("etudiants", etudiantRepository.findAll());
        model.addAttribute("cours", coursRepository.findAll());
        model.addAttribute("content", "inscriptions/form");
        return "layout";
    }

    @PostMapping("/save")
    public String saveInscription(@ModelAttribute("inscription") Inscription inscription,
                                  @RequestParam Long etudiantId,
                                  @RequestParam Long coursId) {

        Etudiant etudiant = etudiantRepository.findById(etudiantId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student Id"));
        Cours cours = coursRepository.findById(coursId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid course Id"));

        // Configuration de la clé composite
        InscriptionPK pk = new InscriptionPK();
        pk.setCoursId(coursId);
        pk.setEtudiantId(etudiantId);
        pk.setDateInscription(LocalDate.now());

        inscription.setInscriptionPK(pk);
        inscription.setEtudiant(etudiant);
        inscription.setCours(cours);

        inscriptionRepository.save(inscription);
        return "redirect:/inscriptions";
    }
}