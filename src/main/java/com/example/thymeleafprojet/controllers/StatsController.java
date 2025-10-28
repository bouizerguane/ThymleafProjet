package com.example.thymeleafprojet.controllers;

import com.example.thymeleafprojet.services.StatsService;
import com.example.thymeleafprojet.repositories.CoursRepository;
import com.example.thymeleafprojet.repositories.EtudiantRepository;
import com.example.thymeleafprojet.repositories.InscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/stats")
public class StatsController {

    @Autowired
    private StatsService statsService;

    @Autowired
    private CoursRepository coursRepository;

    @Autowired
    private EtudiantRepository etudiantRepository;

    @Autowired
    private InscriptionRepository inscriptionRepository;

    @GetMapping
    public String showStats(Model model) {
        try {
            // Récupérer les données depuis le service
            Map<Long, Double> tauxRemplissage = statsService.getTauxRemplissageParCours();
            Map<Long, Double> tauxSucces = statsService.getTauxSuccesParCours();
            Map<String, Object> statsGenerales = statsService.getStatsGenerales();

            // Calculer le nombre d'inscrits par cours
            Map<Long, Integer> inscritsParCours = new HashMap<>();
            coursRepository.findAll().forEach(cours -> {
                int nombreInscriptions = inscriptionRepository.findByCoursId(cours.getId()).size();
                inscritsParCours.put(cours.getId(), nombreInscriptions);
            });

            // Calculer les moyennes pour les cartes
            double tauxRemplissageMoyen = tauxRemplissage.values().stream()
                    .mapToDouble(Double::doubleValue)
                    .average()
                    .orElse(0);

            double tauxSuccesMoyen = tauxSucces.values().stream()
                    .mapToDouble(Double::doubleValue)
                    .average()
                    .orElse(0);

            List<Map<String, Object>> coursList = coursRepository.findAll().stream()
                    .map(cours -> {
                        Map<String, Object> coursMap = new HashMap<>();
                        coursMap.put("id", cours.getId());
                        coursMap.put("intitule", cours.getIntitule());
                        return coursMap;
                    })
                    .collect(Collectors.toList());

            model.addAttribute("title", "Statistiques");
            model.addAttribute("content", "stats/dashboard");

            // Données pour les cartes de résumé
            model.addAttribute("totalEtudiants", statsGenerales.get("totalEtudiants"));
            model.addAttribute("totalCours", statsGenerales.get("totalCours"));
            model.addAttribute("moyenneRemplissage", String.format("%.1f%%", tauxRemplissageMoyen));
            model.addAttribute("moyenneSucces", String.format("%.1f%%", tauxSuccesMoyen));

            model.addAttribute("coursList", coursList);
            model.addAttribute("tauxRemplissage", tauxRemplissage);
            model.addAttribute("tauxSucces", tauxSucces); // ⬅️ CORRECTION : "tauxSucces" au lieu de "tauxSucces"
            model.addAttribute("inscritsParCours", inscritsParCours);

            // DEBUG : Afficher les données dans la console serveur
            System.out.println("=== DONNÉES STATISTIQUES ===");
            System.out.println("Nombre de cours: " + coursList.size());
            System.out.println("Taux succès: " + tauxSucces);
            System.out.println("Inscrits par cours: " + inscritsParCours);

            return "layout";

        } catch (Exception e) {
            e.printStackTrace();

            // En cas d'erreur, données par défaut avec liste vide
            model.addAttribute("title", "Statistiques");
            model.addAttribute("content", "stats/dashboard");
            model.addAttribute("totalEtudiants", 0);
            model.addAttribute("totalCours", 0);
            model.addAttribute("moyenneRemplissage", "0%");
            model.addAttribute("moyenneSucces", "0%");
            model.addAttribute("coursList", List.of());
            model.addAttribute("tauxRemplissage", new HashMap<>());
            model.addAttribute("tauxSucces", new HashMap<>());
            model.addAttribute("inscritsParCours", new HashMap<>());

            return "layout";
        }
    }
}