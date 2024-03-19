package org.example;

public interface Jeu {
  //  public Jeu(Banque labanque);
    public void jouer(Joueur joueur, De de1, De de2) throws JeuFermeException ;
    public void fermer();
    public boolean estOuvert();
}
