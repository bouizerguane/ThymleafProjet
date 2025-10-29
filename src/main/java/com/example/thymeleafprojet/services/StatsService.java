package com.example.thymeleafprojet.services;

import com.example.thymeleafprojet.entities.Cours;
import com.example.thymeleafprojet.entities.Inscription;
import com.example.thymeleafprojet.repositories.CoursRepository;
import com.example.thymeleafprojet.repositories.EtudiantRepository;
import com.example.thymeleafprojet.repositories.InscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StatsService {

    @Autowired
    private CoursRepository coursRepository;

    @Autowired
    private InscriptionRepository inscriptionRepository;

    @Autowired
    private EtudiantRepository etudiantRepository;

    // Taux de remplissage par cours
    public Map<Long, Double> getTauxRemplissageParCours() {
        Map<Long, Double> taux = new HashMap<>();

        List<Cours> tousLesCours = coursRepository.findAll();

        for (Cours cours : tousLesCours) {
            try {
                int nombreInscriptions = inscriptionRepository.findByCoursId(cours.getId()).size();
                double tauxRemplissage = cours.getCapacite() > 0 ?
                        (double) nombreInscriptions / cours.getCapacite() * 100 : 0;
                taux.put(cours.getId(), Math.round(tauxRemplissage * 100.0) / 100.0);
            } catch (Exception e) {
                System.err.println("Erreur pour le cours " + cours.getId() + ": " + e.getMessage());
                taux.put(cours.getId(), 0.0);
            }
        }

        return taux;
    }

    // Taux de succès par cours (note >= 10)
    public Map<Long, Double> getTauxSuccesParCours() {
        Map<Long, Double> taux = new HashMap<>();

        List<Cours> tousLesCours = coursRepository.findAll();

        for (Cours cours : tousLesCours) {
            try {
                List<Inscription> inscriptions = inscriptionRepository.findByCoursId(cours.getId());

                long nombreReussi = inscriptions.stream()
                        .filter(ins -> ins.getNoteFinal() != null && ins.getNoteFinal() >= 10)
                        .count();

                double tauxSucces = inscriptions.isEmpty() ? 0 :
                        (double) nombreReussi / inscriptions.size() * 100;
                taux.put(cours.getId(), Math.round(tauxSucces * 100.0) / 100.0);
            } catch (Exception e) {
                System.err.println("Erreur pour le cours " + cours.getId() + ": " + e.getMessage());
                taux.put(cours.getId(), 0.0);
            }
        }

        return taux;
    }

    // Statistiques générales
    public Map<String, Object> getStatsGenerales() {
        Map<String, Object> stats = new HashMap<>();

        try {
            long totalEtudiants = etudiantRepository.count();
            long totalCours = coursRepository.count();
            long totalInscriptions = inscriptionRepository.count();

            double tauxRemplissageMoyen = getTauxRemplissageParCours().values().stream()
                    .mapToDouble(Double::doubleValue)
                    .average()
                    .orElse(0);

            stats.put("totalEtudiants", totalEtudiants);
            stats.put("totalCours", totalCours);
            stats.put("totalInscriptions", totalInscriptions);
            stats.put("tauxRemplissageMoyen", Math.round(tauxRemplissageMoyen));
        } catch (Exception e) {
            System.err.println("Erreur dans getStatsGenerales: " + e.getMessage());
            stats.put("totalEtudiants", 0);
            stats.put("totalCours", 0);
            stats.put("totalInscriptions", 0);
            stats.put("tauxRemplissageMoyen", 0);
        }

        return stats;
    }

    // Récupère TOUTES les stats en une seule fois
    public Map<String, Object> getAllStats() {
        Map<String, Object> allStats = new HashMap<>();

        try {
            Map<Long, Double> tauxRemplissage = getTauxRemplissageParCours();
            Map<Long, Double> tauxSucces = getTauxSuccesParCours();
            Map<String, Object> statsGenerales = getStatsGenerales();

            // Calculer les moyennes (logique métier dans le service)
            double tauxRemplissageMoyen = tauxRemplissage.values().stream()
                    .mapToDouble(Double::doubleValue)
                    .average()
                    .orElse(0);

            double tauxSuccesMoyen = tauxSucces.values().stream()
                    .mapToDouble(Double::doubleValue)
                    .average()
                    .orElse(0);

            // Préparer la liste des cours simplifiée
            List<Map<String, Object>> coursList = coursRepository.findAll().stream()
                    .map(cours -> {
                        Map<String, Object> coursMap = new HashMap<>();
                        coursMap.put("id", cours.getId());
                        coursMap.put("intitule", cours.getIntitule());
                        return coursMap;
                    })
                    .collect(Collectors.toList());

            // Calculer le nombre d'inscrits par cours
            Map<Long, Integer> inscritsParCours = new HashMap<>();
            coursRepository.findAll().forEach(cours -> {
                int nombreInscriptions = inscriptionRepository.findByCoursId(cours.getId()).size();
                inscritsParCours.put(cours.getId(), nombreInscriptions);
            });

            // Assembler toutes les données
            allStats.put("success", true);
            allStats.put("coursList", coursList);
            allStats.put("tauxRemplissage", tauxRemplissage);
            allStats.put("tauxSucces", tauxSucces);
            allStats.put("inscritsParCours", inscritsParCours);
            allStats.put("statsGenerales", statsGenerales);
            allStats.put("moyenneRemplissage", Math.round(tauxRemplissageMoyen * 10.0) / 10.0);
            allStats.put("moyenneSucces", Math.round(tauxSuccesMoyen * 10.0) / 10.0);

        } catch (Exception e) {
            allStats.put("success", false);
            allStats.put("error", e.getMessage());
        }

        return allStats;
    }
}
