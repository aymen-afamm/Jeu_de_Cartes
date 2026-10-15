# Modèle d'Email d'Envoi du Test Technique

> **Instructions :**  
> Copiez le texte ci-dessous dans votre messagerie, remplacez les informations entre crochets `[...]` (par exemple le nom du recruteur s'il est connu), joignez le fichier archive `Test_Technique_Jeu_de_Cartes.zip` si vous envoyez une pièce jointe, et insérez le lien GitHub si vous partagez le dépôt distant.

---

**Objet :** Soumission du Test Technique Java / Spring Boot — Simulation et Tri d'un Jeu de Cartes — Aymen ZAGNOUNI

**Corps du message :**

Bonjour [Madame / Monsieur / Prénom du recruteur],

Je vous remercie vivement pour l'opportunité de passer cette évaluation technique.

Veuillez trouver ci-joint ma réalisation du test technique portant sur la simulation de distribution et de tri d'une main de cartes à jouer en Java et Spring Boot.

### Synthèse des éléments développés :
* **Génération et modèle de données :** Paquet standard de 52 cartes uniques généré automatiquement par produit cartésien de 4 couleurs (`Carreau`, `Cœur`, `Pique`, `Trèfle`) et 13 valeurs (`As` à `Roi`).
* **Mélange et distribution :** Algorithme de permutation aléatoire de Fisher-Yates et tirage sans remise d'une main de 10 cartes sans doublon.
* **Tri déterministe :** Tri d'une copie indépendante de la main par couleur puis par valeur, sans modification de la main d'origine.
* **Interface utilisateur :** Vue web Thymeleaf accessible sur `http://localhost:8080/random-hand-cards` présentant distinctement le paquet de 52 cartes, la main aléatoire et la main triée, avec les cartes au format vectoriel SVG.
* **Gestion centralisée des erreurs :** Implémentation d'un composant `@ControllerAdvice` renvoyant une réponse structurée de type `ApiError`.
* **Qualité et tests :** Une suite complète de 19 tests automatisés (JUnit 5, MockMvc) validant l'intégralité de la logique métier, des routes web et de la gestion des erreurs.

### Modalités d'accès et d'exécution :
* Le code source complet et sa documentation détaillée sont inclus dans l'archive jointe `Test_Technique_Jeu_de_Cartes.zip`.
* Le projet est également consultable sur le dépôt GitHub : [https://github.com/aymen-afamm/jeu-des-carts](https://github.com/aymen-afamm/jeu-des-carts)
* Grâce au Maven Wrapper intégré, l'application peut être compilée, testée et lancée immédiatement sans installation préalable de Maven via les commandes :
  * Exécution des tests : `.\mvnw.cmd clean test` (ou `./mvnw clean test` sous Linux/Mac)
  * Lancement de l'application : `.\mvnw.cmd spring-boot:run` (ou `./mvnw spring-boot:run`)
  * Accès à l'application : `http://localhost:8080/random-hand-cards`

Je reste à votre entière disposition pour tout échange complémentaire ou pour vous présenter cette réalisation lors de notre entretien technique.

En vous souhaitant une excellente réception.

Bien cordialement,

**Aymen ZAGNOUNI**  
*Candidat Développeur*
