# TODO: Ajouter l'option de modifier un lieu

## Étapes à compléter

- [x] Ajouter la méthode `sauvegarderEdition()` dans NavigationBean.java pour enregistrer les modifications en appelant `modifierLieu`.
- [x] Modifier lieu.xhtml pour ajouter un bouton "Editer" dans la colonne Actions du tableau.
- [x] Rendre le formulaire conditionnel : changer le titre ("Ajouter un Lieu" vs "Modifier un Lieu"), le texte du bouton ("Ajouter" vs "Modifier"), et ajouter un bouton "Annuler".
- [x] Tester l'application pour s'assurer que l'édition fonctionne.

## Fichiers à éditer
- src/main/java/com/jakarta2/udbl/jakartamission2/beans/NavigationBean.java
- src/main/webapp/lieu.xhtml

# TODO: Ajouter la vérification d'existence d'utilisateur avant insertion

## Étapes à compléter

- [ ] Ajouter la méthode `trouverUtilisateurParUsername` dans UtilisateurEntrepriseBean.java.
- [ ] Modifier `ajouterUtilisateurEntreprise` pour vérifier si l'utilisateur existe avant insertion.
- [ ] Modifier `UtilisateurBean.ajouterUtilisateur` pour gérer l'exception et afficher le message d'erreur.
- [ ] Tester l'application pour s'assurer que la vérification fonctionne.

## Fichiers à éditer
- src/main/java/com/jakarta2/udbl/jakartamission2/business/UtilisateurEntrepriseBean.java
- src/main/java/com/jakarta2/udbl/jakartamission2/beans/UtilisateurBean.java
