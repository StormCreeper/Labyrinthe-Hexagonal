# Labyrinthe hexagonal
Ceci est mon rendu de projet Java pour le cours INF203 de Telecom Paris, année 2023.

## Description
Ceci est mon implémentation d'un logiciel de résolution de labyrinthe sous forme de grille hexagonale. Il permet de :
- Créer un labyrinthe vide en spécifiant une taille de grille
- Charger et sauvegarder un labyrinthe dans un fichier
- Générer un labyrinthe à partir de marches aléatoires
- Modifier un labyrinthe avec la souris
- Résoudre un labyrinthe avec l'algorithme de Dijkstra

## Images
// à venir

## Installation
Vous pouvez télécharger le dossier du projet en .zip avec github, ou cloner le repository.

## Utilisation
Il suffit d'exécuter le fichier builds/build.jar. En ligne de commande, vous pouvez spécifier en argument le chemin d'accès à un fichier de labyrinthe, qui sera chargé au démarrage.

Une fois le logiciel lancé, vous pouvez interagir avec les boutons, ou avec les cases du labyrinthe avec la souris. Si vous cliquez sur une case :
	- Si c'est un mur, la case devient vide, si elle est vide, elle devient un mur. Si vous faites glisser la souris, le contenu des cases survolées est selon la première case sélectionnée.
	- Si c'est la case départ ou arrivée, cliquez sur une autre case pour définir le nouvel emplacement du départ ou de l'arrivée.
Vous pouvez activer la console (qui ne s'affiche qu'un instant par défaut) et le mode debug dans le menu d'affichage.
Il y a quelques labyrinthes d'exemple : un petit, un moyen et un grand, ainsi qu'un labyrinthe qui provoque une erreur.

## Support
Vous pouvez m'envoyer un mail à telo.philippe@telecom-paris.fr en cas de problème ou pour signaler un bug.

## Roadmap
~~J'aimerais implémenter un algorithme de génération procédurale de labyrinthe, par exemple l'algorithme de Wilson.~~ Fait

## Auteur
J'ai réalisé ce projet seul, pour le cours de MDI103 de Telecom Paris.

## Statut du projet
En développement
