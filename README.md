1. Description du projet
	1-1- Contexte

Application de gestion des inscriptions dans un établissement d’enseignement.
Elle permet de gérer les étudiants, les cours proposés et les inscriptions.

	1-2- Objectif

Faciliter la gestion des cours, des étudiants et des inscriptions à travers une interface web simple et intuitive.

	1-3- Public cible

Administrateurs et responsables pédagogiques d’un établissement.

	1-4- Fonction principale

L’application permet d’ajouter, modifier, supprimer et consulter les étudiants, les cours et leurs inscriptions, avec un tableau de bord statistique.

2. Architecture technique

L’application « Gestion des inscriptions » repose sur une architecture en couches basée sur le modèle MVC (Model – View – Controller) de Spring Boot.
Elle combine une logique backend, une interface web dynamique et une base de données relationnelle.

	2-1- Stack technologique
	a- Backend

Framework principal : Spring Boot 3.5.6
→ Fournit une structure légère, un serveur Tomcat intégré.

ORM / Persistance : Spring Data JPA et Hibernate
→ Permettent la gestion automatique des entités et des relations entre tables.

Langage : Java 25 (OpenJDK)
→ Utilisé pour le développement orienté objet et la compatibilité avec les dernières API Spring.

	b- Frontend

Moteur de templates : Thymeleaf 3.1
→ Génère des pages HTML dynamiques et lie les objets Java aux vues via th:object et th:field.

Technologies web : HTML5, CSS3, JavaScript (chargées via CDN).

Framework CSS : Bootstrap 5.3
→ Fournit un design responsive et des composants prêts à l’emploi (boutons, tableaux, formulaires).

Icônes : Font Awesome 6.0 pour les symboles dans le menu et les actions CRUD.

	c- Base de données

Système de gestion : MySQL
→ Gère la persistance des données (étudiants, cours, inscriptions).

Pilote JDBC : mysql-connector-j 9.4.0
→ Permet la communication entre Java et MySQL.

Création des tables : Automatique via Hibernate (spring.jpa.hibernate.ddl-auto=update).

	d- Build et configuration

Outil de build : Apache Maven 3.9+
→ Utilisé pour gérer les dépendances, compiler et exécuter le projet.

Plugin de lancement : spring-boot-maven-plugin
→ Permet d’exécuter l’application avec mvn spring-boot:run.

	e-Structure de configuration :

application.properties → paramètres de la base de données et de Hibernate.

pom.xml → versions et dépendances du projet.

	2-2- Structure du code

Cette structuration garantit une séparation claire entre les couches modèle, contrôle et vue.

entities/	Contient les classes JPA représentant les tables.
repositories/	Interfaces pour l’accès aux données avec JPA.
services/	Logique métier (calculs et statistiques).
controllers/	Contrôleurs web MVC (gestion des routes et vues).
templates/	Fichiers HTML Thymeleaf utilisés comme vues dynamiques.


	2-3- Diagramme d’architecture (flux technique)
	a- Description textuelle
Le flux de traitement d’une requête utilisateur se déroule comme suit :
	L’utilisateur interagit avec une page web (ex. formulaire Thymeleaf).
	La requête HTTP est envoyée à un contrôleur Spring MVC (@Controller).
	Le contrôleur appelle un service pour exécuter la logique métier.
	Le service interroge un repository JPA pour récupérer ou sauvegarder les données.
	Le repository communique avec la base MySQL via Hibernate.
	Le résultat est renvoyé au contrôleur, puis à la vue Thymeleaf affichée dans le navigateur.

	b- Schéma simplifié
Navigateur Web
     ↓
[Vues Thymeleaf]
     ↓
[Controllers Spring MVC]
     ↓
[Services Métier]
     ↓
[Repositories JPA]
     ↓
[Base de données MySQL]


3. Fonctionnalités principales

	3-1- CRUD sur les entités principales
L’application permet de gérer efficacement les principales entités :
* Étudiants :
	Ajouter un nouvel étudiant avec ses informations personnelles.
	Modifier les données d’un étudiant existant.
	Supprimer un étudiant si nécessaire.
	Consulter la liste complète des étudiants.

* Cours :
	Ajouter un nouveau cours avec son intitulé, niveau et capacité.
	Modifier les informations d’un cours existant.
	Supprimer un cours annulé ou obsolète.
	Consulter la liste des cours avec possibilité de filtrer par niveau.

* Inscriptions :
	Créer une inscription pour un étudiant à un cours spécifique.
	Modifier ou annuler une inscription existante.
	Supprimer une inscription si nécessaire.
	Consulter toutes les inscriptions, avec la possibilité de filtrer par statut ou période.

	3-2- Recherche et filtrage

* Filtrage des inscriptions :
	Par statut : inscrit, en attente, annulé.
	Par période : date de début et date de fin de l’inscription.
	Combinaison statut + période pour des recherches précises.
* Filtrage des cours :
	Par niveau pour visualiser uniquement les cours d’un certain niveau.

	3-3- Tableau de bord et statistiques

L’application propose un tableau de bord interactif affichant des indicateurs clés pour le suivi global :
	Nombre total d’étudiants, de cours.
	Taux de remplissage moyen des cours (calculé à partir du nombre d’inscriptions par rapport à la capacité des cours).
	Taux de succès par cours (pourcentage d’étudiants ayant obtenu une note ≥ 10).
	Nombre d’inscrits par cours pour identifier les cours les plus demandés.

Ces données sont présentées via des cartes et graphiques simples pour une lecture rapide et intuitive.

	3-4- Gestion des statuts

L’application gère les statuts des inscriptions et des entités pour un suivi précis :
	Inscriptions : inscrit, terminé, abondonné.

4. Modèle de données

L’application repose sur un modèle de données simple, comprenant trois entités principales : Etudiant, Cours et Inscription.

	4-1- Entités
	a- Etudiant: Représente un étudiant inscrit:
		id : identifiant unique de l’étudiant.
		nom : nom complet de l’étudiant.
		cne : code national de l’étudiant.
		filiere : filière de l’étudiant.
		inscriptions : liste des inscriptions de l’étudiant aux cours (relation OneToMany).

	b- Cours: Représente un cours disponible pour l’inscription:
		id : identifiant unique du cours.
		intitule : nom ou intitulé du cours.
		niveau : niveau du cours (ex : Tronc Commun, 1ère année, etc.).
		capacite : nombre maximum d’étudiants pouvant s’inscrire.
		inscriptions : liste des inscriptions associées à ce cours (relation OneToMany).

	c- Inscription: Représente l’inscription d’un étudiant à un cours donné à une date spécifique.
		inscriptionPK : clé composite incluant coursId, etudiantId et dateInscription.
		statut : état de l’inscription (ex : en attente, confirmée, annulée).
		noteFinal : note finale obtenue par l’étudiant pour ce cours.
		etudiant : référence vers l’entité Etudiant (ManyToOne).
		cours : référence vers l’entité Cours (ManyToOne).

	4-2- Relations entre les entités
		Etudiant ↔ Inscription : OneToMany (un étudiant peut avoir plusieurs inscriptions).
		Cours ↔ Inscription : OneToMany (un cours peut avoir plusieurs inscriptions).
		Inscription ↔ Etudiant/Cours : ManyToOne (une inscription est liée à un étudiant et à un cours spécifiques).

	4-3- Configuration de la base de données
L’application utilise MySQL comme base de données pour stocker les informations des étudiants, cours et inscriptions.
	a- Paramètres de connexion
		URL de connexion :
jdbc:mysql://localhost:3306/thymeleafprojet?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
		Nom d’utilisateur : root
		Mot de passe : (vide)
	b- Configuration JPA / Hibernate
		Affichage des requêtes SQL : activé
		spring.jpa.show-sql = true
		Stratégie de génération des tables : update
		spring.jpa.hibernate.ddl-auto = update

5. Lancer le projet
	5-1- Prérequis
Pour exécuter l’application “InscriptionCours”, il est nécessaire d’avoir les éléments suivants installés sur votre machine :
	Java JDK 17 (ou version compatible avec Spring Boot 3.x)
	Maven 3.x pour la gestion des dépendances et la compilation du projet
	MySQL ou tout autre SGBD compatible pour la base de données

	5-2- Installation et exécution
	Cloner le projet depuis le dépôt GitHub :
		git clone https://github.com/bouizerguane/ThymleafProjet.git
	Configurer la base de données
		Créer une base de données thymeleafprojet dans MySQL.
		Vérifier que les paramètres dans application.properties correspondent à votre configuration locale (URL, username, password).
	Compiler le projet avec Maven
		mvn clean install
	Lancer l’application
		mvn spring-boot:run
	L’application sera accessible à l’adresse : http://localhost:8080
	5-3- Accéder au tableau de bord
		La page d’accueil affiche les statistiques globales (total étudiants, total cours, taux de remplissage moyen).
		Les sections Étudiants, Cours et Inscriptions permettent de gérer les entités via les interfaces CRUD.

6- Démonstration vidéo

https://drive.google.com/file/d/1jkcWR0wpOyDA6QpzxKbAr826BUmO4u-s/view?usp=sharing

7- Auteur / Encadrement
 
 Nom de l'étudiant: BOUIZERGUANE Mohamed
Encadrant : Pr. LACHGAR Mohamed
Module: Programmation Avancées et DevOps
Etablissement: Ecole Normale Supérieure - Marrakech

