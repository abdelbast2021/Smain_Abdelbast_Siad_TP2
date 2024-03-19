package org.example;

public class Jeux implements Jeu  {
    private final Banque banque;
    private boolean ouvert;


    public Jeux(Banque banque) {
        this.banque = banque;
        this.ouvert = true;
    }

    @Override
    public void jouer(Joueur joueur, De de1, De de2) throws JeuFermeException {
        if (!ouvert) {
            throw new JeuFermeException("Le jeu est fermé.");
        }

        // Débit de la mise du joueur
        try {
            joueur.debiter(joueur.mise());
        } catch (DebitImpossibleException e) {
            ouvert = false;
            throw new JeuFermeException("Le joueur est insolvable.");
        }

        // Lancer des dés
        int sommeDes = de1.lancer() + de2.lancer();

        // Vérifier le résultat
        if (sommeDes == 7) {
            // Créditer le joueur
            joueur.crediter(2 * joueur.mise());
            // Vérifier si la banque est solvable après le gain
            if (!banque.est_solvable()) {
                ouvert = false;
                throw new JeuFermeException("La banque n'est plus solvable.");
            }
        } else {
            // Si la somme n'est pas 7, le joueur perd sa mise
            ouvert = false;
        }
    }

    public void fermer() {
        ouvert = false;
    }

    public boolean estOuvert() {
        return ouvert;
    }
}


