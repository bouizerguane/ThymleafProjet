package com.example.thymeleafprojet.controllers;

import com.example.thymeleafprojet.entities.Etudiant;
import com.example.thymeleafprojet.repositories.EtudiantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/etudiants")
public class EtudiantController {

    @Autowired
    private EtudiantRepository etudiantRepository;

    @GetMapping
    public String listEtudiants(Model model) {
        model.addAttribute("title", "Étudiants");
        model.addAttribute("etudiants", etudiantRepository.findAll());
        model.addAttribute("content", "etudiants/list");
        return "layout";
    }

    @GetMapping("/new")
    public String newEtudiantForm(Model model) {
        model.addAttribute("title", "Nouvel Étudiant");
        model.addAttribute("etudiant", new Etudiant());
        model.addAttribute("content", "etudiants/form");
        return "layout";
    }

    @PostMapping("/save")
    public String saveEtudiant(@ModelAttribute Etudiant etudiant) {
        etudiantRepository.save(etudiant);
        return "redirect:/etudiants";
    }

    @GetMapping("/edit/{id}")
    public String editEtudiantForm(@PathVariable Long id, Model model) {
        Etudiant etudiant = etudiantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student Id:" + id));
        model.addAttribute("title", "Modifier Étudiant");
        model.addAttribute("etudiant", etudiant);
        model.addAttribute("content", "etudiants/form");
        return "layout";
    }

    @PostMapping("/update")
    public String updateEtudiant(@ModelAttribute Etudiant etudiant) {
        etudiantRepository.save(etudiant);
        return "redirect:/etudiants";
    }

    @GetMapping("/delete/{id}")
    public String deleteEtudiant(@PathVariable Long id) {
        etudiantRepository.deleteById(id);
        return "redirect:/etudiants";
    }
}