package com.example.thymeleafprojet.controllers;

import com.example.thymeleafprojet.services.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/stats")
public class StatsController {

    @Autowired
    private StatsService statsService;

    // Méthode pour afficher la vue
    @GetMapping
    public String showStatsPage(Model model) {
        model.addAttribute("title", "Statistiques");
        model.addAttribute("content", "stats/dashboard");
        return "layout";
    }

    // Méthode pour envoyer les données JSON
    @GetMapping("/data")
    @ResponseBody
    public Map<String, Object> getStatsData() {
        // Délégation complète au service
        return statsService.getAllStats();
    }
}