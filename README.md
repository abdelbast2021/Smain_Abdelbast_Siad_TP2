Les objets dont dépend la classe Jeu et qui doivent être mockés dans un test pour automatiser jouer sont :

Le joueur (interface Joueur) : Pour simuler les actions du joueur telles que la mise, le débit et le crédit.
Les dés (interface De) : Pour simuler les lancers des dés et obtenir des résultats prévisibles.
La banque (interface Banque) : Pour simuler les interactions avec la banque telles que le crédit, le débit et la vérification de sa solvabilité.


Scénarios (classes d'équivalence) pour tester jouer :

Le joueur perd la mise (somme des lancers != 7).
Le joueur gagne la mise (somme des lancers = 7).
La banque devient insolvable après le gain du joueur.
Le joueur est insolvable.
Le jeu est fermé.
Le jeu est ouvert mais le joueur ne peut pas miser une somme négative.
Le jeu est ouvert mais le joueur mise une somme supérieure à son solde.
