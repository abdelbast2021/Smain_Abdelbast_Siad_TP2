package org.example;


import com.google.protobuf.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import org.mockito.ArgumentCaptor;


import com.google.protobuf.ServiceException;
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest_Exercice3 {
    @Mock
    private UtilisateurApi utilisateurApiMock;



    @Test
    public void testCreerUtilisateur_Exception() throws ServiceException {
        Utilisateur utilisateur = new Utilisateur("Jean", "Dupont", "jeandupont@email.com" , 5);

        // Scenario 1: Throw an exception during user creation
        doThrow(new ServiceException("Echec de la création de l'utilisateur")).when(utilisateurApiMock).creerUtilisateur(utilisateur);

        UserService userService = new UserService(utilisateurApiMock);
        try {
            userService.creerUtilisateur(utilisateur);
        } catch (ServiceException e) {
            // Ensure ServiceException is thrown
            // No further verification since the exception is expected
            assertEquals("Echec de la création de l'utilisateur",e.getMessage());
        }
    }

    @Test
    public void testCreerUtilisateur_ValidationFailure() throws ServiceException {
        Utilisateur utilisateur = new Utilisateur("Jean", "Dupont", "jeandupont@email.com" , 5);

        // Scenario 2: Validation error behavior
        // Assuming validation failure doesn't throw an exception but no interaction with UtilisateurApi occurs
        doNothing().when(utilisateurApiMock).creerUtilisateur(utilisateur);

        UserService userService = new UserService(utilisateurApiMock);
        userService.creerUtilisateur(utilisateur);

        // Verify that UtilisateurApi method is never called
        verify(utilisateurApiMock, never()).creerUtilisateur(utilisateur);
    }

    @Test
    public void testCreerUtilisateur_UniqueIDReturned() throws ServiceException {
        Utilisateur utilisateur = new Utilisateur("Jean", "Dupont", "jeandupont@email.com" , 5);
        int idUtilisateur = 123;

        // Scenario 3: Unique ID returned by API
        when(utilisateurApiMock.creerUtilisateur2(utilisateur)).thenReturn(true);
        when(utilisateurApiMock.getIdUtilisateur()).thenReturn(idUtilisateur);

        UserService userService = new UserService(utilisateurApiMock);
        userService.creerUtilisateur(utilisateur);

        int result = utilisateurApiMock.getIdUtilisateur() ;

        // Verification of the ID of the user after sending it to the API
        verify(utilisateurApiMock).creerUtilisateur(utilisateur);
        verify(utilisateurApiMock).getIdUtilisateur();
        // Assuming you have appropriate getters in Utilisateur class
        assertEquals(idUtilisateur, result);
    }

    @Test
    public void testCreerUtilisateur_CaptureArguments() throws ServiceException {
        Utilisateur utilisateur = new Utilisateur("Jean", "Dupont", "jeandupont@email.com" , 4);
        ArgumentCaptor<Utilisateur> argumentCaptor = ArgumentCaptor.forClass(Utilisateur.class);

        // Scenario 4: Capture arguments passed to creerUtilisateur method
        when(utilisateurApiMock.creerUtilisateur2(any(Utilisateur.class))).thenReturn(true);




// Call the method under test
        UserService userService = new UserService(utilisateurApiMock);
        userService.creerUtilisateur(utilisateur);

// Capture the argument(s) passed to the method
        verify(utilisateurApiMock).creerUtilisateur(argumentCaptor.capture());

// Retrieve the captured argument
        Utilisateur utilisateurCapture = argumentCaptor.getValue();

// Assert the captured argument against expected values using getters
        assertEquals(utilisateur.getNom(), utilisateurCapture.getNom());
        assertEquals(utilisateur.getPrenom(), utilisateurCapture.getPrenom());
        assertEquals(utilisateur.getEmail(), utilisateurCapture.getEmail());
    }
}