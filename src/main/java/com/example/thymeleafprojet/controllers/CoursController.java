package com.example.thymeleafprojet.controllers;

import com.example.thymeleafprojet.entities.Cours;
import com.example.thymeleafprojet.repositories.CoursRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cours")
public class CoursController {

    @Autowired
    private CoursRepository coursRepository;

    @GetMapping
    public String listCours(Model model, @RequestParam(required = false) String niveau) {
        List<Cours> coursList;

        if (niveau != null && !niveau.isEmpty()) {
            coursList = coursRepository.findByNiveau(niveau);
        } else {
            coursList = coursRepository.findAll();
        }

        model.addAttribute("title", "Cours");
        model.addAttribute("coursList", coursList);
        model.addAttribute("niveaux", coursRepository.findDistinctNiveaux());
        model.addAttribute("niveauSelectionne", niveau);
        model.addAttribute("content", "cours/list");
        return "layout";
    }

    @GetMapping("/new")
    public String newCoursForm(Model model) {
        model.addAttribute("title", "Nouveau Cours");
        model.addAttribute("cours", new Cours());
        model.addAttribute("content", "cours/form");
        return "layout";
    }

    @PostMapping("/save")
    public String saveCours(@ModelAttribute Cours cours) {
        coursRepository.save(cours);
        return "redirect:/cours";
    }

    @GetMapping("/edit/{id}")
    public String editCoursForm(@PathVariable Long id, Model model) {
        Cours cours = coursRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid course Id:" + id));
        model.addAttribute("title", "Modifier Cours");
        model.addAttribute("cours", cours);
        model.addAttribute("content", "cours/form");
        return "layout";
    }

    @PostMapping("/update")
    public String updateCours(@ModelAttribute Cours cours) {
        coursRepository.save(cours);
        return "redirect:/cours";
    }

    @GetMapping("/delete/{id}")
    public String deleteCours(@PathVariable Long id) {
        coursRepository.deleteById(id);
        return "redirect:/cours";
    }
}