# Gestion des Inscriptions - Application Web

## Description du projet

### Contexte
Application de gestion des inscriptions dans un établissement d'enseignement permettant de gérer les étudiants, les cours proposés et les inscriptions.

### Objectif
Faciliter la gestion des cours, des étudiants et des inscriptions à travers une interface web simple et intuitive.

### Public cible
Administrateurs et responsables pédagogiques d'un établissement.

### Fonctionnalité principale
L'application permet d'ajouter, modifier, supprimer et consulter les étudiants, les cours et leurs inscriptions, avec un tableau de bord statistique.

## Architecture technique

### 2.1 Stack technologique

**Backend :**
- Spring Boot 3.5.6
- Spring Data JPA/Hibernate
- Java 25 (OpenJDK)

**Frontend :**
- Thymeleaf 3.1
- HTML5, CSS3, JavaScript
- Bootstrap 5.3
- Font Awesome 6.0

**Base de données :**
- MySQL
- Pilote JDBC : mysql-connector-j 9.4.0

**Build :**
- Apache Maven 3.9+
- spring-boot-maven-plugin

### 2.2 Structure du code
```
src/
├── main/
│ ├── java/
│ │ └── com/example/thymleafprojet
│ │ ├── entities/ # Classes JPA
│ │ ├── repositories/ # Interfaces JPA
│ │ ├── services/ # Logique métier
│ │ └── controllers/ # Contrôleurs MVC
│ └── resources/
│ ├── templates/ # Vues Thymeleaf
│ └── application.properties
└── pom.xml
```

### 2.3 Diagramme d'architecture
```
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
```

## Fonctionnalités principales

### CRUD Complet
- **Étudiants** : Ajout, modification, suppression, consultation avec informations personnelles
- **Cours** : Gestion des cours avec intitulé, niveau et capacité
- **Inscriptions** : Création, modification, annulation des inscriptions étudiant-cours

### Filtrage
- **Inscriptions** : Filtrage par statut (inscrit, en attente, annulé) et par période
- **Cours** : Filtrage par niveau

### Tableau de bord statistique
- Nombre total d'étudiants et de cours
- Taux de remplissage moyen des cours
- Taux de succès par cours (note ≥ 10)
- Nombre d'inscrits par cours


## Modèle de données

### 4.1 Entités principales

**Etudiant :**
```java
- id : Long (identifiant unique)
- nom : String
- cne : String (code national)
- filiere : String
- inscriptions : List<Inscription> (OneToMany)
```

**Cours :**
```java
- id : Long (identifiant unique)
- intitule : String
- niveau : String
- capacite : Integer
- inscriptions : List<Inscription> (OneToMany)
```

**Inscription :**
```java
- inscriptionPK : InscriptionPK (clé composite)
- statut : String
- noteFinal : Double
- etudiant : Etudiant (ManyToOne)
- cours : Cours (ManyToOne)
```

### 4.2 Relations
```
Etudiant ↔ Inscription : OneToMany
Cours ↔ Inscription : OneToMany
Inscription ↔ Etudiant : ManyToOne
Inscription ↔ Cours : ManyToOne
```

### 4.3 Configuration base de données
URL de connexion :
```
jdbc:mysql://localhost:3306/thymeleafprojet?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
```
Identifiants :
```
Utilisateur : root
Mot de passe : (vide)
```

Configuration JPA/Hibernate :
```
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
```

## Lancer le projet

### 5.1 Prérequis
Java JDK 17 ou supérieur  
Maven 3.x  
MySQL 

<<<<<<< HEAD
https://drive.google.com/file/d/1jkcWR0wpOyDA6QpzxKbAr826BUmO4u-s/view?usp=sharing
=======
### 5.2 Installation et exécution
Cloner le dépôt :
```bash
git clone https://github.com/bouizerguane/ThymleafProjet.git
cd ThymleafProjet
```

Configurer la base de données :
```sql
CREATE DATABASE thymeleafprojet;
```
>>>>>>> 7bbcfaf (Modification du code et readme)

Vérifier la configuration :
- Modifier `application.properties` si nécessaire
- Adapter les identifiants de connexion MySQL

Compiler et lancer :
```bash
mvn clean install
mvn spring-boot:run
```

### 5.3 Accès à l'application
Application : http://localhost:8080  
Tableau de bord : Accessible depuis la page d'accueil

## Démonstration
Lien vers la vidéo de démonstration à ajouter

La démonstration inclut :
- Navigation dans les pages
- Création d'étudiants, cours et inscriptions
- Recherche et filtrage avancé
- Consultation du tableau de bord statistique

## Auteurs
- **Étudiant :** BOUIZERGUANE Mohamed  
- **Encadrant :** Pr. LACHGAR Mohamed  
- **Module :** Programmation Avancées et DevOps  
- **Établissement :** Ecole Normale Supérieure - Marrakech
