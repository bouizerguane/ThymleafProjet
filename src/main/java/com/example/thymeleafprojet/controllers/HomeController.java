package com.example.thymeleafprojet.controllers;

import com.example.thymeleafprojet.repositories.CoursRepository;
import com.example.thymeleafprojet.repositories.EtudiantRepository;
import com.example.thymeleafprojet.repositories.InscriptionRepository;
import com.example.thymeleafprojet.services.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    private EtudiantRepository etudiantRepository;

    @Autowired
    private CoursRepository coursRepository;

    @Autowired
    private InscriptionRepository inscriptionRepository;

    @Autowired
    private StatsService statsService;

    @GetMapping("/")
    public String home(Model model) {
        // DEBUG: Vérifier que le service fonctionne
        try {
            Map<String, Object> stats = statsService.getStatsGenerales();
            System.out.println("Stats générées: " + stats);

            Map<Long, Double> tauxRemplissage = statsService.getTauxRemplissageParCours();
            System.out.println("Taux de remplissage: " + tauxRemplissage);

            model.addAttribute("title", "Tableau de Bord");
            model.addAttribute("totalEtudiants", stats.get("totalEtudiants"));
            model.addAttribute("totalCours", stats.get("totalCours"));
            model.addAttribute("totalInscriptions", stats.get("totalInscriptions"));
            model.addAttribute("tauxRemplissageMoyen", stats.get("tauxRemplissageMoyen"));
            model.addAttribute("content", "home");
        } catch (Exception e) {
            System.err.println("ERREUR dans HomeController: " + e.getMessage());
            e.printStackTrace();
            // Valeurs par défaut en cas d'erreur
            model.addAttribute("totalEtudiants", 0);
            model.addAttribute("totalCours", 0);
            model.addAttribute("totalInscriptions", 0);
            model.addAttribute("tauxRemplissageMoyen", 0);
        }

        return "layout";
    }
}