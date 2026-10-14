# Guide de Préparation à l'Entretien Technique

Ce guide a été spécialement conçu pour vous permettre de comprendre, d'expliquer et de défendre avec aisance chaque ligne de code de votre application devant les recruteurs ou les évaluateurs techniques.

---

## 1. Présentation du projet en français (Script prêt à l'emploi)

> *« Bonjour. Dans le cadre de ce test technique, j'ai développé une application web en Java avec Spring Boot simulant la distribution et le tri d'une main de 10 cartes à jouer.*
>
> *Le projet respecte rigoureusement les critères demandés :*
> 1. *Un paquet complet de 52 cartes est généré à partir du produit cartésien de 4 couleurs et 13 valeurs.*
> 2. *Ce paquet est mélangé aléatoirement, puis une main de 10 cartes uniques est tirée sans remise.*
> 3. *Une copie de cette main est triée de façon déterministe : d'abord par couleur (Carreau, Cœur, Pique, Trèfle), puis par valeur (de l'As au Roi).*
> 4. *L'interface web, accessible sur `/random-hand-cards`, affiche clairement le paquet complet, la main aléatoire et la main triée, avec un bouton permettant de générer une nouvelle main.*
> 5. *J'ai privilégié une architecture simple, propre et découplée (Modèle - Service - Contrôleur), accompagnée d'une gestion centralisée des erreurs avec `ApiError` et d'une suite de 13 tests unitaires et d'intégration validant 100% de la logique métier. »*

---

## 2. Réponses aux 14 points clés de compréhension

### 1. Que fait l'application ?
L'application simule un jeu de 52 cartes. À chaque chargement ou clic sur « Générer une nouvelle main », elle crée les 52 cartes, les mélange, sélectionne les 10 premières cartes, en fait une copie triée selon des règles précises, et présente ces données sur une page web claire et agréable.

### 2. Pourquoi Java et Spring Boot ?
* **Java 17 (LTS) :** Langage orienté objet robuste, fortement typé, sécurisé et largement utilisé dans les systèmes d'entreprise.
* **Spring Boot 3 :** Facilite la création d'applications web autonomes (« production-ready ») sans configuration XML complexe. Il intègre directement un serveur web Tomcat embarqué et gère le cycle de vie des composants grâce à l'injection de dépendances.

### 3. Que signifie Spring MVC dans ce projet ?
Le motif **MVC (Modèle - Vue - Contrôleur)** sépare les responsabilités :
* **M (Modèle) :** Les objets métiers (`Card`, `Suit`, `Rank`, `Pack`, `CardGameRound`).
* **V (Vue) :** Les pages HTML dynamiques rendues par le moteur Thymeleaf (`random-hand-cards.html`).
* **C (Contrôleur) :** La classe `CardController` qui reçoit les requêtes HTTP de l'utilisateur, interroge le service métier, transmet les données au Modèle et choisit la Vue à afficher.

### 4. Que fait Thymeleaf ?
Thymeleaf est un moteur de templates Java moderne. Il permet d'écrire du HTML standard tout en y insérant des balises dynamiques (attributs `th:each`, `th:text`, `th:src`). Contrairement aux anciennes JSP, une page Thymeleaf peut s'ouvrir dans un navigateur standard et reste parfaitement lisible.

### 5. Que fait Maven ?
Maven est l'outil de gestion de projet et de dépendances standard de l'écosystème Java :
* Il télécharge automatiquement les bibliothèques requises définies dans le fichier `pom.xml`.
* Il compile le code source Java (`mvn compile`).
* Il exécute les tests unitaires automatisés (`mvn test`).
* Il assemble l'application sous forme de fichier JAR exécutable (`mvn package`).

### 6. Rôle de chaque classe du projet

| Classe / Fichier | Package | Rôle exact |
| :--- | :--- | :--- |
| **`CardGameApplication`** | `com.example.cardgame` | Classe principale annotée `@SpringBootApplication`. Elle contient le `main()` qui démarre Spring Boot et le serveur web. |
| **`Suit`** | `...model` | Enumération des 4 couleurs (`CARREAU`, `COEUR`, `PIQUE`, `TREFLE`). L'ordre de déclaration définit l'ordre de tri. |
| **`Rank`** | `...model` | Enumération des 13 valeurs (`ACE`, `TWO`, ..., `KING`). Elle encapsule la valeur numérique et le libellé court. |
| **`Card`** | `...model` | Représente une carte (association immuable d'un `Suit` et d'un `Rank`). Implémente `Comparable<Card>` pour le tri naturel. |
| **`Pack`** | `...model` | Représente le paquet de 52 cartes. Généré automatiquement par produit cartésien, avec validation d'intégrité (52 cartes uniques). |
| **`CardGameRound`** | `...model` | Objet de transport (DTO) regroupant le paquet complet, la main non triée et la main triée pour la transmission à la vue. |
| **`ApiError`** | `...model` | Structure d'erreur normalisée (horodatage, code statut, libellé d'erreur, message explicatif, URL appelée). |
| **`CardGameException`** | `...exception` | Exception métier personnalisée (`RuntimeException`) levée en cas de dysfonctionnement du jeu. |
| **`CardService`** | `...service` | Cœur de la logique métier : création du paquet, mélange aléatoire, tirage de 10 cartes et tri déterministe. |
| **`CardController`** | `...controller` | Contrôleur web Spring MVC associant les URL (`/` et `/random-hand-cards`) aux méthodes du service. |
| **`GlobalExceptionHandler`** | `...controller` | Classe `@ControllerAdvice` qui intercepte les exceptions pour renvoyer une réponse structurée `ApiError` ou une page d'erreur. |

### 7. Pourquoi séparer le Modèle, le Service et le Contrôleur ?
C'est le principe de **responsabilité unique (SRP)** de l'architecture logicielle :
* Le contrôleur ne doit s'occuper **que** du protocole web (HTTP, URL, paramètres, vue).
* Le service contient **toutes les règles métier** indépendantes du web (permettant d'être réutilisé par exemple dans une application mobile, une API REST ou une tâche planifiée).
* Le modèle définit les structures de données.
* Cette séparation rend le code **facile à tester unitairement**, propre et évolutif.

### 8. Comment sont créées les 52 cartes ?
Par une double boucle (produit cartésien) dans la classe `Pack` :
```java
for (Suit suit : Suit.values()) {
    for (Rank rank : Rank.values()) {
        cards.add(new Card(suit, rank));
    }
}
```
Comme `Suit.values()` contient 4 éléments et `Rank.values()` contient 13 éléments, 4 × 13 = exactement 52 cartes uniques.

### 9. Comment fonctionne le mélange ?
Dans `CardService.java`, on utilise `Collections.shuffle(shuffledList)`. Cette méthode de la bibliothèque standard Java implémente l'algorithme réputé de **Fisher-Yates**, assurant une permutation aléatoire équiprobable de la liste. On opère sur une copie pour ne pas altérer la liste originale.

### 10. Comment 10 cartes sont sélectionnées sans doublon ?
Le paquet contenant initialement 52 cartes distinctes et le mélange étant une permutation sans ajout ni suppression, prendre les 10 premières cartes (`subList(0, 10)`) garantit mathématiquement **l'absence totale de doublon (tirage sans remise)**. Le service applique en plus un contrôle de sécurité via un `Set` (`uniqueCards.size() == 10`).

### 11. Comment fonctionne le tri ?
En Java, les éléments d'un `enum` possèdent un ordre naturel correspondant à leur ordre de déclaration (`ordinal()`).
Le tri est orchestré via un `Comparator` en deux étapes :
```java
Comparator<Card> cardComparator = Comparator
        .comparing(Card::getSuit)    // 1. D'abord par couleur
        .thenComparing(Card::getRank); // 2. Puis par valeur
```
1. Toutes les cartes de Carreau viennent en premier, triées de l'As au Roi.
2. Puis les cartes de Cœur, de l'As au Roi.
3. Puis les cartes de Pique, de l'As au Roi.
4. Enfin les cartes de Trèfle, de l'As au Roi.

### 12. Comment fonctionnent les tests unitaires et que vérifient-ils ?
Les tests sont écrits avec **JUnit 5** et s'exécutent avec `mvn clean test`.
Ils vérifient de façon reproductible et déterministe :
* Que le paquet compte 52 cartes et qu'elles sont toutes uniques.
* Que chaque couleur a 13 cartes.
* Que le mélange ne perd aucune carte.
* Que la main tirée fait 10 cartes uniques.
* Qu'une main test prédéterminée est triée dans l'ordre exact attendu.
* Que la main triée contient les mêmes cartes que la main tirée.
* Que le contrôleur web répond HTTP 200 et transmet le bon modèle à Thymeleaf.

### 13. Comment lancer l'application et la démontrer ?
1. Dans le terminal du projet, taper :
   ```bash
   mvn spring-boot:run
   ```
2. Ouvrir le navigateur sur :
   `http://localhost:8080/random-hand-cards`
3. Montrer les 3 sections :
   * La main aléatoire de 10 cartes.
   * La main triée (mêmes cartes, rangées par couleur puis valeur).
   * Le paquet de 52 cartes en bas.
4. Cliquer sur le bouton **« Générer une nouvelle main »** pour prouver le caractère dynamique du tirage.

---

## 3. Cinq questions probables en entretien & Réponses

#### Q1 : « Pourquoi avez-vous utilisé des `enum` plutôt que de simples chaînes de caractères (`String`) pour la couleur et la valeur ? »
> **Réponse :** « L'utilisation des énumérations (`enum`) offre la sécurité du typage à la compilation (type safety) : il est impossible d'instancier par erreur une couleur ou une valeur inexistante. De plus, les `enum` fournissent nativement un ordre naturel (`ordinal()`), ce qui rend le tri déterministe et très lisible sans coder de longues conditions `if/else`. »

#### Q2 : « Que se passe-t-il si un utilisateur demande à tirer 60 cartes d'un paquet de 52 cartes ? »
> **Réponse :** « La méthode `drawHand` dans `CardService` valide strictement les paramètres d'entrée. Si le nombre demandé est négatif, nul ou supérieur à la taille du paquet, une exception métier `CardGameException` est levée. Notre gestionnaire centralisé `@ControllerAdvice` intercepte cette exception et renvoie une réponse structurée `ApiError` avec un code HTTP 400 Bad Request, sans planter le serveur. »

#### Q3 : « Pourquoi avoir utilisé `Collections.unmodifiableList()` dans `Pack` et `CardGameRound` ? »
> **Réponse :** « C'est une bonne pratique de programmation défensive. En renvoyant une vue immuable de la liste, on empêche tout code extérieur (comme un contrôleur ou une autre classe) d'ajouter, supprimer ou modifier des cartes dans le paquet original par inadvertance. »

#### Q4 : « Quelle est la différence entre `equals()` / `hashCode()` et `compareTo()` dans votre classe `Card` ? »
> **Réponse :** « `equals()` et `hashCode()` définissent l'égalité stricte entre deux cartes (même couleur et même valeur). Ils sont indispensables pour garantir l'unicité dans les structures comme `HashSet`. `compareTo()` quant à lui implémente l'interface `Comparable<Card>` et établit l'ordre relatif entre deux cartes différentes (laquelle est plus petite ou plus grande) pour permettre le tri automatique. »

#### Q5 : « Comment avez-vous géré l'affichage des images de cartes si les fichiers venaient à manquer ? »
> **Réponse :** « J'ai mis en place une double stratégie : d'une part, 52 fichiers SVG légers et nets sont générés dans `static/img/cards/` avec un mapping prévisible (`couleur_valeur.svg`). D'autre part, la vue Thymeleaf et le CSS intègrent un fallback automatique : si l'image est indisponible, l'événement `onerror` masque la balise image et la carte s'affiche instantanément en HTML/CSS pur avec son rang, son symbole et sa couleur (rouge ou noire), évitant toute icône d'image brisée. »
