package org.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.ArgumentCaptor;

public class JeuTest {

    // Test du jeu fermé
    @Test
    public void testJouer_JeuFermeException() throws JeuFermeException {
        // Arrange
        Banque banqueMock = Mockito.mock(Banque.class);
        when(banqueMock.est_solvable()).thenReturn(true); // La banque est solvable
        Jeu jeu = new Jeux(banqueMock);
        jeu.fermer(); // On ferme le jeu
        Joueur joueurMock = Mockito.mock(Joueur.class);
        De de1Mock = Mockito.mock(De.class);
        De de2Mock = Mockito.mock(De.class);

        // Act & Assert
        assertThrows(JeuFermeException.class, () -> jeu.jouer(joueurMock, de1Mock, de2Mock));
        verify(banqueMock, never()).debiter(anyInt()); // La banque ne doit pas débiter
        verify(joueurMock, never()).crediter(anyInt()); // Le joueur ne doit pas être crédité
    }

    // Test du joueur insolvable
    @Test
    public void testJouer_JoueurInsolvable() throws JeuFermeException, DebitImpossibleException {
        // Arrange
        Banque banqueMock = Mockito.mock(Banque.class);
        when(banqueMock.est_solvable()).thenReturn(true); // La banque est solvable
        Jeu jeu = new Jeux(banqueMock);
        Joueur joueurMock = Mockito.mock(Joueur.class);
        when(joueurMock.mise()).thenReturn(10); // Mise du joueur
        doThrow(DebitImpossibleException.class).when(joueurMock).debiter(anyInt()); // Simulation du joueur insolvable
        De de1Mock = Mockito.mock(De.class);
        De de2Mock = Mockito.mock(De.class);

        // Act & Assert
        assertThrows(DebitImpossibleException.class, () -> jeu.jouer(joueurMock, de1Mock, de2Mock));
        verify(banqueMock, never()).debiter(anyInt()); // La banque ne doit pas débiter
        verify(joueurMock, never()).crediter(anyInt()); // Le joueur ne doit pas être crédité
    }

    // Test du jeu lorsque la banque devient insolvable après un pari gagnant
    @Test
    public void testJouer_BanqueInsolvableApresGain() throws JeuFermeException, DebitImpossibleException {
        // Arrange
        Banque banque = new BanqueImpl(100, 50); // Fond initial de 100 et fond minimum de 50
        Jeu jeu = new Jeux(banque);
        Joueur joueurMock = mock(Joueur.class);
        when(joueurMock.mise()).thenReturn(30); // Mise du joueur
        doNothing().when(joueurMock).debiter(anyInt());  // Simulation du débit du joueur
        doNothing().when(joueurMock).crediter(anyInt()); // Simulation du crédit du joueur
        De de1Mock = mock(De.class);
        when(de1Mock.lancer()).thenReturn(4); // Simulation du lancer de dé
        De de2Mock = mock(De.class);
        when(de2Mock.lancer()).thenReturn(3); // Simulation du lancer de dé

        // Act
        jeu.jouer(joueurMock, de1Mock, de2Mock);

        // Assert
        assertFalse(jeu.estOuvert()); // Le jeu doit être fermé car la banque n'est plus solvable
    }


}
