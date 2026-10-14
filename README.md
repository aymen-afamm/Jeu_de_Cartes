# Jeu de Cartes - Technical Test (Spring Boot)

Application web développée en Java avec Spring Boot simulant la distribution et le tri d'une main de 10 cartes à jouer, conformément aux spécifications du test technique.

---

## 1. Fonctionnalités Principales

* **Génération automatique du paquet (Pack) :** 52 cartes uniques issues du produit cartésien de 4 couleurs et 13 valeurs.
* **Mélange aléatoire :** Mélange de cartes sans altération du paquet source.
* **Tirage d'une main de 10 cartes :** Tirage sans remise garantissant l'absence de doublons.
* **Tri déterministe :** Tri d'une copie de la main selon la règle métier :
  1. D'abord par couleur : **Carreau (♦) &rarr; Cœur (♥) &rarr; Pique (♠) &rarr; Trèfle (♣)**
  2. Puis par valeur : **As &rarr; 2 &rarr; 3 &rarr; ... &rarr; 10 &rarr; Valet &rarr; Dame &rarr; Roi**
* **Affichage dans l'interface web :**
  * La main aléatoire (10 cartes non triées)
  * La main triée (les mêmes 10 cartes triées)
  * Le paquet complet de 52 cartes
  * Un bouton **« Générer une nouvelle main »** pour rejouer à volonté.
* **Double rendu visuel des cartes :**
  * Fichiers images SVG vectoriels nets (`src/main/resources/static/img/cards/`).
  * Repli visuel automatique (fallback) en HTML/CSS pur pour garantir qu'aucune image brisée ne s'affiche si les images sont désactivées.
* **Gestion centralisée des erreurs :** Gestionnaire `@ControllerAdvice` renvoyant une réponse structurée de type `ApiError`.
* **Endpoint API REST companion :** `GET /api/random-hand-cards` fournissant le résultat structuré en JSON.

---

## 2. Choix Techniques et Justifications

Conformément aux critères d'évaluation du test technique :

| Composant | Choix technique | Justification |
| :--- | :--- | :--- |
| **Langage** | Java 17 LTS | Version moderne, stable et performante, avec syntaxe moderne (`switch`, `record`, typage fort). |
| **Framework Web** | Spring Boot 3.3.4 (Spring MVC) | Standard de l'industrie pour Java. Permet une séparation nette MVC, l'injection de dépendances et une configuration minimale. |
| **Moteur de Template** | Thymeleaf | Intégration native avec Spring Boot, rendu côté serveur (SSR) sans framework JavaScript lourd, syntaxe HTML naturelle. |
| **Build & Dépendances** | Apache Maven | Gestion standard des dépendances, reproductibilité des builds et cycle de vie de test automatisé. |
| **Tests Unitaires** | JUnit 5 + Spring Boot Test | Écriture de tests unitaires clairs, indépendants et déterministes sur la logique métier et l'API web. |
| **Modélisation** | Enums `Suit` & `Rank`, Classe `Card`, `Pack` | Utilisation des énumérations Java garantissant l'immutabilité, l'absence de valeurs invalides et un tri naturel ordonné par déclaration (`ordinal`). |

---

## 3. Règle de Tri des Cartes

Le tri est effectué selon la règle prescrite dans le sujet :
1. **Ordre des couleurs :**
   * **Carreau (♦)**
   * **Cœur (♥)**
   * **Pique (♠)**
   * **Trèfle (♣)**
2. **Ordre des valeurs (au sein d'une même couleur) :**
   * **As** (valeur 1)
   * **2, 3, 4, 5, 6, 7, 8, 9, 10**
   * **Valet** (valeur 11)
   * **Dame** (valeur 12)
   * **Roi** (valeur 13)

L'implémentation Java repose sur le comparateur standard :
```java
Comparator<Card> cardComparator = Comparator
        .comparing(Card::getSuit)
        .thenComparing(Card::getRank);
```

---

## 4. Architecture et Organisation du Code

```text
card-game/
├── pom.xml                                   # Descripteur Maven et dépendances
├── README.md                                 # Documentation technique
├── INTERVIEW_GUIDE.md                        # Guide complet de préparation pour l'entretien
├── src/
│   ├── main/
│   │   ├── java/com/example/cardgame/
│   │   │   ├── CardGameApplication.java     # Point d'entrée Spring Boot
│   │   │   ├── model/
│   │   │   │   ├── Suit.java                # Enum des 4 couleurs (Carreau, Cœur, Pique, Trèfle)
│   │   │   │   ├── Rank.java                # Enum des 13 valeurs (As -> Roi)
│   │   │   │   ├── Card.java                # Modèle d'une carte (Suit, Rank, affichage, comparaison)
│   │   │   │   ├── Pack.java                # Modèle du paquet de 52 cartes (produit cartésien)
│   │   │   │   ├── CardGameRound.java       # DTO regroupant paquet complet, main tirée et main triée
│   │   │   │   └── ApiError.java            # Modèle structuré pour les erreurs centralisées
│   │   │   ├── service/
│   │   │   │   └── CardService.java         # Logique métier : création, mélange, tirage 10 cartes, tri
│   │   │   ├── controller/
│   │   │   │   ├── CardController.java      # Contrôleur Spring MVC (/random-hand-cards, /, /api/...)
│   │   │   │   └── GlobalExceptionHandler.java # Gestion centralisée des erreurs (@ControllerAdvice)
│   │   │   └── exception/
│   │   │       └── CardGameException.java   # Exception métier dédiée
│   │   └── resources/
│   │       ├── application.properties       # Configuration de l'application (port 8080)
│   │       ├── templates/
│   │       │   ├── random-hand-cards.html   # Vue Thymeleaf principale
│   │       │   └── error.html               # Vue d'erreur utilisateur
│   │       └── static/
│   │           ├── css/
│   │           │   └── style.css            # Styles CSS responsives et soignés
│   │           └── img/cards/               # 52 cartes vectorielles au format SVG
│   └── test/
│       └── java/com/example/cardgame/
│           ├── service/
│           │   └── CardServiceTest.java     # 10 tests unitaires complets sur la logique métier
│           └── controller/
│               └── CardControllerTest.java  # Tests d'intégration de la couche web
```

---

## 5. Prérequis

* **Java JDK 17** ou supérieur (`java -version`)
* **Apache Maven 3.8+** (`mvn -version`)
* Une connexion Internet lors du tout premier build pour télécharger les dépendances Maven.

---

## 6. Commandes d'Exécution et Tests

### Compiler et exécuter les tests unitaires
```bash
mvn clean test
```

### Lancer l'application localement
```bash
mvn spring-boot:run
```

Ou en construisant le JAR exécutable :
```bash
mvn clean package
java -jar target/card-game-1.0.0.jar
```

### Accéder à l'application dans le navigateur
* URL principale demandée : **[http://localhost:8080/random-hand-cards](http://localhost:8080/random-hand-cards)**
* Redirection automatique depuis la racine : **[http://localhost:8080/](http://localhost:8080/)**
* Endpoint JSON : **[http://localhost:8080/api/random-hand-cards](http://localhost:8080/api/random-hand-cards)**

---

## 7. Couverture des Tests

Les tests unitaires (`CardServiceTest`) et d'intégration (`CardControllerTest`) valident formellement :
1. La création d'un paquet de **52 cartes exactes**.
2. L'**unicité stricte** des 52 cartes (aucun doublon).
3. La présence de **13 cartes par couleur**.
4. La conservation de toutes les cartes après **mélange aléatoire**.
5. Le tirage d'une main contenant **exactement 10 cartes**.
6. L'**unicité des 10 cartes** de la main (tirage sans remise).
7. Le **respect strict de la règle de tri** (d'abord par couleur, puis par valeur).
8. La **non-mutation** de la main d'origine et la parfaite conservation des éléments.
9. La validité de **tirages répétés** successifs.
10. La levée d'exceptions sur les cas limites (quantités invalides, paquet vide/nul).
11. Le bon fonctionnement des endpoints web Spring MVC (redirection 3xx, code HTTP 200, format JSON).
