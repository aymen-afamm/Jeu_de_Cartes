# 🎓 Guide de Préparation Complet à l'Entretien Technique Java / Spring Boot
## Projet : Jeu de Cartes (Distribution et Tri)

> **Public cible :** Débutant en Java / Spring Boot préparant un entretien de recrutement technique en français.  
> **Objectif :** Comprendre le fonctionnement réel de chaque composant, savoir expliquer le code avec vos propres mots sans réciter par cœur, et aborder sereinement les questions du recruteur.

---

# Sommaire

1. [Partie A — Comprendre les technologies de zéro](#partie-a--comprendre-les-technologies-de-zéro)
2. [Partie B — Explication détaillée du projet fichier par fichier](#partie-b--explication-détaillée-du-projet-fichier-par-fichier)
3. [Partie C — Le cycle de vie complet d'une requête HTTP (Flux applicatif)](#partie-c--le-cycle-de-vie-complet-dune-requête-http)
4. [Partie D — 32 questions / réponses réalistes pour l'entretien](#partie-d--32-questions--réponses-réalistes-pour-lentretien)
5. [Partie E — Scripts de présentation orale et conseils pour l'entretien](#partie-e--scripts-de-présentation-orale-et-conseils)
6. [Partie F — Fiche mémo express (Dernière révision avant l'entretien)](#partie-f--fiche-mémo-express)

---

# Partie A — Comprendre les technologies de zéro

Cette partie définit chaque concept fondamental avec :
1. Sa définition simple.
2. Son rôle précis dans notre projet.
3. Un exemple concret extrait de notre code source.
4. Une phrase naturelle en français prête à dire à l'oral.

---

### 1. Java
* **Définition :** Java est un langage de programmation orienté objet, fortement typé, compilé en bytecode puis exécuté sur une machine virtuelle (la JVM).
* **Rôle dans le projet :** C'est le langage utilisé pour écrire toute la logique de notre application (modèles, algorithmes de tri, contrôleurs).
* **Exemple concret :** Fichiers `.java` dans `src/main/java/com/example/cardgame/`.
* **Phrase pour l'oral :** *« J'ai choisi Java 17 LTS car c'est un langage robuste et typé qui garantit la fiabilité du modèle de données et une excellente maintenabilité. »*

---

### 2. Le JDK (Java Development Kit) et la JVM
* **Définition :** Le JDK est la boîte à outils du développeur Java. Il contient le compilateur (`javac`) qui transforme le code source en bytecode, ainsi que la JVM (Java Virtual Machine) qui exécute ce bytecode sur n'importe quel système d'exploitation.
* **Rôle dans le projet :** Nous utilisons Temurin OpenJDK 17 pour compiler notre projet en fichier JAR et le faire tourner sur Windows ou Linux.
* **Phrase pour l'oral :** *« Le JDK fournit les outils pour compiler le code Java, tandis que la JVM permet d'exécuter l'application de façon portable sur n'importe quelle machine. »*

---

### 3. Spring Boot
* **Définition :** Un framework Java qui simplifie considérablement la création d'applications web d'entreprise en réduisant le besoin de configuration complexe grâce au principe de « convention plutôt que configuration » (convention over configuration).
* **Rôle dans le projet :** Il intègre directement un serveur web Apache Tomcat, configure l'injection de dépendances, gère le routage des URL et relie les données à notre template Thymeleaf.
* **Exemple concret :** L'annotation `@SpringBootApplication` dans `CardGameApplication.java`.
* **Phrase pour l'oral :** *« Spring Boot nous permet de créer une application web autonome immédiatement prête pour la production avec un serveur Tomcat embarqué, sans avoir à configurer de serveur d'application externe. »*

---

### 4. Apache Maven et le Wrapper (`mvnw` / `mvnw.cmd`)
* **Définition :** Maven est le gestionnaire de cycle de vie et de dépendances standard de Java. Le "Maven Wrapper" (`mvnw`) est un petit script fourni avec le projet qui télécharge automatiquement la bonne version de Maven si celle-ci n'est pas installée sur l'ordinateur de l'évaluateur.
* **Rôle dans le projet :** Maven télécharge les bibliothèques (Spring Boot, Thymeleaf, JUnit), compile les fichiers Java, lance les tests unitaires et génère le fichier `target/card-game-1.0.0.jar`.
* **Exemple concret :** Commande `.\mvnw.cmd clean test`.
* **Phrase pour l'oral :** *« J'ai inclus le Maven Wrapper dans le projet afin que n'importe quel développeur ou évaluateur puisse compiler et tester l'application immédiatement sans prérequis d'installation de Maven. »*

---

### 5. Le fichier `pom.xml` (Project Object Model)
* **Définition :** C'est la carte d'identité XML du projet Maven. Il liste les métadonnées (nom, version, version Java) et toutes les dépendances externes dont le projet a besoin.
* **Rôle dans le projet :** Il déclare la dépendance vers Spring Boot 3.3.4, Java 17, `spring-boot-starter-web`, `spring-boot-starter-thymeleaf` et `spring-boot-starter-test`.
* **Exemple concret :** Les balises `<dependencies>` dans `pom.xml`.
* **Phrase pour l'oral :** *« Le fichier pom.xml centralise toutes les dépendances externes et plugins nécessaires au build de notre application. »*

---

### 6. Les Dépendances (Dependencies)
* **Définition :** Des bibliothèques de code pré-écrites par d'autres développeurs que notre projet importe pour éviter de réinventer la roue (comme le serveur web Tomcat ou le moteur de rendu HTML).
* **Rôle dans le projet :** `spring-boot-starter-web` apporte Tomcat et Spring MVC, `spring-boot-starter-thymeleaf` gère les templates HTML, `spring-boot-starter-test` apporte JUnit 5 et Mockito.
* **Phrase pour l'oral :** *« Les starters Spring Boot regroupent des ensembles cohérents de dépendances testées ensemble pour éviter les conflits de versions. »*

---

### 7. Un Serveur Web et Apache Tomcat
* **Définition :** Un logiciel qui écoute sur un port réseau (par exemple le port 8080), reçoit les requêtes des navigateurs web (requêtes HTTP) et leur renvoie des pages HTML, des images ou du JSON.
* **Rôle dans le projet :** Spring Boot démarre un serveur Tomcat embarqué dès qu'on lance l'application. On n'a pas besoin d'installer de serveur manuellement.
* **Phrase pour l'oral :** *« Tomcat est le serveur web embarqué par défaut dans Spring Boot Web qui écoute par défaut sur le port 8080 pour traiter nos requêtes HTTP. »*

---

### 8. Requêtes HTTP et Méthode GET
* **Définition :** HTTP est le protocole de communication du web. Une requête `GET` est une demande d'un navigateur pour lire ou récupérer une ressource (une page web, une image) sans modifier l'état d'un compte sur le serveur.
* **Rôle dans le projet :** Lorsque vous tapez `http://localhost:8080/random-hand-cards`, le navigateur envoie une requête `GET /random-hand-cards`.
* **Exemple concret :** `@GetMapping("/random-hand-cards")`.
* **Phrase pour l'oral :** *« Le protocole HTTP est utilisé par le navigateur pour solliciter l'application via la méthode GET, associée à notre endpoint de tirage de cartes. »*

---

### 9. Un Endpoint (Point de terminaison)
* **Définition :** Une adresse URL spécifique exposée par le serveur à laquelle est attachée une action précise du programme.
* **Rôle dans le projet :** L'endpoint principal exigé par le sujet est `/random-hand-cards`. L'application propose aussi `/` (redirection) et `/api/random-hand-cards` (format JSON).
* **Phrase pour l'oral :** *« Un endpoint est l'URL d'entrée qui déclenche l'exécution d'une méthode spécifique dans notre contrôleur. »*

---

### 10. Le motif d'architecture MVC (Modèle - Vue - Contrôleur)
* **Définition :** Un motif d'architecture logicielle séparant l'application en 3 couches distinctes :
  * **Modèle (Model) :** Les données et les règles métier (`Card`, `Pack`, `Suit`, `Rank`).
  * **Vue (View) :** L'affichage graphique pour l'utilisateur (`random-hand-cards.html`).
  * **Contrôleur (Controller) :** Le coordinateur qui reçoit la requête HTTP, sollicite le service, transmet les données au modèle et désigne la vue à renvoyer (`CardController.java`).
* **Phrase pour l'oral :** *« Le motif MVC sépare nettement la présentation de la logique métier, ce qui rend le code beaucoup plus propre, lisible et testable. »*

---

### 11. Le Contrôleur (`@Controller`)
* **Définition :** Une classe Java responsable de l'interaction avec le protocole HTTP.
* **Rôle dans le projet :** `CardController` intercepte l'URL `/random-hand-cards`, demande une manche au `CardService`, place les 3 listes de cartes dans le modèle Spring MVC et renvoie le nom du template Thymeleaf.
* **Phrase pour l'oral :** *« Le contrôleur ne contient aucun algorithme de jeu : il se contente d'orchestrer la communication entre la requête web et le service métier. »*

---

### 12. Le Service (`@Service`)
* **Définition :** Une classe Java qui contient **toute la logique métier** (les algorithmes et règles du jeu).
* **Rôle dans le projet :** `CardService` génère le paquet de 52 cartes, effectue le mélange aléatoire, extrait les 10 cartes de la main et réalise le tri par couleur puis par valeur.
* **Phrase pour l'oral :** *« Le service isole les règles métier du web : si demain nous voulions créer une application mobile ou console, nous pourrions réutiliser ce service sans changer une seule ligne. »*

---

### 13. Le Moteur de Template Thymeleaf
* **Définition :** Un moteur de rendu HTML côté serveur. Il prend un fichier HTML standard contenant des balises spéciales (préfixées par `th:`) et remplace ces balises par les vraies données calculées par Java avant d'envoyer la page au navigateur.
* **Rôle dans le projet :** Il boucle sur les cartes avec `th:each="card : ${randomHand}"` pour afficher chaque carte avec son image, son symbole et son rang.
* **Exemple concret :** `<div th:each="card : ${sortedHand}">`.
* **Phrase pour l'oral :** *« Thymeleaf produit du HTML dynamique côté serveur tout en restant conforme aux standards HTML, ce qui permet de tester et prototyper les maquettes très facilement. »*

---

### 14. Différence entre Vue HTML (SSR) et API REST JSON
* **Définition :** 
  * Une **Vue HTML (Server-Side Rendering)** renvoie du code HTML prêt à être affiché directement par un navigateur pour un humain.
  * Une **API REST JSON** renvoie uniquement des données brutes structurées sous forme de texte JSON, destinées à un autre programme (une application mobile Flutter, un frontend React/Vue, etc.).
* **Rôle dans le projet :** Notre endpoint `/random-hand-cards` renvoie la vue HTML avec Thymeleaf, tandis que `/api/random-hand-cards` renvoie les données en JSON pour démontrer la flexibilité de l'application.
* **Phrase pour l'oral :** *« Le sujet demandait une vue HTML complète, que j'ai complétée par un endpoint JSON optionnel pour prouver que notre service métier est facilement interfaçable avec des clients tiers. »*

---

### 15. JUnit 5 et MockMvc
* **Définition :** 
  * **JUnit 5 :** Le framework standard en Java pour écrire et exécuter des tests unitaires automatisés.
  * **MockMvc :** Un outil de Spring Test permettant de simuler des requêtes HTTP sur un contrôleur sans avoir besoin d'ouvrir un vrai navigateur ni de démarrer un port réseau.
* **Rôle dans le projet :** 19 tests automatisés (`CardServiceTest`, `CardControllerTest`, `GlobalExceptionHandlerTest`) vérifient la validité du paquet, du tirage, du tri et des réponses HTTP.
* **Phrase pour l'oral :** *« Les tests JUnit 5 valident mathématiquement les règles métier du jeu, et MockMvc simule les requêtes HTTP pour vérifier nos codes de statut et la structure des données renvoyées. »*

---

### 16. Les Annotations Java et Spring
Une annotation est un mot-clé précédé d'un `@` qui ajoute un comportement ou une configuration spéciale à une classe ou une méthode :
* **`@SpringBootApplication`** : Place cette classe au sommet de l'application pour activer la configuration automatique et le scan des composants.
* **`@Controller`** : Indique à Spring que cette classe est un contrôleur web qui renvoie des vues HTML.
* **`@Service`** : Indique à Spring que cette classe contient de la logique métier et doit être gérée comme un singleton réutilisable.
* **`@GetMapping("/...")`** : Associe une URL HTTP GET à une méthode Java précise.
* **`@ControllerAdvice`** : Intercepteur global qui capture toutes les exceptions levées par les contrôleurs pour renvoyer une réponse d'erreur propre.
* **`@ExceptionHandler`** : Spécifie quelle exception précise est traitée par quelle méthode d'erreur.

---

# Partie B — Explication détaillée du projet fichier par fichier

Cette section passe en revue chaque fichier du projet dans un ordre pédagogique pour comprendre son code ligne par ligne.

---

## 1. `pom.xml`
* **Localisation :** Racine du projet `pom.xml`
* **Pourquoi il existe :** Il définit pour Maven comment compiler l'application et quelles bibliothèques importer.
* **Éléments clés :**
  * `<parent>` : Hérite de `spring-boot-starter-parent` en version `3.3.4`, ce qui configure automatiquement les versions compatibles de tous les sous-composants.
  * `<java.version>17</java.version>` : Spécifie que le projet requiert Java 17.
  * `spring-boot-starter-web` : Apporte Tomcat et le moteur Spring MVC.
  * `spring-boot-starter-thymeleaf` : Apporte le moteur de template HTML.
  * `spring-boot-starter-test` : Apporte JUnit 5, AssertJ, Mockito et MockMvc.
  * `spring-boot-maven-plugin` : Permet de packager l'application en un fichier JAR exécutable (`fat JAR`).

---

## 2. `CardGameApplication.java`
* **Localisation :** `src/main/java/com/example/cardgame/CardGameApplication.java`
* **Rôle :** C'est le point de départ de l'application (contient la méthode `public static void main(String[] args)`).
* **Code :**
```java
@SpringBootApplication
public class CardGameApplication {
    public static void main(String[] args) {
        SpringApplication.run(CardGameApplication.class, args);
    }
}
```
* **Concepts Java à expliquer :**
  * `public static void main` : Point d'entrée standard de tout programme Java.
  * `SpringApplication.run(...)` : Démarre le conteneur Spring, instancie les services et contrôleurs, puis démarre le serveur web Tomcat sur le port 8080.

---

## 3. `Suit.java` (Les 4 couleurs)
* **Localisation :** `src/main/java/com/example/cardgame/model/Suit.java`
* **Rôle :** Représente les 4 couleurs possibles d'un jeu de 52 cartes.
* **Code :**
```java
public enum Suit {
    CARREAU("Carreau", "♦", "red", "carreau"),
    COEUR("Cœur", "♥", "red", "coeur"),
    PIQUE("Pique", "♠", "black", "pique"),
    TREFLE("Trèfle", "♣", "black", "trefle");
...
```
* **Pourquoi utiliser un `enum` ?**
  1. **Sécurité de typage (`Type Safety`) :** Impossible de passer une couleur invalide comme `"TrèfleBleu"` ou `"Triangle"`.
  2. **Ordre naturel intrinsèque :** En Java, les éléments d'un `enum` ont un ordre naturel défini par leur position de déclaration (`ordinal()`). Ainsi, déclarer `CARREAU` avant `COEUR` permet à Java de savoir automatiquement que Carreau est plus petit que Cœur !
  3. **Attributs enrichis :** Chaque couleur porte son nom français (`displayName`), son symbole visuel Unicode (`♦`), sa couleur CSS (`red` ou `black`) et son code pour les fichiers images SVG (`carreau`).

---

## 4. `Rank.java` (Les 13 valeurs)
* **Localisation :** `src/main/java/com/example/cardgame/model/Rank.java`
* **Rôle :** Représente les 13 valeurs d'une couleur, de l'As au Roi.
* **Code :**
```java
public enum Rank {
    ACE("As", 1, "A", "as"),
    TWO("2", 2, "2", "2"),
    THREE("3", 3, "3", "3"),
    FOUR("4", 4, "4", "4"),
    FIVE("5", 5, "5", "5"),
    SIX("6", 6, "6", "6"),
    SEVEN("7", 7, "7", "7"),
    EIGHT("8", 8, "8", "8"),
    NINE("9", 9, "9", "9"),
    TEN("10", 10, "10", "10"),
    JACK("Valet", 11, "J", "valet"),
    QUEEN("Dame", 12, "Q", "dame"),
    KING("Roi", 13, "K", "roi");
```
* **Points forts à justifier en entretien :**
  * L'ordre de déclaration respecte rigoureusement l'ordre demandé par le sujet : **As d'abord**, puis de 2 à 10, puis Valet, Dame, Roi.
  * Chaque valeur possède son libellé court pour les cartes (`"A"`, `"10"`, `"K"`), son code image (`"as"`, `"valet"`), et sa valeur numérique de 1 à 13.

---

## 5. `Card.java` (Une carte de jeu)
* **Localisation :** `src/main/java/com/example/cardgame/model/Card.java`
* **Rôle :** Modélise une carte individuelle associant une couleur (`Suit`) et une valeur (`Rank`).
* **Propriétés :**
```java
private final Suit suit;
private final Rank rank;
```
* **Concepts clés :**
  * **Immutabilité (`final`) :** Une carte créée ne peut jamais voir sa couleur ou sa valeur modifiée. C'est un objet valeur immuable et sûr.
  * **Validation à la construction :** `Objects.requireNonNull(suit)` empêche d'instancier une carte nulle.
  * **Méthodes utilitaires :**
    * `getImageFileName()` &rarr; renvoie `"carreau_as.svg"`.
    * `getImagePath()` &rarr; renvoie `"/img/cards/carreau_as.svg"`.
    * `getDisplayName()` &rarr; renvoie `"As de Carreau"`.
  * **Implémentation de `Comparable<Card>` :**
```java
@Override
public int compareTo(Card other) {
    if (other == null) return 1;
    int suitComparison = this.suit.compareTo(other.suit);
    if (suitComparison != 0) {
        return suitComparison;
    }
    return this.rank.compareTo(other.rank);
}
```
* **Différence entre `equals()` / `hashCode()` et `compareTo()` :**
  * `equals()` et `hashCode()` vérifient si deux cartes sont identiques (pour empêcher les doublons dans un `HashSet`).
  * `compareTo()` détermine qui est plus grand ou plus petit pour le tri.

---

## 6. `Pack.java` (Le paquet de 52 cartes)
* **Localisation :** `src/main/java/com/example/cardgame/model/Pack.java`
* **Rôle :** Représente le paquet complet standard de 52 cartes généré automatiquement par produit cartésien.
* **Génération par produit cartésien :**
```java
private static List<Card> generate52Cards() {
    List<Card> generated = new ArrayList<>(STANDARD_PACK_SIZE);
    for (Suit suit : Suit.values()) {
        for (Rank rank : Rank.values()) {
            generated.add(new Card(suit, rank));
        }
    }
    return generated;
}
```
* **Validation d'intégrité :**
```java
public static void validatePack(List<Card> cardsToValidate) {
    if (cardsToValidate == null) {
        throw new IllegalArgumentException("La liste de cartes ne peut pas être nulle");
    }
    if (cardsToValidate.size() != STANDARD_PACK_SIZE) {
        throw new IllegalStateException("Le paquet doit contenir exactement 52 cartes...");
    }
    Set<Card> uniqueCards = new HashSet<>(cardsToValidate);
    if (uniqueCards.size() != STANDARD_PACK_SIZE) {
        throw new IllegalStateException("Le paquet contient des cartes en double !");
    }
}
```
* **Copie défensive et immutabilité :**
  * `getCards()` renvoie `Collections.unmodifiableList(cards)`.
  * Cela empêche toute classe extérieure d'ajouter ou retirer des cartes du paquet.

---

## 7. `CardGameRound.java` (Le conteneur de données d'une manche)
* **Localisation :** `src/main/java/com/example/cardgame/model/CardGameRound.java`
* **Rôle :** C'est un DTO (Data Transfer Object) immuable qui regroupe les 3 résultats d'une manche :
  1. `fullDeck` : La liste des 52 cartes du paquet.
  2. `randomHand` : La main de 10 cartes tirées au sort (non triée).
  3. `sortedHand` : La copie indépendante de ces 10 cartes triée selon les règles.
* **Intérêt :** Le contrôleur n'a besoin d'appeler le service qu'une seule fois (`cardService.playRound()`) et reçoit toutes les données nécessaires en un seul objet propre.

---

## 8. `CardService.java` (Le cœur de la logique métier)
* **Localisation :** `src/main/java/com/example/cardgame/service/CardService.java`
* **Rôle :** Service Spring (`@Service`) exécutant les 4 étapes du jeu :
  1. **`createPack()`** : Instancie un nouveau `Pack` de 52 cartes.
  2. **`shuffle(List<Card> cards)`** : Crée une copie de la liste et appelle `Collections.shuffle(shuffledList)`. Le paquet source n'est jamais modifié.
  3. **`drawHand(List<Card> shuffledDeck, int count)`** :
     * Extrait les $N$ premières cartes via `new ArrayList<>(shuffledDeck.subList(0, count))`.
     * Valide l'absence de doublon avec un `HashSet`.
     * Valide que `count` est valide (ni 0, ni négatif, ni supérieur à la taille du paquet).
  4. **`sortHand(List<Card> hand)`** :
     * Fait une copie indépendante de la main : `new ArrayList<>(hand)`.
     * Applique un comparateur composé en deux temps :
       ```java
       Comparator<Card> cardComparator = Comparator
               .comparing(Card::getSuit)
               .thenComparing(Card::getRank);
       sorted.sort(cardComparator);
       ```
     * Vérifie par sécurité que la taille et le contenu sont parfaitement conservés.
  5. **`playRound()`** : Orchestre la séquence complète et renvoie le `CardGameRound`.

---

## 9. `CardController.java` (Le contrôleur Spring MVC)
* **Localisation :** `src/main/java/com/example/cardgame/controller/CardController.java`
* **Rôle :** Classe `@Controller` traitant les requêtes HTTP.
* **Injection par constructeur :**
```java
public CardController(CardService cardService) {
    this.cardService = cardService;
}
```
*Pourquoi par constructeur ?* C'est la recommandation officielle de Spring : cela rend les dépendances explicites, immuables (`final`), et facilite grandement l'écriture des tests unitaires sans framework magique d'injection.
* **Les routes gérées :**
  * `GET /` : Redirige vers `/random-hand-cards` (`return "redirect:/random-hand-cards"`).
  * `GET /random-hand-cards` : Appelle `cardService.playRound()`, injecte les listes dans l'objet `Model` de Spring MVC, et renvoie `"random-hand-cards"` (qui fait afficher le template `random-hand-cards.html`).
  * `GET /api/random-hand-cards` : Endpoint JSON retournant l'objet `CardGameRound` sérialisé automatiquement par Jackson.

---

## 10. `ApiError.java` (Modèle d'erreur structurée)
* **Localisation :** `src/main/java/com/example/cardgame/model/ApiError.java`
* **Rôle :** Requis expressément par le sujet du test technique.
* **Champs structurés :**
  * `timestamp` : Date et heure ISO de l'erreur (`LocalDateTime.now()`).
  * `status` : Code numérique HTTP (ex: `400` ou `500`).
  * `error` : Libellé standard HTTP (ex: `"Bad Request"` ou `"Internal Server Error"`).
  * `message` : Message explicatif compréhensible en français.
  * `path` : L'URL appelée ayant causé l'erreur (ex: `"/random-hand-cards"`).

---

## 11. `GlobalExceptionHandler.java` (Gestionnaire centralisé des erreurs)
* **Localisation :** `src/main/java/com/example/cardgame/controller/GlobalExceptionHandler.java`
* **Rôle :** Classe annotée `@ControllerAdvice`. Si une exception est levée n'importe où dans un contrôleur, elle est automatiquement interceptée ici.
* **Comportement intelligent selon le client :**
  * Si la requête vient d'un navigateur web (HTML) : Elle injecte `ApiError` dans le modèle, définit le code de statut HTTP avec `response.setStatus(status.value())` (ne renvoie **jamais** un faux code 200), et affiche la vue `error.html`.
  * Si la requête demande du JSON (ex: `/api/...`) : Elle renvoie directement un `ResponseEntity<ApiError>` avec le statut HTTP adéquat (400 ou 500).

---

## 12. Les Templates HTML (`random-hand-cards.html` & `error.html`)
* **Localisation :** `src/main/resources/templates/`
* **Structure de `random-hand-cards.html` :**
  1. En-tête avec titre, bannière explicative de la règle de tri et bouton d'action **« 🔄 Générer une nouvelle main »**.
  2. Barre de navigation rapide à puces ancrées pour sauter directement aux 3 sections.
  3. **Section 1 : Paquet Complet (52 cartes)** &rarr; Grille responsive affichant les 52 cartes.
  4. **Section 2 : Main Aléatoire (10 cartes non triées)** &rarr; Grille des 10 cartes tirées.
  5. **Section 3 : Main Triée (10 cartes triées)** &rarr; Grille des mêmes 10 cartes rangées selon la règle.
* **Sécurité du rendu des cartes :**
  Chaque carte intègre sa balise image SVG :
  ```html
  <img th:src="@{${card.imagePath}}" th:alt="${card.displayName}"
       class="card-image" onerror="this.style.display='none'">
  ```
  Si une image ne se chargeait pas, l'attribut `onerror` la masque immédiatement et dévoile la carte de repli stylisée en CSS pur (valeur dans les coins et symbole géant au centre), sans jamais afficher d'image brisée.

---

## 13. Les Tests Automatisés (19 Tests)
* **Localisation :** `src/test/java/com/example/cardgame/`
* **`CardServiceTest.java` (13 tests) :**
  * Teste le paquet de 52 cartes (taille, unicité, 13 cartes par couleur).
  * Teste le mélange sans perte de cartes.
  * Teste le tirage des 10 cartes (taille, unicité sans remise).
  * **Test déterministe du tri :** Prend une liste fixe de cartes connues et valide que l'ordre obtenu correspond exactement à la liste attendue.
  * Teste la non-altération de la main d'origine et la parfaite conservation des éléments.
  * Teste les cas d'erreur (paramètres négatifs, main nulle, paquet nul ou altéré).
* **`CardControllerTest.java` (3 tests) :**
  * Vérifie la redirection de `/` vers `/random-hand-cards`.
  * Vérifie le code 200 et la présence des attributs `fullDeck` (52), `randomHand` (10) et `sortedHand` (10).
  * Vérifie la structure JSON de `/api/random-hand-cards`.
* **`GlobalExceptionHandlerTest.java` (3 tests) :**
  * Vérifie que la gestion d'erreur HTML renvoie le statut HTTP 400 et la vue `error`.
  * Vérifie que la gestion d'erreur JSON renvoie HTTP 400 et l'objet JSON `ApiError`.
  * Vérifie la gestion des erreurs 500 sur exception imprévue.

---

# Partie C — Le cycle de vie complet d'une requête HTTP

### 1. Diagramme du flux d'une requête utilisateur

```text
[Utilisateur dans le navigateur]
             │
             │ Tape l'adresse http://localhost:8080/random-hand-cards
             ▼
[Serveur Tomcat embarqué]
             │
             │ Achemine la requête HTTP GET
             ▼
[CardController (Spring MVC)]
             │
             │ Appelle la méthode playRound()
             ▼
[CardService (Couche Métier)]
             │
             ├─ 1. new Pack() ──> Produit cartésien 4 couleurs x 13 valeurs = 52 cartes
             │
             ├─ 2. shuffle(...) ──> Permutation aléatoire Fisher-Yates
             │
             ├─ 3. drawHand(...) ──> Sélection des 10 premières cartes sans remise
             │
             ├─ 4. sortHand(...) ──> Copie indépendante + Tri (Couleur puis Valeur)
             │
             │ Retourne l'objet CardGameRound (52 cartes, 10 aléatoires, 10 triées)
             ▼
[CardController]
             │
             │ Injecte les attributs dans le Model :
             │ - model.addAttribute("fullDeck", round.getFullDeck());
             │ - model.addAttribute("randomHand", round.getRandomHand());
             │ - model.addAttribute("sortedHand", round.getSortedHand());
             │ Renvoie le nom de la vue : "random-hand-cards"
             ▼
[Moteur de Template Thymeleaf]
             │
             │ Lit le template src/main/resources/templates/random-hand-cards.html
             │ Remplace les balises th:each, th:src par les cartes réelles
             │ Assemble le document HTML final
             ▼
[Navigateur Web de l'utilisateur]
             │
             │ Reçoit le HTML (code 200 OK) + charge le fichier CSS et les 52 images SVG
             ▼
[Affichage graphique complet des 3 sections]
```

### 2. Que se passe-t-il quand l'utilisateur clique sur « Générer une nouvelle main » ?
1. Le bouton est un lien hypertexte standard `<a th:href="@{/random-hand-cards}">`.
2. Le clic déclenche une nouvelle requête HTTP `GET /random-hand-cards` vers le serveur.
3. Le serveur exécute à nouveau `cardService.playRound()`.
4. Un nouveau mélange aléatoire est opéré, une nouvelle sélection de 10 cartes est réalisée, et la page est restituée avec un tirage totalement neuf.

### 3. Comment chemine une erreur avec la gestion centralisée ?
1. Si une anomalie survient (par exemple, un appel invalide avec 0 carte), le `CardService` lance une `CardGameException("Message d'erreur")`.
2. L'exception remonte à travers le contrôleur.
3. L'annotation `@ControllerAdvice` de `GlobalExceptionHandler` intercepte automatiquement l'exception.
4. Le gestionnaire crée une instance de `ApiError` contenant l'horodatage, le code 400 Bad Request, le message explicatif et l'URL.
5. Il définit le statut de la réponse HTTP (`response.setStatus(400)`) et injecte `apiError` dans la page `error.html` ou sérialise en JSON.
6. Le serveur ne plante jamais et l'utilisateur reçoit une information claire.

---

# Partie D — 32 questions / réponses réalistes pour l'entretien

#### 1. Pourquoi avoir choisi Java 17 ?
> **Réponse :** « Java 17 est une version LTS (Long-Term Support) moderne, stable et largement adoptée en entreprise. Elle apporte des fonctionnalités avancées de productivité, une JVM très optimisée et la robustesse du typage fort pour modéliser notre jeu de cartes en toute sécurité. »

#### 2. Pourquoi Spring Boot et pas simplement du Java pur (Java SE) ?
> **Réponse :** « Spring Boot nous évite de réécrire l'infrastructure web. Il intègre directement un serveur Tomcat prêt à l'emploi, fournit l'architecture MVC pour séparer les responsabilités, prend en charge le moteur de templates Thymeleaf et propose une suite d'outils de test puissante comme MockMvc. »

#### 3. Pourquoi Maven plutôt que Gradle ?
> **Réponse :** « Maven est le standard historique le plus répandu dans l'écosystème Java d'entreprise. Sa structure déclarative avec `pom.xml` est très lisible pour un évaluateur, et l'inclusion du Maven Wrapper (`mvnw`) garantit un build 100% reproductible sur n'importe quel poste sans installation préalable. »

#### 4. Pourquoi Thymeleaf pour l'affichage web ?
> **Réponse :** « Le sujet demandait une application web simple et robuste. Thymeleaf s'intègre nativement à Spring Boot et permet un rendu côté serveur (SSR) sans avoir à monter une usine à gaz JavaScript (React ou Angular). De plus, les templates Thymeleaf restent du HTML valide et propre. »

#### 5. Pourquoi avoir séparé la couche Service de la couche Contrôleur ?
> **Réponse :** « C'est l'application du principe de responsabilité unique (SRP). Le contrôleur ne gère que les aspects liés au protocole web (URL, requêtes, vues, codes HTTP). Le service encapsule la logique métier pure du jeu de cartes. Cette séparation rend la logique facilement testable unitairement et réutilisable dans d'autres contextes (API, mobile, CLI). »

#### 6. Pourquoi utiliser des `enum` pour `Suit` et `Rank` au lieu de simples chaînes de caractères ?
> **Réponse :** « Les `enum` garantissent la sécurité de typage à la compilation (type safety) : il est mathématiquement impossible de créer une carte avec une couleur ou une valeur inexistante. De plus, ils définissent un ordre naturel basé sur leur ordre de déclaration (`ordinal()`), ce qui simplifie le tri sans écrire de longs `if/else`. »

#### 7. Comment sont générées les 52 cartes du paquet ?
> **Réponse :** « Par le produit cartésien de nos 4 couleurs et 13 valeurs dans la classe `Pack`. Deux boucles imbriquées parcourent `Suit.values()` et `Rank.values()` pour instancier exactement 4 × 13 = 52 cartes uniques. »

#### 8. Qu'est-ce qu'un produit cartésien dans ce contexte ?
> **Réponse :** « C'est l'ensemble de toutes les combinaisons possibles entre deux ensembles : pour chaque couleur parmi les 4, on associe successivement chacune des 13 valeurs possibles, ce qui produit l'intégralité des 52 cartes du jeu standard. »

#### 9. Comment fonctionne le mélange des cartes ?
> **Réponse :** « Dans `CardService.java`, on duplique la liste des 52 cartes pour ne pas altérer le paquet original, puis on utilise `Collections.shuffle()`. Cette méthode de la bibliothèque standard Java applique l'algorithme reconnu de Fisher-Yates pour garantir une permutation équiprobable et aléatoire. »

#### 10. Pourquoi utiliser l'algorithme de Fisher-Yates ?
> **Réponse :** « C'est l'algorithme de référence pour mélanger une collection en temps linéaire $O(n)$. Il garantit que chaque arrangement possible des 52 cartes a exactement la même probabilité d'apparaître, sans biais statistique. »

#### 11. Comment garantissez-vous qu'il n'y a pas de doublon dans la main de 10 cartes ?
> **Réponse :** « Le paquet source comporte 52 cartes toutes uniques. Le mélange aléatoire est une permutation pure qui ne duplique ni ne supprime aucune carte. Sélectionner les 10 premières cartes après mélange équivaut à un tirage sans remise. De plus, une vérification avec un `HashSet` dans `drawHand()` valide formellement qu'il y a bien 10 cartes uniques. »

#### 12. Pourquoi trier une copie de la main et non la main elle-même ?
> **Réponse :** « Le sujet exige d'afficher à la fois la main aléatoire non triée ET la main triée. Si nous modifiions la liste originale sur place (tri in-place), nous perdrions l'ordre d'origine du tirage. Nous effectuons donc une copie défensive avec `new ArrayList<>(hand)` avant de la trier. »

#### 13. Comment deux cartes sont-elles comparées lors du tri ?
> **Réponse :** « Via un `Comparator` en deux temps : d'abord sur la couleur (`Card::getSuit`), et si les couleurs sont identiques, sur la valeur (`Card::getRank`). La classe `Card` implémente également l'interface `Comparable<Card>` selon cette même règle. »

#### 14. Quel est l'ordre exact retenu pour le tri ?
> **Réponse :** « L'ordre des couleurs est : Carreau (♦), Cœur (♥), Pique (♠), Trèfle (♣). L'ordre des valeurs est : As (1), 2, 3, 4, 5, 6, 7, 8, 9, 10, Valet (11), Dame (12), Roi (13). Cet ordre est identique dans le code, les tests unitaires et la documentation. »

#### 15. Comment les images sont-elles associées aux cartes ?
> **Réponse :** « Chaque carte calcule son nom d'image via `getImageFileName()` avec la formule `codeCouleur_codeValeur.svg`. Par exemple, le Roi de Pique correspond à `/img/cards/pique_roi.svg`. Les 52 fichiers SVG sont servis directement par Spring Boot depuis `static/img/cards/`. »

#### 16. Que se passe-t-il si un fichier image est manquant ou ne se charge pas ?
> **Réponse :** « La balise `<img>` dans le template Thymeleaf comporte l'attribut JavaScript `onerror="this.style.display='none'"`. Si l'image échoue à charger, elle se masque et laisse apparaître automatiquement une carte de repli élégante rendue en HTML/CSS avec ses symboles et valeurs. Aucune icône d'image brisée n'apparaît. »

#### 17. Comment fonctionne l'injection de dépendances dans votre contrôleur ?
> **Réponse :** « J'utilise l'injection par constructeur : `public CardController(CardService cardService) { this.cardService = cardService; }`. Spring instancie automatiquement le singleton `CardService` et le transmet au contrôleur au démarrage de l'application. »

#### 18. Pourquoi privilégier l'injection par constructeur plutôt que l'annotation `@Autowired` sur le champ ?
> **Réponse :** « L'injection par constructeur permet de rendre le champ `final` (immuable), garantit que la classe ne peut pas être instanciée dans un état incomplet, et facilite grandement les tests unitaires car on peut instancier le contrôleur en lui passant un mock sans recourir à la réflexion. »

#### 19. Quelle est la différence entre les routes `/random-hand-cards` et `/api/random-hand-cards` ?
> **Réponse :** « `/random-hand-cards` renvoie une page HTML complète pour l'affichage visuel dans le navigateur, tandis que `/api/random-hand-cards` renvoie un objet JSON structuré pour une utilisation programmatique via API. »

#### 20. Pourquoi centraliser la gestion des erreurs avec `@ControllerAdvice` ?
> **Réponse :** « Cela évite de répéter des blocs `try/catch` dans chaque méthode de nos contrôleurs. Toute exception non capturée est interceptée au même endroit, ce qui garantit une réponse homogène, sécurisée et conforme au standard `ApiError` exigé par le sujet. »

#### 21. Que contient la classe `ApiError` ?
> **Réponse :** « Elle contient l'horodatage (`timestamp`), le code de statut HTTP numérique (`status`), le libellé HTTP (`error`), le message compréhensible de l'anomalie (`message`) et l'URI sollicitée (`path`). »

#### 22. Votre gestionnaire d'erreur renvoie-t-il du code 200 en cas d'erreur HTML ?
> **Réponse :** « Non, absolument pas. Dans `GlobalExceptionHandler`, nous injectons `HttpServletResponse` et appelons explicitement `response.setStatus(status.value())` (code 400 pour une erreur client ou 500 pour une erreur serveur) avant de retourner la vue d'erreur. »

#### 23. Qu'est-ce qu'un test unitaire ?
> **Réponse :** « C'est un test automatisé qui valide une petite unité de code isolée (comme une méthode de service) de manière déterministe, rapide et sans dépendance extérieure (sans base de données ni réseau). »

#### 24. Comment tester le tri de manière déterministe si le jeu est aléatoire ?
> **Réponse :** « Dans `CardServiceTest.java`, au lieu de trier une main issue d'un mélange aléatoire, nous construisons nous-mêmes une liste de 10 cartes fixes et connues dans un désordre précis. Nous appelons `sortHand()` et nous comparons le résultat à une liste attendue pré-définie avec `assertEquals(expected, sorted)`. »

#### 25. Quelle est la différence entre `CardServiceTest` et `CardControllerTest` ?
> **Réponse :** « `CardServiceTest` est un pur test unitaire qui valide la logique algorithmique (cartes, mélange, tri). `CardControllerTest` est un test d'intégration web utilisant MockMvc pour valider le routage HTTP, les codes de retour, la redirection et le modèle transmis à la vue. »

#### 26. Comment lance-t-on l'application sur Windows ?
> **Réponse :** « En ouvrant un terminal à la racine du projet et en tapant `.\mvnw.cmd spring-boot:run`, ou en générant le JAR avec `.\mvnw.cmd package` puis en l'exécutant avec `java -jar target/card-game-1.0.0.jar`. »

#### 27. Que fait la commande `mvn package` ?
> **Réponse :** « Elle exécute tout le cycle de vie Maven : validation, compilation du code source, exécution de tous les tests unitaires et d'intégration, puis empaquetage de l'application et de ses dépendances sous forme d'une archive JAR autonome exécutable. »

#### 28. Que faire si le port 8080 est déjà occupé sur votre machine ?
> **Réponse :** « Le projet est configuré par défaut sur le port 8080 requis par le sujet. Si un autre logiciel local occupe déjà le port 8080, on peut démarrer l'application sur le port 8081 sans modifier les fichiers de configuration via la commande : `java -jar target/card-game-1.0.0.jar --server.port=8081` ou avec Maven : `.\mvnw.cmd spring-boot:run -Dspring-boot.run.arguments="--server.port=8081"`. »

#### 29. Pourquoi avoir utilisé `Collections.unmodifiableList()` dans `Pack` et `CardGameRound` ?
> **Réponse :** « C'est le principe de programmation défensive. En exposant une vue en lecture seule de la liste de cartes, on s'assure qu'aucun code extérieur ne peut accidentellement altérer, ajouter ou supprimer des cartes dans le paquet. »

#### 30. Quelles sont les limites de l'application actuelle ?
> **Réponse :** « L'application est sans état (stateless) : elle génère chaque manche à la volée sans persistance en base de données. Pour ce test technique, c'était le choix optimal et léger recommandé par les spécifications. »

#### 31. Quelles améliorations pourraient être apportées si le projet devait évoluer ?
> **Réponse :** « On pourrait ajouter un historique des manches jouées en base de données H2/PostgreSQL, un mode multijoueur avec distribution de 10 cartes à plusieurs joueurs à partir du même paquet de 52 cartes jusqu'à épuisement du deck, et une API WebSocket pour animer la distribution carte par carte. »

#### 32. Que dites-vous en entretien si le recruteur vous pose une question pointue à laquelle vous ne connaissez pas la réponse ?
> **Réponse :** « Je réponds avec honnêteté et professionnalisme : *« Je n'ai pas encore rencontré ce cas de figure précis dans ma pratique récente de Java, mais d'après la logique de Spring, j'imagine que cela pourrait fonctionner comme... Je serais ravi d'approfondir ce point ou de voir comment vous l'abordez dans vos projets. »* »

---

# Partie E — Scripts de présentation orale et conseils

### 1. Présentation générale (60 à 90 secondes) — À mémoriser

> *« Bonjour. Pour ce test technique, j'ai développé une application web Java avec Spring Boot simulant la distribution et le tri d'une main de 10 cartes à jouer.*
>
> *Le projet répond rigoureusement aux spécifications :*
> * *Un paquet standard de 52 cartes est généré automatiquement par produit cartésien de 4 couleurs et 13 valeurs.*
> * *Ce paquet est mélangé aléatoirement sans perte de cartes avec l'algorithme de Fisher-Yates.*
> * *Une main de 10 cartes uniques est tirée sans remise.*
> * *Une copie indépendante de cette main est triée de manière déterministe : d'abord par couleur (Carreau, Cœur, Pique, Trèfle), puis par valeur (de l'As au Roi).*
> * *L'interface web, accessible sur `/random-hand-cards`, affiche clairement les 3 sections : le paquet complet de 52 cartes, la main aléatoire et la main triée, avec les images SVG des cartes et un bouton pour relancer un tirage.*
>
> *J'ai privilégié une architecture propre et découplée avec une séparation stricte entre le Contrôleur et le Service métier, une gestion centralisée des erreurs avec `ApiError` et une suite de 19 tests automatisés validant 100% de la logique et des endpoints. »*

---

### 2. Explication technique détaillée (3 à 5 minutes)

Si le recruteur vous demande d'entrer dans les détails de l'architecture :

1. **La modélisation :**
   *« J'ai modélisé les couleurs (`Suit`) et les valeurs (`Rank`) avec des `enum`. Cela garantit le typage strict et élimine tout risque d'avoir des valeurs de cartes fantaisistes. L'ordre de déclaration des enums définit également l'ordre naturel de tri. La classe `Card` associe une couleur et une valeur de manière immuable (`final`). »*
2. **Le Service métier (`CardService`) :**
   *« Toute la logique algorithmique est isolée dans `CardService`. Le paquet de 52 cartes est validé par un `Set` pour vérifier qu'il ne comporte aucun doublon. Le mélange utilise `Collections.shuffle()`. Le tirage extrait une sous-liste de 10 cartes. Et le tri utilise un `Comparator` en deux temps : `Card::getSuit` puis `Card::getRank` sur une copie indépendante de la main. »*
3. **Le Contrôleur et la Vue :**
   *« `CardController` utilise l'injection de dépendances par constructeur. La route `/random-hand-cards` transmet les données au moteur de template Thymeleaf. Les images vectorielles SVG sont nommées de façon prévisible (`carreau_as.svg`) avec un mécanisme de repli en CSS pur en cas d'indisponibilité de l'image. »*
4. **La gestion d'erreurs et les tests :**
   *« J'ai mis en place un `@ControllerAdvice` (`GlobalExceptionHandler`) qui capture les exceptions et renvoie une réponse structurée `ApiError` avec les vrais codes HTTP 400 ou 500. Enfin, 19 tests automatisés JUnit 5 et MockMvc valident l'ensemble des règles métier de manière reproductible. »*

---

### 3. Version ultra-courte (30 secondes)

> *« J'ai conçu une application Spring Boot MVC en Java 17 qui génère un paquet de 52 cartes, le mélange, tire 10 cartes uniques et trie une copie par couleur puis par valeur. L'affichage repose sur Thymeleaf avec images SVG, la logique métier est isolée dans un Service testé à 100% par 19 tests JUnit, et les erreurs sont gérées de façon centralisée via un objet `ApiError`. »*

---

### 4. Mots techniques : Prononciation et sens exact

* **JDK** : Prononcer *« J-D-K »* (Outils de développement Java).
* **Spring Boot** : Prononcer *« Springue Boute »* (Framework applicatif).
* **Thymeleaf** : Prononcer *« Taïm-lif »* (Moteur de templates HTML).
* **Maven** : Prononcer *« Méï-ven »* (Gestionnaire de build et dépendances).
* **Endpoint** : Prononcer *« Ende-point »* (URL d'entrée d'une méthode web).
* **MockMvc** : Prononcer *« Moke-M-V-C »* (Outil de simulation de requêtes HTTP pour les tests).
* **Enum** : Prononcer *« Énum »* (Énumération de constantes typées).

---

# Partie F — Fiche mémo express

### 📌 Les 5 classes capitales à connaître
1. **`Card`** : Modèle immuable combinant un `Suit` et un `Rank`, avec sa méthode `compareTo()`.
2. **`Pack`** : Paquet de 52 cartes généré par produit cartésien et validé sans doublon.
3. **`CardService`** : Logique métier pure (création, mélange, tirage de 10 cartes, tri déterministe).
4. **`CardController`** : Reçoit la requête HTTP `/random-hand-cards` et passe les cartes à la vue.
5. **`GlobalExceptionHandler`** : Capture les erreurs et renvoie l'objet structuré `ApiError`.

---

### 📌 Les règles de tri à avoir en tête
1. **Couleurs :** Carreau (♦) &rarr; Cœur (♥) &rarr; Pique (♠) &rarr; Trèfle (♣).
2. **Valeurs :** As &rarr; 2 &rarr; 3 &rarr; 4 &rarr; 5 &rarr; 6 &rarr; 7 &rarr; 8 &rarr; 9 &rarr; 10 &rarr; Valet &rarr; Dame &rarr; Roi.
3. **Immutabilité :** La main aléatoire d'origine n'est **jamais modifiée** ; c'est une copie qui est triée.

---

### 📌 Les commandes à connaître
* Exécuter les tests : `.\mvnw.cmd clean test`
* Compiler le JAR : `.\mvnw.cmd package`
* Démarrer l'application : `.\mvnw.cmd spring-boot:run`
* Lancer le JAR sur port alternatif : `java -jar target\card-game-1.0.0.jar --server.port=8081`

---

### 📌 Les 3 chiffres clés à retenir
* **52** cartes dans le paquet complet (4 couleurs × 13 valeurs).
* **10** cartes tirées dans la main sans remise.
* **19** tests automatisés réussis (13 tests de service + 3 tests contrôleur + 3 tests d'erreurs).
