# 🃏 Jeu de Cartes - Test Technique (Java / Spring Boot)

Application web développée en **Java 17** avec **Spring Boot 3** simulant la génération, le mélange aléatoire, la distribution et le tri d'une main de 10 cartes à jouer, conformément aux spécifications du test technique.

---

## 1. Vue d'Ensemble du Projet

L'application permet de simuler un tirage de cartes de façon interactive et reproductible :
1. **Création d'un paquet standard (Pack) de 52 cartes uniques**, issu du produit cartésien de 4 couleurs et 13 valeurs.
2. **Mélange aléatoire** du paquet sans altération du paquet d'origine (algorithme Fisher-Yates via `Collections.shuffle`).
3. **Distribution d'une main de 10 cartes** (les 10 premières cartes après mélange, sans remise, garanties sans doublon).
4. **Tri d'une copie indépendante** de la main selon la règle métier :
   * **1° Par couleur :** Carreau (♦) &rarr; Cœur (♥) &rarr; Pique (♠) &rarr; Trèfle (♣)
   * **2° Par valeur :** As &rarr; 2 &rarr; 3 &rarr; 4 &rarr; 5 &rarr; 6 &rarr; 7 &rarr; 8 &rarr; 9 &rarr; 10 &rarr; Valet &rarr; Dame &rarr; Roi
5. **Rendu visuel HTML/CSS moderne et soigné** présentant distinctement :
   * Le **Paquet Complet de 52 cartes**
   * La **Main Aléatoire de 10 cartes non triées**
   * La **Main Triée de 10 cartes**
   * Un bouton **« 🔄 Générer une nouvelle main »** pour relancer un tirage dynamique côté serveur.
6. **Gestion centralisée des erreurs** (`@ControllerAdvice`) retournant une réponse structurée de type `ApiError` avec les codes HTTP appropriés (400 ou 500, jamais de faux 200).
7. **Endpoint API REST companion** (`GET /api/random-hand-cards`) fournissant les données au format JSON structuré.

---

## 2. Choix Techniques et Justifications

| Composant | Choix technique | Justification |
| :--- | :--- | :--- |
| **Langage** | **Java 17 LTS** | Version LTS moderne, stable et performante. Typage fort, sécurité mémoire, lisibilité accrue (`switch`, `record`, `Comparator`). |
| **Framework Web** | **Spring Boot 3.3.4 (Spring MVC)** | Standard industriel incontournable pour le développement Java. Fournit un serveur Tomcat embarqué, l'injection de dépendances et une architecture MVC claire. |
| **Moteur de Template** | **Thymeleaf** | Intégration native et fluide avec Spring Boot. Permet un rendu côté serveur (SSR) sans complexité JavaScript superflue, avec une syntaxe HTML naturelle. |
| **Gestionnaire de Build** | **Apache Maven (avec Wrapper `mvnw`)** | Gestion standardisée des dépendances et du cycle de vie (compilation, tests, packaging). Le wrapper garantit que le projet compile immédiatement sans installer Maven manuellement. |
| **Framework de Test** | **JUnit 5 + Spring Boot Test + MockMvc** | Tests unitaires isolés pour la logique métier et tests d'intégration web pour les contrôleurs et la gestion d'erreurs. |
| **Modélisation** | **Énumérations Java (`Suit`, `Rank`)** | Sécurité de typage à la compilation (type safety), immutabilité, impossibilité d'avoir une carte invalide, et ordre naturel intrinsèque (`ordinal`). |

---

## 3. Règle de Tri et Déterminisme

Le tri de la main s'appuie sur une règle stricte et cohérente :

### Ordre des 4 couleurs :
1. **Carreau (♦)** (`Suit.CARREAU`)
2. **Cœur (♥)** (`Suit.COEUR`)
3. **Pique (♠)** (`Suit.PIQUE`)
4. **Trèfle (♣)** (`Suit.TREFLE`)

### Ordre des 13 valeurs au sein de chaque couleur :
**As (1) &rarr; 2 &rarr; 3 &rarr; 4 &rarr; 5 &rarr; 6 &rarr; 7 &rarr; 8 &rarr; 9 &rarr; 10 &rarr; Valet (11) &rarr; Dame (12) &rarr; Roi (13)**

### Implémentation du Comparateur :
```java
Comparator<Card> cardComparator = Comparator
        .comparing(Card::getSuit)
        .thenComparing(Card::getRank);

sorted.sort(cardComparator);
```
La classe `Card` implémente également l'interface `Comparable<Card>` avec cette même logique de comparaison pour assurer un ordre naturel uniforme dans tout le projet.

---

## 4. Architecture et Séparation des Responsabilités

Le projet applique rigoureusement le principe de responsabilité unique (SRP) :

```text
card-game/
├── pom.xml                                           # Configuration Maven & dépendances
├── mvnw / mvnw.cmd / .mvn/                           # Maven Wrapper (exécutable sous Linux/Mac/Windows)
├── README.md                                         # Documentation technique et guide de lancement
├── PREPARATION_ENTRETIEN.md                          # Guide complet de préparation pour l'entretien (débutant)
├── EMAIL_ENVOI.md                                    # Modèle d'email d'envoi du test technique
├── src/
│   ├── main/
│   │   ├── java/com/example/cardgame/
│   │   │   ├── CardGameApplication.java             # Point d'entrée Spring Boot (@SpringBootApplication)
│   │   │   ├── model/
│   │   │   │   ├── Suit.java                        # Enum : CARREAU, COEUR, PIQUE, TREFLE
│   │   │   │   ├── Rank.java                        # Enum : ACE, TWO, ..., KING
│   │   │   │   ├── Card.java                        # Modèle immuable d'une carte (couleur, valeur, image)
│   │   │   │   ├── Pack.java                        # Paquet de 52 cartes (produit cartésien 4x13)
│   │   │   │   ├── CardGameRound.java               # DTO regroupant paquet, main tirée et main triée
│   │   │   │   └── ApiError.java                    # Modèle d'erreur structurée (status, message, path, timestamp)
│   │   │   ├── service/
│   │   │   │   └── CardService.java                 # Logique métier pure (création, mélange, tirage, tri)
│   │   │   ├── controller/
│   │   │   │   ├── CardController.java              # Contrôleur web Spring MVC (endpoints HTTP)
│   │   │   │   └── GlobalExceptionHandler.java     # Gestion centralisée des exceptions (@ControllerAdvice)
│   │   │   └── exception/
│   │   │       └── CardGameException.java           # Exception métier personnalisée (RuntimeException)
│   │   └── resources/
│   │       ├── application.properties               # Configuration applicative (port 8080 par défaut)
│   │       ├── templates/
│   │       │   ├── random-hand-cards.html           # Vue Thymeleaf principale
│   │       │   └── error.html                       # Vue HTML d'erreur conviviale
│   │       └── static/
│   │           ├── css/
│   │           │   └── style.css                    # Feuilles de styles CSS responsives
│   │           └── img/cards/                       # 52 images SVG des cartes (nommage normalisé)
│   └── test/
│       └── java/com/example/cardgame/
│           ├── service/
│           │   └── CardServiceTest.java             # 13 tests unitaires de la logique métier
│           └── controller/
│               ├── CardControllerTest.java          # 3 tests d'intégration web (endpoints HTTP)
│               └── GlobalExceptionHandlerTest.java  # 3 tests d'intégration de la gestion d'erreurs
```

### Séparation Service / Contrôleur :
* **`CardController`** : Reçoit les requêtes HTTP (`GET /random-hand-cards`), appelle le service métier, transmet les données au `Model` Spring MVC et renvoie le nom de la vue (`"random-hand-cards"`). Aucun algorithme de jeu n'est présent dans le contrôleur.
* **`CardService`** : Contient l'intégralité de la logique métier (création du paquet, mélange aléatoire, tirage sans remise, tri déterministe). Il est totalement indépendant du protocole HTTP et facilement testable unitairement.

---

## 5. Mapping des Images de Cartes

Les 52 images vectorielles SVG sont stockées dans `src/main/resources/static/img/cards/`.

### Convention de nommage :
Le nom du fichier est calculé de manière déterministe dans `Card.getImageFileName()` :
$$\text{Nom du fichier} = \text{codeCouleur} + \text{"\_"} + \text{codeValeur} + \text{".svg"}$$

* **Codes couleur (`Suit.getCode()`)** : `carreau`, `coeur`, `pique`, `trefle`
* **Codes valeur (`Rank.getCode()`)** : `as`, `2`, `3`, `4`, `5`, `6`, `7`, `8`, `9`, `10`, `valet`, `dame`, `roi`

### Exemples :
* As de Carreau &rarr; `/img/cards/carreau_as.svg`
* 10 de Pique &rarr; `/img/cards/pique_10.svg`
* Roi de Cœur &rarr; `/img/cards/coeur_roi.svg`
* Valet de Trèfle &rarr; `/img/cards/trefle_valet.svg`

### Sécurité visuelle (Fallback CSS) :
Si une image venait à être indisponible, l'attribut `onerror="this.style.display='none'"` masque l'image et laisse apparaître automatiquement une carte élégante rendue en HTML/CSS pur (valeur dans les coins, symbole central rouge ou noir), évitant tout affichage d'icône d'image brisée.

---

## 6. Prérequis

* **Java JDK 17** ou supérieur installé (`java -version`).
* **Git** (pour le clonage du dépôt).
* Maven n'a **pas** besoin d'être installé globalement : le projet inclut le wrapper officiel `mvnw` (Linux/Mac) et `mvnw.cmd` (Windows).

---

## 7. Commandes de Compilation, Test et Exécution

Toutes les commandes ci-dessous sont à exécuter depuis la racine du projet (`D:\FlutterProjects\Test_Technique`).

### Sous Windows (PowerShell ou Invite de commandes) :

#### 1. Nettoyer et exécuter tous les tests (19 tests) :
```cmd
.\mvnw.cmd clean test
```

#### 2. Compiler et packager le fichier JAR exécutable :
```cmd
.\mvnw.cmd package
```
Le fichier JAR généré se trouve dans : `target/card-game-1.0.0.jar`.

#### 3. Lancer l'application :
```cmd
.\mvnw.cmd spring-boot:run
```
Ou directement avec le JAR généré :
```cmd
java -jar target/card-game-1.0.0.jar
```

### Sous Linux / macOS :
```bash
./mvnw clean test
./mvnw package
./mvnw spring-boot:run
```

---

## 8. URLs et Navigation

Une fois l'application démarrée :

* **URL principale demandée par le sujet :**
  👉 **[http://localhost:8080/random-hand-cards](http://localhost:8080/random-hand-cards)**
* **Redirection racine :**
  👉 **[http://localhost:8080/](http://localhost:8080/)** (redirige automatiquement en HTTP 302 vers `/random-hand-cards`)
* **Endpoint API REST (JSON) :**
  👉 **[http://localhost:8080/api/random-hand-cards](http://localhost:8080/api/random-hand-cards)**

---

## 9. Gestion des Conflits de Port (Port 8080 occupé)

Le port par défaut configuré dans `src/main/resources/application.properties` est **strictement le port 8080** comme exigé par le sujet (`server.port=8080`).

Si le port 8080 est déjà occupé sur votre machine par un autre service (par exemple IIS, Docker ou un agent local), vous pouvez lancer l'application sur un port alternatif (ex: **8081**) **sans modifier les fichiers du projet** :

### Option A — En ligne de commande avec Spring Boot :
```cmd
.\mvnw.cmd spring-boot:run -Dspring-boot.run.arguments="--server.port=8081"
```

### Option B — Avec le fichier JAR exécutable :
```cmd
java -jar target/card-game-1.0.0.jar --server.port=8081
```

Vous accéderez alors à l'application via : **[http://localhost:8081/random-hand-cards](http://localhost:8081/random-hand-cards)**.

---

## 10. Couverture des Tests Automatisés (19 Tests Réussis)

La suite de tests automatisés valide 100% des exigences fonctionnelles et techniques :

### Tests unitaires du Service (`CardServiceTest` - 13 tests) :
1. `testPackContainsExactly52Cards` : Le paquet complet contient exactement 52 cartes.
2. `testAll52CardsAreUnique` : Toutes les 52 cartes sont distinctes (aucun doublon).
3. `testEachSuitHas13Cards` : Exactement 13 cartes par couleur (produit cartésien 4x13).
4. `testShufflePreservesAllCards` : Le mélange conserve l'intégralité des 52 cartes d'origine.
5. `testDrawHandReturnsExactly10Cards` : Le tirage retourne exactement 10 cartes.
6. `testHandCardsAreUnique` : Les 10 cartes tirées sont uniques (tirage sans remise).
7. `testSortHandDeterministicOrder` : Tri déterministe sur un jeu de cartes prédéfini (Carreau, Cœur, Pique, Trèfle puis As à Roi).
8. `testSortDoesNotMutateOriginalAndPreservesCards` : La main d'origine reste intacte (non-mutation) et la main triée contient les mêmes cartes.
9. `testMultipleRoundsValidity` : Répétition de 5 manches successives avec validation de l'ordre.
10. `testInvalidDrawCountThrowsException` : Gestion d'erreurs sur les tirages invalides (0, négatif, > 52, paquet nul).
11. `testPackValidationRejectsInvalidData` : Rejet des paquets nuls, de taille anormale ou comportant des doublons.
12. `testSortHandNullThrowsException` : Rejet d'une main nulle au tri.
13. `testShuffleNullOrEmptyThrowsException` : Rejet d'une liste nulle ou vide au mélange.

### Tests d'intégration du Contrôleur Web (`CardControllerTest` - 3 tests) :
14. `testRootRedirectsToRandomHandCards` : Redirection HTTP 302 de `/` vers `/random-hand-cards`.
15. `testGetRandomHandCardsReturnsViewWithModel` : Vérification du code 200, du nom de la vue et des attributs du modèle (10 cartes aléatoires, 10 cartes triées, 52 cartes).
16. `testGetRandomHandCardsJsonEndpoint` : Vérification de l'endpoint JSON (`fullDeck`, `randomHand`, `sortedHand`).

### Tests d'intégration de la Gestion d'Erreurs (`GlobalExceptionHandlerTest` - 3 tests) :
17. `testHtmlErrorHandling` : Requête HTML avec `CardGameException` &rarr; statut HTTP 400 Bad Request, vue `error` et attribut `apiError`.
18. `testJsonCardGameExceptionHandling` : Requête JSON avec `CardGameException` &rarr; statut HTTP 400 Bad Request avec JSON `ApiError` complet.
19. `testUnexpectedExceptionHandling` : Exception non gérée &rarr; statut HTTP 500 Internal Server Error avec JSON `ApiError`.

---

## 11. Gestion Centralisée des Erreurs (`ApiError`)

Conformément au sujet, toute anomalie est capturée par la classe `GlobalExceptionHandler` (`@ControllerAdvice`) :
* Les requêtes de navigateur (HTML) affichent une page d'erreur conviviale `error.html` avec les détails de l'erreur et un lien de retour.
* Les requêtes API (ou avec en-tête `Accept: application/json`) reçoivent une charge utile JSON standardisée :

```json
{
  "timestamp": "2026-10-15T02:22:15.123",
  "status": 400,
  "error": "Bad Request",
  "message": "Le nombre de cartes à tirer doit être supérieur à 0",
  "path": "/api/random-hand-cards"
}
```
